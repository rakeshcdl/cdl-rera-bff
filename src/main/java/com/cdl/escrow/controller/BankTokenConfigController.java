
package com.cdl.escrow.controller;

import com.cdl.escrow.dto.BankTokenConfigDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.repository.BankTokenConfigRepository;
import com.cdl.escrow.service.BankTokenConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/bank-token-config")
@RequiredArgsConstructor
@Slf4j
public class BankTokenConfigController {

    private final BankTokenConfigService bankTokenConfigService;

    private final BankTokenConfigRepository repository;

    private static final String ENTITY_NAME = "BANK_TOKEN_CONFIG";

    @GetMapping("/find-all")
    public ResponseEntity<Page<BankTokenConfigDTO>> getAllBankTokenConfig(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all bank token config, page: {}", pageable.getPageNumber());
        Page<BankTokenConfigDTO> page = bankTokenConfigService.getAllBankTokenConfig(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BankTokenConfigDTO> saveBankTokenConfig(
            @Valid @RequestBody BankTokenConfigDTO dto) {

        log.info("Creating new bank token config");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new bank token config cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankTokenConfigDTO saved = bankTokenConfigService.saveBankTokenConfig(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankTokenConfigDTO> getBankTokenConfigById(@PathVariable Long id) {
        log.info("Fetching bank token config with ID: {}", id);
        return bankTokenConfigService.getBankTokenConfigById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Bank token config not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankTokenConfigDTO> updateBankTokenConfig(
            @PathVariable Long id,
            @Valid @RequestBody BankTokenConfigDTO dto) {
        log.info("Updating bank token config with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankTokenConfigDTO updated = bankTokenConfigService.updateBankTokenConfig(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankTokenConfigById(@PathVariable Long id) {
        log.info("Deleting bank token config with ID: {}", id);
        boolean deleted = bankTokenConfigService.deleteBankTokenConfigById(id);
        if (deleted) {
            return ResponseEntity.ok("bank token config deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("bank token config deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBankTokenConfigById(@PathVariable Long id) {
        log.info("Soft deleting BankTokenConfig with ID: {}", id);

        boolean deleted = bankTokenConfigService.softBankTokenConfigById(id);
        if (deleted) {
            return ResponseEntity.ok("BankTokenConfig soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BankTokenConfig soft deletion failed - ID: " + id);
        }
    }

}
