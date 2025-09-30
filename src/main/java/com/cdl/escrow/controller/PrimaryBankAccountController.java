package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.PrimaryBankAccountCriteria;
import com.cdl.escrow.criteriaservice.PrimaryBankAccountCriteriaService;
import com.cdl.escrow.dto.PrimaryBankAccountDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.PrimaryBankAccountRepository;
import com.cdl.escrow.service.PrimaryBankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/primary-bank-account")
@RequiredArgsConstructor
@Slf4j
public class PrimaryBankAccountController {

    private final PrimaryBankAccountService primaryBankAccountService;

    private final PrimaryBankAccountCriteriaService primaryBankAccountCriteriaService;

    private final PrimaryBankAccountRepository repository;

    private static final String ENTITY_NAME = "PRIMARY_BANK_ACCOUNT";

    @GetMapping
    public ResponseEntity<Page<PrimaryBankAccountDTO>> getAllPrimaryBankAccountByCriteria(@ParameterObject PrimaryBankAccountCriteria criteria,
                                                                          @ParameterObject  Pageable pageable) {
        Page<PrimaryBankAccountDTO> page = primaryBankAccountCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<PrimaryBankAccountDTO>> getAllPrimaryBankAccount(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all primary bank account, page: {}", pageable.getPageNumber());
        Page<PrimaryBankAccountDTO> page = primaryBankAccountService.getAllPrimaryBankAccount(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<PrimaryBankAccountDTO> savePrimaryBankAccount(
            @Valid @RequestBody PrimaryBankAccountDTO dto) {
        log.info("Creating new primary bank account");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new primary bank account cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrimaryBankAccountDTO saved = primaryBankAccountService.savePrimaryBankAccount(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrimaryBankAccountDTO> getPrimaryBankAccountById(@PathVariable Long id) {
        log.info("Fetching primary bank account with ID: {}", id);
        return primaryBankAccountService.getPrimaryBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Primary bank account not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrimaryBankAccountDTO> updatePrimaryBankAccount(
            @PathVariable Long id,
            @Valid @RequestBody PrimaryBankAccountDTO dto) {
        log.info("Updating primary bank account with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        PrimaryBankAccountDTO updated = primaryBankAccountService.updatePrimaryBankAccount(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrimaryBankAccountById(@PathVariable Long id) {
        log.info("Deleting primary bank account with ID: {}", id);
        boolean deleted = primaryBankAccountService.deletePrimaryBankAccountById(id);
        if (deleted) {
            return ResponseEntity.ok("PrimaryBankAccount deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("PrimaryBankAccount deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeletePrimaryBankAccountServiceById(@PathVariable Long id) {
        log.info("Soft deleting PrimaryBankAccount with ID: {}", id);

        boolean deleted = primaryBankAccountService.softPrimaryBankAccountServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("PrimaryBankAccount soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("PrimaryBankAccount soft deletion failed - ID: " + id);
        }
    }

}
