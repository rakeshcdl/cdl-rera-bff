package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.BinaryDataStoreCriteria;
import com.cdl.escrow.criteriaservice.BinaryDataStoreCriteriaService;
import com.cdl.escrow.dto.BinaryDataStoreDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.BinaryDataStoreRepository;
import com.cdl.escrow.service.BinaryDataStoreService;
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
@RequestMapping("/api/v1/binary-data-store")
@RequiredArgsConstructor
@Slf4j
public class BinaryDataStoreController {

   private final BinaryDataStoreService binaryDataStoreService;

   private final BinaryDataStoreCriteriaService binaryDataStoreCriteriaService;

    private final BinaryDataStoreRepository repository;

    private static final String ENTITY_NAME = "BINARY_DATA_STORE";

    @GetMapping
    public ResponseEntity<List<BinaryDataStoreDTO>> getAllBinaryDataStoresByCriteria(@ParameterObject BinaryDataStoreCriteria criteria,
                                                                                @ParameterObject  Pageable pageable) {
        Page<BinaryDataStoreDTO> page = binaryDataStoreCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<BinaryDataStoreDTO>> getAllBinaryDataStores(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all binary data store , page: {}", pageable.getPageNumber());
        Page<BinaryDataStoreDTO> page = binaryDataStoreService.getAllBinaryDataStore(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BinaryDataStoreDTO> saveBinaryDataStore(
            @Valid @RequestBody BinaryDataStoreDTO dto) {
        log.info("Creating new binary data store");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new binary data store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BinaryDataStoreDTO saved = binaryDataStoreService.saveBinaryDataStore(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BinaryDataStoreDTO> getBinaryDataStoreById(@PathVariable Long id) {
        log.info("Fetching binary data store with ID: {}", id);
        return binaryDataStoreService.getBinaryDataStoreById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Binary data store not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BinaryDataStoreDTO> updateBinaryDataStore(
            @PathVariable Long id,
            @Valid @RequestBody BinaryDataStoreDTO dto) {
        log.info("Updating binary data store with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BinaryDataStoreDTO updated = binaryDataStoreService.updateBinaryDataStore(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBinaryDataStoreById(@PathVariable Long id) {
        log.info("Deleting binary data store with ID: {}", id);
        boolean deleted = binaryDataStoreService.deleteBinaryDataStoreById(id);
        if (deleted) {
            return ResponseEntity.ok("BinaryDataStore deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BinaryDataStore deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBinaryDataStoreServiceById(@PathVariable Long id) {
        log.info("Soft deleting BinaryDataStore with ID: {}", id);

        boolean deleted = binaryDataStoreService.softBinaryDataStoreServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("BinaryDataStore soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BinaryDataStore soft deletion failed - ID: " + id);
        }
    }
}
