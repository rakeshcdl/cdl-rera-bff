
package com.cdl.escrow.controller;

import com.cdl.escrow.dto.BankApiParameterDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.repository.BankApiParameterRepository;
import com.cdl.escrow.service.BankApiParameterService;
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
@RequestMapping("/api/v1/bank-api-parameter")
@RequiredArgsConstructor
@Slf4j
public class BankApiParameterController {


    private final BankApiParameterService bankApiParameterService;

    private final BankApiParameterRepository repository;

    private static final String ENTITY_NAME = "BANK_API_PARAMETER";

     /* @GetMapping
    public ResponseEntity<List<BankApiParameterDTO>> getAllBankApiHeaders(@ParameterObject AppLanguageCodeCriteria criteria,
                                                                                  @ParameterObject Pageable pageable) {
       return null;
    }*/

    @GetMapping("/find-all")
    public ResponseEntity<Page<BankApiParameterDTO>> getAllBankApiParameters(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all bank api parameter, page: {}", pageable.getPageNumber());
        Page<BankApiParameterDTO> page = bankApiParameterService.getAllBankApiParameter(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BankApiParameterDTO> saveBankApiParameter(
            @Valid @RequestBody BankApiParameterDTO dto) {
        log.info("Creating new bank api parameter");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new bank api parameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankApiParameterDTO saved = bankApiParameterService.saveBankApiParameter(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankApiParameterDTO> getBankApiParameterById(@PathVariable Long id) {
        log.info("Fetching bank api parameter with ID: {}", id);
        return bankApiParameterService.getBankApiParameterById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Bank api parameter not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankApiParameterDTO> updateBankApiParameter(
            @PathVariable Long id,
            @Valid @RequestBody BankApiParameterDTO dto) {
        log.info("Updating bank api parameter with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BankApiParameterDTO updated = bankApiParameterService.updateBankApiParameter(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBankApiParameterById(@PathVariable Long id) {
        log.info("Deleting bank api parameter with ID: {}", id);
        boolean deleted = bankApiParameterService.deleteBankApiParameterById(id);
        if (deleted) {
            return ResponseEntity.ok("bank api parameter deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("bank api parameter deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBankApiParameterById(@PathVariable Long id) {
        log.info("Soft deleting BankApiParameter with ID: {}", id);

        boolean deleted = bankApiParameterService.softBankApiParameterById(id);
        if (deleted) {
            return ResponseEntity.ok("BankApiParameter soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BankApiParameter soft deletion failed - ID: " + id);
        }
    }

}
