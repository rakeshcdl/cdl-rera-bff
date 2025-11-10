
package com.cdl.escrow.controller;

import com.cdl.escrow.dto.LoginRequestDTO;
import com.cdl.escrow.dto.LogoutRequestDTO;
import com.cdl.escrow.helper.RefreshRequestDTO;
import com.cdl.escrow.helper.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthLoginLogoutController {

    @Value("${keycloak.admin.server-url}")
    private String serverUrl;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    private final ObjectMapper objectMapper;

    // Build a RestTemplate with timeouts
    private RestTemplate restTemplate() {
        var factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10_000);
        factory.setReadTimeout(30_000);
        return new RestTemplate(factory);
    }

    private final RestTemplate restRefreshTemplate = new RestTemplate();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("login called for user {}", loginRequest.getUsername());

        String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("username", loginRequest.getUsername());
        params.put("password", loginRequest.getPassword());

        String formBody = params.entrySet()
                .stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tokenUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formBody))
                .build();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Successful login → return token response
                AccessTokenResponse tokenResponse = objectMapper.readValue(response.body(), AccessTokenResponse.class);
                return ResponseEntity.ok(tokenResponse);
            } else {
                // Try to parse Keycloak error
                Map<String, Object> errorDetails = new HashMap<>();
                try {
                    errorDetails = objectMapper.readValue(response.body(), Map.class);
                } catch (Exception ignored) {
                    errorDetails.put("error", "invalid_request");
                    errorDetails.put("error_description", "Unexpected error response");
                }

                // Build UI-friendly message
                String description = (String) errorDetails.getOrDefault("error_description", "Login failed");
                String errorCode = (String) errorDetails.getOrDefault("error", "login_failed");

                Map<String, Object> uiError = new HashMap<>();
                uiError.put("status", response.statusCode());
                uiError.put("error", errorCode);
                uiError.put("message", description);

                return ResponseEntity
                        .status(response.statusCode())
                        .body(uiError);
            }
        } catch (Exception e) {
            log.error("Error while calling Keycloak login API", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("error", "internal_error");
            errorResponse.put("message", "Authentication service unavailable. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody LogoutRequestDTO logoutRequest) {
        String logoutUrl = "https://103.181.200.143:8443/realms/cdl_rera/protocol/openid-connect/logout";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("refresh_token", logoutRequest.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(logoutUrl, request, String.class);
            return ResponseEntity.ok("Logout successful");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshRequestDTO refreshRequest) {

        // 🔹 Your Keycloak token endpoint
        String tokenUrl = "https://103.181.200.143:8443/realms/cdl_rera/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // ✅ important to avoid 406 errors

        // 🔹 Body parameters
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("refresh_token", refreshRequest.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            // 🔹 POST call to Keycloak’s /token endpoint
            ResponseEntity<TokenResponse> response =
                    restTemplate.postForEntity(tokenUrl, request, TokenResponse.class);

            // ✅ Return new tokens
            return ResponseEntity.ok(response.getBody());

        } catch (HttpClientErrorException e) {
            // 🔹 Catch 400/401 errors, typically invalid_grant when refresh token expired
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of(
                            "error", "refresh_failed",
                            "details", e.getResponseBodyAsString()
                    ));
        } catch (Exception ex) {
            // 🔹 Catch network or parsing errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "internal_error", "message", ex.getMessage()));
        }
    }


}
