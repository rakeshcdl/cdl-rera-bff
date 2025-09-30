package com.cdl.escrow.integration;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/core-bank-get")
@RequiredArgsConstructor
@Slf4j
public class CoreBankingFetchIntegrationController {

    private final CoreBankingFetchApiService coreBankingFetchApiService;

    /**
     * Trigger a bank API call by bankCode + apiName
     *
     * Example:
     *  GET /api/v1/banks/hdfc/apis/AccountStatus?accountNumber=123456
     */

    @GetMapping("/{bankCode}/apis/account-status")
    public ResponseEntity<JsonNode> triggerAccountStatusApi(
            @PathVariable String bankCode,
            @RequestParam String accountNumber) {

        try {
            String apiName = "";
           // JsonNode response = coreBankingFetchApiService.processCoreBankFetchApi(bankCode, apiName, accountNumber);
            JsonNode response = coreBankingFetchApiService.mockApiCall("getAccountStatus");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    createErrorResponse("API call failed", e.getMessage()));
        }
    }

    @GetMapping("/{bankCode}/apis/customer-details")
    public ResponseEntity<JsonNode> triggerCifApi(
            @PathVariable String bankCode,
            @RequestParam String customerCif) {

        try {
            String apiName = "";
            // JsonNode response = coreBankingFetchApiService.processCoreBankFetchApi(bankCode, apiName, accountNumber);
            JsonNode response = coreBankingFetchApiService.mockApiCustomerCall("getCifDetails");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    createErrorResponse("API call failed", e.getMessage()));
        }
    }

    @GetMapping("/{bankCode}/apis/validate-swift")
    public ResponseEntity<JsonNode> triggerSwiftApi(
            @PathVariable String bankCode,
            @RequestParam String swiftCode) {

        try {
            String apiName = "";
            // JsonNode response = coreBankingFetchApiService.processCoreBankFetchApi(bankCode, apiName, accountNumber);
            JsonNode response = coreBankingFetchApiService.mockApiSwiftValidationCall("getCifDetails");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    createErrorResponse("API call failed", e.getMessage()));
        }
    }


    /**
     * Utility to build error JSON
     */
    private JsonNode createErrorResponse(String error, String details) {
        return new com.fasterxml.jackson.databind.ObjectMapper().createObjectNode()
                .put("status", "error")
                .put("error", error)
                .put("details", details);
    }

}
