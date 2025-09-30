package com.cdl.escrow.controller;

import com.cdl.escrow.dto.BankConfigDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.repository.BankConfigRepository;
import com.cdl.escrow.service.BankConfigService;
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
@RequestMapping("/api/v1/bank-config")
@RequiredArgsConstructor
@Slf4j
public class BankConfigController {

    private final BankConfigService bankConfigService;

    private final BankConfigRepository repository;

    private static final String ENTITY_NAME = "BANK_CONFIG";

    @GetMapping("/find-all")
    public ResponseEntity<Page<BankConfigDTO>> getAllBankConfigs(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all bank config, page: {}", pageable.getPageNumber());
        Page<BankConfigDTO> page = bankConfigService.getAllBankConfig(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BankConfigDTO> saveBankConfig(
            @Valid @RequestBody BankConfigDTO dto) {

        log.info("Creating new bank config");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new bank config cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankConfigDTO saved = bankConfigService.saveBankConfig(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankConfigDTO> getBankConfigById(@PathVariable Long id) {
        log.info("Fetching bank config with ID: {}", id);
        return bankConfigService.getBankConfigById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Bank config not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankConfigDTO> updateBankConfig(
            @PathVariable Long id,
            @Valid @RequestBody BankConfigDTO dto) {
        log.info("Updating bank config with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankConfigDTO updated = bankConfigService.updateBankConfig(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankConfigById(@PathVariable Long id) {
        log.info("Deleting bank config with ID: {}", id);
        boolean deleted = bankConfigService.deleteBankConfigById(id);
        if (deleted) {
            return ResponseEntity.ok("bank config deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("bank config deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBankConfigById(@PathVariable Long id) {
        log.info("Soft deleting BankConfig with ID: {}", id);

        boolean deleted = bankConfigService.softBankConfigById(id);
        if (deleted) {
            return ResponseEntity.ok("BankConfig soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BankConfig soft deletion failed - ID: " + id);
        }
    }

}
