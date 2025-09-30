package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.SecondaryBankAccountCriteria;
import com.cdl.escrow.criteriaservice.SecondaryBankAccountCriteriaService;
import com.cdl.escrow.dto.SecondaryBankAccountDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.SecondaryBankAccountRepository;
import com.cdl.escrow.service.SecondaryBankAccountService;
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
@RequestMapping("/api/v1/secondary-bank-account")
@RequiredArgsConstructor
@Slf4j
public class SecondaryBankAccountController {

     private final SecondaryBankAccountService secondaryBankAccountService;

     private final SecondaryBankAccountCriteriaService secondaryBankAccountCriteriaService;

    private final SecondaryBankAccountRepository repository;

    private static final String ENTITY_NAME = "SECONDARY_BANK_ACCOUNT";

    @GetMapping
    public ResponseEntity<Page<SecondaryBankAccountDTO>> getAllSecondaryBankAccountsByCriteria(@ParameterObject SecondaryBankAccountCriteria criteria,
                                                                                          @ParameterObject  Pageable pageable) {
        Page<SecondaryBankAccountDTO> page = secondaryBankAccountCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<SecondaryBankAccountDTO>> getAllSecondaryBankAccounts(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all secondary bank account, page: {}", pageable.getPageNumber());
        Page<SecondaryBankAccountDTO> page = secondaryBankAccountService.getAllSecondaryBankAccount(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<SecondaryBankAccountDTO> saveSecondaryBankAccount(
            @Valid @RequestBody SecondaryBankAccountDTO dto) {
        log.info("Creating new secondary bank account");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new secondary bank account cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecondaryBankAccountDTO saved = secondaryBankAccountService.saveSecondaryBankAccount(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecondaryBankAccountDTO> getSecondaryBankAccountById(@PathVariable Long id) {
        log.info("Fetching secondary bank account with ID: {}", id);
        return secondaryBankAccountService.getSecondaryBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Secondary bank account not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecondaryBankAccountDTO> updateSecondaryBankAccount(
            @PathVariable Long id,
            @Valid @RequestBody SecondaryBankAccountDTO dto) {
        log.info("Updating secondary bank account with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        SecondaryBankAccountDTO updated = secondaryBankAccountService.updateSecondaryBankAccount(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSecondaryBankAccountById(@PathVariable Long id) {
        log.info("Deleting secondary bank account with ID: {}", id);
        boolean deleted = secondaryBankAccountService.deleteSecondaryBankAccountById(id);
        if (deleted) {
            return ResponseEntity.ok("SecondaryBankAccount deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SecondaryBankAccount deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteSecondaryBankAccountServiceById(@PathVariable Long id) {
        log.info("Soft deleting SecondaryBankAccount with ID: {}", id);

        boolean deleted = secondaryBankAccountService.softSecondaryBankAccountServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("SecondaryBankAccount soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SecondaryBankAccount soft deletion failed - ID: " + id);
        }
    }
}
