package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.ConfigurationStoreCriteria;
import com.cdl.escrow.criteriaservice.ConfigurationStoreCriteriaService;
import com.cdl.escrow.dto.ConfigurationStoreDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ConfigurationStoreRepository;
import com.cdl.escrow.service.ConfigurationStoreService;
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
@RequestMapping("/api/v1/configuration-store")
@RequiredArgsConstructor
@Slf4j
public class ConfigurationStoreController {
    private final ConfigurationStoreService configurationStoreService;

    private final ConfigurationStoreCriteriaService configurationStoreCriteriaService;

    private final ConfigurationStoreRepository repository;

    private static final String ENTITY_NAME = "CONFIGURATION_STORE";

    @GetMapping
    public ResponseEntity<Page<ConfigurationStoreDTO>> getAllConfigurationStoresByCriteria(@ParameterObject ConfigurationStoreCriteria criteria,
                                                                                                   @ParameterObject  Pageable pageable) {
        Page<ConfigurationStoreDTO> page = configurationStoreCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ConfigurationStoreDTO>> getAllConfigurationStores(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all configuration store, page: {}", pageable.getPageNumber());
        Page<ConfigurationStoreDTO> page = configurationStoreService.getAllConfigurationStore(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ConfigurationStoreDTO> saveConfigurationStore(
            @Valid @RequestBody ConfigurationStoreDTO dto) {
        log.info("Creating new configuration store");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new configuration store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigurationStoreDTO saved = configurationStoreService.saveConfigurationStore(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationStoreDTO> getConfigurationStoreById(@PathVariable Long id) {
        log.info("Fetching configuration store with ID: {}", id);
        return configurationStoreService.getConfigurationStoreById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Configuration store not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationStoreDTO> updateConfigurationStore(
            @PathVariable Long id,
            @Valid @RequestBody ConfigurationStoreDTO dto) {
        log.info("Updating configuration store with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ConfigurationStoreDTO updated = configurationStoreService.updateConfigurationStore(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConfigurationStoreById(@PathVariable Long id) {
        log.info("Deleting configuration store with ID: {}", id);
        boolean deleted = configurationStoreService.deleteConfigurationStoreById(id);
        if (deleted) {
            return ResponseEntity.ok("ConfigurationStore deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ConfigurationStore deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteConfigurationStoreServiceById(@PathVariable Long id) {
        log.info("Soft deleting ConfigurationStore with ID: {}", id);

        boolean deleted = configurationStoreService.softConfigurationStoreServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("ConfigurationStore soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ConfigurationStore soft deletion failed - ID: " + id);
        }
    }

}
