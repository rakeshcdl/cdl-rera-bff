package com.cdl.escrow.integration;

import com.cdl.escrow.entity.BankApiHeader;
import com.cdl.escrow.entity.BankConfig;
import com.cdl.escrow.entity.BankTokenConfig;
import com.cdl.escrow.repository.BankConfigRepository;
import com.cdl.escrow.repository.BankTokenConfigRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoreBankingFetchApiService {

    private final BankConfigRepository bankConfigRepository;
    private final BankTokenConfigRepository bankTokenConfigRepository;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    /**
     * Main entry point to fetch bank API response.
     */
    public JsonNode processCoreBankFetchApi(String bankCode, String apiName, String accountNumber) throws IOException {
        BankConfig config = bankConfigRepository.findByBankCodeAndApiName(bankCode, apiName);

        if (config == null) {
            throw new RuntimeException("No API Config found for bank=" + bankCode + ", api=" + apiName);
        }

        // Step 1: Get Token if required
        String token = null;
        BankTokenConfig tokenConfig = bankTokenConfigRepository.findByBankConfigId(config.getId());

        if (tokenConfig != null && tokenConfig.getEnabled()) {
            token = fetchAccessToken(tokenConfig);
        }

        // Step 2: Call Primary API with Retry, else Fallback
        try {
            return callWithRetry(config, token, accountNumber, config.getApiUrl());
        } catch (Exception ex) {
            log.warn("Primary API failed for bank={} api={}, trying fallback", bankCode, apiName, ex);
            if (config.getFallbackUrl() != null) {
                return callWithRetry(config, token, accountNumber, config.getFallbackUrl());
            } else {
                throw new RuntimeException("Both primary and fallback API calls failed", ex);
            }
        }
    }

    /**
     * Retry mechanism with exponential backoff.
     */
    private JsonNode callWithRetry(BankConfig config, String token, String accountNumber, String url) throws IOException {
        int retries = (config.getRetryCount() != null) ? config.getRetryCount() : 3;
        int baseDelay = (config.getRetryDelay() != null) ? config.getRetryDelay() : 2;

        for (int attempt = 1; attempt <= retries; attempt++) {
            try {
                return callApi(config, token, accountNumber, url);
            } catch (Exception ex) {
                log.error("API attempt {} failed for url={} with error={}", attempt, url, ex.getMessage());
                if (attempt == retries) {
                    throw ex; // all retries failed
                }
                sleepWithBackoff(baseDelay, attempt);
            }
        }
        throw new RuntimeException("Failed after " + retries + " retries");
    }

    /**
     * Actual API call logic.
     */
    private JsonNode callApi(BankConfig config, String token, String accountNumber, String url) throws IOException {
        // Replace placeholders in URL
        url = url.replace("{accountNumber}", accountNumber);

        Request.Builder requestBuilder = new Request.Builder().url(url);

        // Add Headers
        addHeaders(config.getHeaders(), requestBuilder, token);

        // Add Body
        if ("GET".equalsIgnoreCase(config.getHttpMethod())) {
            requestBuilder.get();
        } else {
            String bodyTemplate = config.getBodyTemplate();
            String requestBody = (bodyTemplate != null)
                    ? bodyTemplate.replace("{accountNumber}", accountNumber)
                    : "{}";

            requestBuilder.method(
                    config.getHttpMethod(),
                    RequestBody.create(requestBody, MediaType.parse("application/json"))
            );
        }

        // Execute Request
        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API call failed with status=" + response.code());
            }

            String responseBody = response.body() != null ? response.body().string() : "";
            return mapper.readTree(responseBody);
        }
    }

    /**
     * Extract Access Token.
     */
    private String fetchAccessToken(BankTokenConfig tokenConfig) throws IOException {
        log.info("Fetching access token from {}", tokenConfig.getTokenUrl());

        Headers headers = buildHeadersFromJson(tokenConfig.getHeadersJson());

        // Build Request Body if present
        RequestBody body = null;
        if (tokenConfig.getBodyTemplate() != null) {
            body = RequestBody.create(tokenConfig.getBodyTemplate(), MediaType.parse("application/json"));
        }

        // Build Request
        Request.Builder requestBuilder = new Request.Builder()
                .url(tokenConfig.getTokenUrl())
                .headers(headers);

        if ("POST".equalsIgnoreCase(tokenConfig.getMethod())) {
            requestBuilder.post(body);
        } else {
            requestBuilder.get();
        }

        // Execute Request
        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch token, status=" + response.code());
            }

            String responseBody = response.body() != null ? response.body().string() : "";
            log.debug("Token response: {}", responseBody);

            return (tokenConfig.getJsonPath() != null)
                    ? JsonPath.read(responseBody, tokenConfig.getJsonPath())
                    : responseBody;
        }
    }

    /**
     * Helper: Add headers dynamically.
     */
    private void addHeaders(List<BankApiHeader> headers, Request.Builder requestBuilder, String token) {
        if (headers == null) return;

        for (BankApiHeader header : headers) {
            if (header.getEnabled()) {
                String headerValue = header.getValue();
                if ("Authorization".equalsIgnoreCase(header.getName()) && token != null) {
                    headerValue = headerValue.replace("{token}", token);
                }
                requestBuilder.addHeader(header.getName(), headerValue);
            }
        }
    }

    /**
     * Helper: Convert JSON string to OkHttp Headers.
     */
    private Headers buildHeadersFromJson(String headersJson) throws IOException {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headersJson != null) {
            Map<String, String> headersMap = mapper.readValue(headersJson, new TypeReference<>() {});
            headersMap.forEach(headerBuilder::add);
        }
        return headerBuilder.build();
    }

    /**
     * Helper: Sleep with exponential backoff.
     */
    private void sleepWithBackoff(int baseDelay, int attempt) {
        try {
            int delay = baseDelay * (int) Math.pow(2, attempt - 1);
            log.info("Retrying after {}s (attempt={})", delay, attempt);
            Thread.sleep(delay * 1000L);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public JsonNode mockApiCall(String url) throws IOException {
        // TODO: remove this mock once real API is working
        String mockJson = """
        {
            "id": "ACE-N-S9x9fwpAGC6PqNxo6",
            "accountNumber": "0205738667605",
            "cif": "57386676",
            "currencyCode": "INR",
            "branchCode": "4111",
            "name": "ACAA CPAANPNCP - PACCNN ACCNANY",
            "shortName": "ACAA CPAX",
            "schemeType": "SBA",
            "schemeCode": "020",
            "transferType": "NEFT",
            "bankName": "HDFC Bank",
            "swiftCode" : "SWFT1122",
            "routingCode": "RT0012",
            "beneficiaryId": "BENE00111",
            "details": {
                "genLedgerSubHeadCode": "22320",
                "iban": "AE230260000205738667605",
                "lastStatementDate": 1632946140000,
                "lastModificationDate": 1658952420000,
                "interestFrequencyType": "M",
                "interestFrequencyStartDate": "31",
                "interestFrequencyWeeknumber": " ",
                "accruedInterest": 0,
                "interestRateCode": "TRUST",
                "interestRate": 0,
                "referAllDebits": true,
                "referAllCredits": false,
                "transferLimits": {
                    "debitTransfer": 0,
                    "creditTransfer": 99999999999999.98
                }
            }
        }
    """;

        return mapper.readTree(mockJson);
    }


    public JsonNode mockApiCustomerCall(String getCifDetails) throws JsonProcessingException {

        String mockJson = """
                {
                  "customerId": "CUE-N-6xMILIvAtlyxHbZ42",
                  "cif": "57386676",
                  "name": {
                    "firstName": "A C A A CPAX PAWAWP YPRPXYPAPNW",
                    "shortName": "ACAA CPAX"
                  },
                  "type": "CORPORATE",
                  "contact": {
                    "preferredEmail": "REGEML@57386676.ae",
                    "preferredPhone": "+971(99)9995738",
                    "address": {
                      "line1": "2902,ds d dodf",
                      "line2": "dvIdf sfddvf lfvjd,deOIf",
                      "city": "DUBAI",
                      "state": "DUBAI",
                      "country": "UNITED ARAB EMIRATES",
                      "pinCode": "215497"
                    }
                  }
                }
                
                """;
        return mapper.readTree(mockJson);
    }

    public JsonNode mockApiSwiftValidationCall(String getCifDetails) throws JsonProcessingException {

        String mockJson = """
                {
                   "swiftCode": "57386676",
                   "valid": true,
                   "message": "Validated successfully"
                 }
                
                
                """;
        return mapper.readTree(mockJson);
    }
}
