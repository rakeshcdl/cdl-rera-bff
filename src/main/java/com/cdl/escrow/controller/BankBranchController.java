package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.BankBranchCriteria;
import com.cdl.escrow.criteriaservice.BankBranchCriteriaService;
import com.cdl.escrow.dto.BankBranchDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.BankBranchRepository;
import com.cdl.escrow.service.BankBranchService;
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
@RequestMapping("/api/v1/bank-branch")
@RequiredArgsConstructor
@Slf4j
public class BankBranchController {

    private final BankBranchService bankBranchService;

    private final BankBranchCriteriaService bankBranchCriteriaService;

    private final BankBranchRepository repository;

    private static final String ENTITY_NAME = "BANK_BRANCH";

    @GetMapping
    public ResponseEntity<List<BankBranchDTO>> getAllApplicationUsersByCriteria(@ParameterObject BankBranchCriteria criteria,
                                                                                     @ParameterObject  Pageable pageable) {
        Page<BankBranchDTO> page = bankBranchCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<BankBranchDTO>> getAllBankBranches(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all bank branch , page: {}", pageable.getPageNumber());
        Page<BankBranchDTO> page = bankBranchService.getAllBankBranch(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BankBranchDTO> saveBankBranch(
            @Valid @RequestBody BankBranchDTO dto) {
        log.info("Creating new bank branch");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new bank branch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankBranchDTO saved = bankBranchService.saveBankBranch(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankBranchDTO> getBankBranchById(@PathVariable Long id) {
        log.info("Fetching bank branch with ID: {}", id);
        return bankBranchService.getBankBranchById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Bank branch not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankBranchDTO> updateBankBranch(
            @PathVariable Long id,
            @Valid @RequestBody BankBranchDTO dto) {
        log.info("Updating bank branch with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankBranchDTO updated = bankBranchService.updateBankBranch(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankBranchById(@PathVariable Long id) {
        log.info("Deleting bank branch with ID: {}", id);
        boolean deleted = bankBranchService.deleteBankBranchById(id);
        if (deleted) {
            return ResponseEntity.ok("BankBranch deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BankBranch deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBankBranchById(@PathVariable Long id) {
        log.info("Soft deleting BankBranch with ID: {}", id);

        boolean deleted = bankBranchService.softBankBranchById(id);
        if (deleted) {
            return ResponseEntity.ok("BankBranch soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BankBranch soft deletion failed - ID: " + id);
        }
    }
}
