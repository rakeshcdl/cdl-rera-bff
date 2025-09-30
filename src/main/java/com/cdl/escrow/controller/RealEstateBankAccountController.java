package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.RealEstateBankAccountCriteria;
import com.cdl.escrow.criteriaservice.RealEstateBankAccountCriteriaService;
import com.cdl.escrow.dto.RealEstateBankAccountDTO;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateBankAccountRepository;
import com.cdl.escrow.service.RealEstateBankAccountService;
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
@RequestMapping("/api/v1/real-estate-bank-account")
@RequiredArgsConstructor
@Slf4j
public class RealEstateBankAccountController {

    private final RealEstateBankAccountService realEstateBankAccountService;

    private final RealEstateBankAccountRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_BANK_ACCOUNT";

    private final RealEstateBankAccountCriteriaService realEstateBankAccountCriteriaService;

    @GetMapping
    public ResponseEntity<Page<RealEstateBankAccountDTO>> getAllRealEstateBankAccountByCriteria(@ParameterObject RealEstateBankAccountCriteria criteria,
                                                                                                                      @ParameterObject  Pageable pageable) {
        Page<RealEstateBankAccountDTO> page = realEstateBankAccountCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateBankAccountDTO>> getAllRealEstateBankAccount(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate bank account, page: {}", pageable.getPageNumber());
        Page<RealEstateBankAccountDTO> page = realEstateBankAccountService.getAllRealEstateBankAccount(pageable);
        return ResponseEntity.ok(page);
    }
    @PostMapping
    public ResponseEntity<RealEstateBankAccountDTO> saveRealEstateBankAccount(
            @Valid @RequestBody RealEstateBankAccountDTO dto) {
        log.info("Creating new real estate bank account");

        RealEstateBankAccountDTO saved = realEstateBankAccountService.saveRealEstateBankAccount(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateBankAccountDTO> getRealEstateBankAccountById(@PathVariable Long id) {
        log.info("Fetching real estate bank account with ID: {}", id);
        return realEstateBankAccountService.getRealEstateBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate bank account not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateBankAccountDTO> updateRealEstateBankAccount(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateBankAccountDTO dto) {
        log.info("Updating real estate bank account with ID: {}", id);

        RealEstateBankAccountDTO updated = realEstateBankAccountService.updateRealEstateBankAccount(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateBankAccountById(@PathVariable Long id) {
        log.info("Deleting real estate bank account with ID: {}", id);
        boolean deleted = realEstateBankAccountService.deleteRealEstateBankAccountById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateBankAccount deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateBankAccount deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateBankAccountServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateBankAccount with ID: {}", id);

        boolean deleted = realEstateBankAccountService.softRealEstateBankAccountServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateBankAccount soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateBankAccount soft deletion failed - ID: " + id);
        }
    }

}
