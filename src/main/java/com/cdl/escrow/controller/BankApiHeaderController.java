

package com.cdl.escrow.controller;

import com.cdl.escrow.dto.BankApiHeaderDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.repository.BankApiHeaderRepository;
import com.cdl.escrow.service.BankApiHeaderService;
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
@RequestMapping("/api/v1/bank-api-header")
@RequiredArgsConstructor
@Slf4j
public class BankApiHeaderController {

    private final BankApiHeaderService bankApiHeaderService;

    private final BankApiHeaderRepository repository;

    private static final String ENTITY_NAME = "BANK_API_HEADER";


   /* @GetMapping
    public ResponseEntity<List<BankApiHeaderDTO>> getAllBankApiHeaders(@ParameterObject AppLanguageCodeCriteria criteria,
                                                                                  @ParameterObject Pageable pageable) {
       return null;
    }*/

    @GetMapping("/find-all")
    public ResponseEntity<Page<BankApiHeaderDTO>> getAllBankApiHeaders(
            @ParameterObject   @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all bank api header, page: {}", pageable.getPageNumber());
        Page<BankApiHeaderDTO> page = bankApiHeaderService.getAllBankApiHeader(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BankApiHeaderDTO> saveBankApiHeader(
            @Valid @RequestBody BankApiHeaderDTO dto) {

        log.info("Creating new bank  api header");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new bank  api header cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankApiHeaderDTO saved = bankApiHeaderService.saveBankApiHeader(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankApiHeaderDTO> getBankApiHeaderById(@PathVariable Long id) {
        log.info("Fetching bank  api header with ID: {}", id);
        return bankApiHeaderService.getBankApiHeaderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Bank  api header not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankApiHeaderDTO> updateBankApiHeader(
            @PathVariable Long id,
            @Valid @RequestBody BankApiHeaderDTO dto) {
        log.info("Updating bank  api header with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankApiHeaderDTO updated = bankApiHeaderService.updateBankApiHeader(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankApiHeaderById(@PathVariable Long id) {

        log.info("Deleting bank  api header with ID: {}", id);
        boolean deleted = bankApiHeaderService.deleteBankApiHeaderById(id);
        if (deleted) {
            return ResponseEntity.ok("bank  api header deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("bank  api header deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBankApiHeaderById(@PathVariable Long id) {
        log.info("Soft deleting BankApiHeader with ID: {}", id);

        boolean deleted = bankApiHeaderService.softBankApiHeaderById(id);
        if (deleted) {
            return ResponseEntity.ok("BankApiHeader soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BankApiHeader soft deletion failed - ID: " + id);
        }
    }

}
