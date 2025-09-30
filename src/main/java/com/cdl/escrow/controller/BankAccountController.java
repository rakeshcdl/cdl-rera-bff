

package com.cdl.escrow.controller;

import com.cdl.escrow.criteriaservice.BankAccountCriteriaService;
import com.cdl.escrow.dto.BankAccountDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.repository.BankAccountRepository;
import com.cdl.escrow.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/bank-account")
@RequiredArgsConstructor
@Slf4j
public class BankAccountController {

    private final BankAccountService bankAccountService;

    private final BankAccountCriteriaService bankAccountCriteriaService;

    private final BankAccountRepository repository;

    private static final String ENTITY_NAME = "BANK_ACCOUNT";

    /*@GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllFinancialInstitutionByCriteria(@ParameterObject FinancialInstitutionCriteria criteria,
                                                                                     @ParameterObject Pageable pageable) {
        Page<BankAccountDTO> page = bankAccountService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/


    @GetMapping("/find-all")
    public ResponseEntity<Page<BankAccountDTO>> getAllFinancialInstitution(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all bank account, page: {}", pageable.getPageNumber());
        Page<BankAccountDTO> page = bankAccountService.getAllBankAccount(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> saveFinancialInstitution(
            @Valid @RequestBody BankAccountDTO dto) {
        log.info("Creating new bank account");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new bank account cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankAccountDTO saved = bankAccountService.saveBankAccount(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getFinancialInstitutionById(@PathVariable Long id) {
        log.info("Fetching bank account with ID: {}", id);
        return bankAccountService.getBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Bank account not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO> updateFinancialInstitution(
            @PathVariable Long id,
            @Valid @RequestBody BankAccountDTO dto) {
        log.info("Updating bank account with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankAccountDTO updated = bankAccountService.updateBankAccount(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFinancialInstitutionById(@PathVariable Long id) {
        log.info("Deleting bank account with ID: {}", id);
        boolean deleted = bankAccountService.deleteBankAccountById(id);
        if (deleted) {
            return ResponseEntity.ok("bank account deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("bank account deletion failed - ID: " + id);
        }
    }
}
