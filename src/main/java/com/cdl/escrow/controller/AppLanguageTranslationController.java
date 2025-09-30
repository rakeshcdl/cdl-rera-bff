
package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.AppLanguageTranslationCriteria;
import com.cdl.escrow.criteriaservice.AppLanguageTranslationCriteriaService;
import com.cdl.escrow.dto.AppLanguageTranslationDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.AppLanguageTranslationRepository;
import com.cdl.escrow.service.AppLanguageTranslationService;
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
@RequestMapping("/api/v1/app-language-translation")
@RequiredArgsConstructor
@Slf4j
public class AppLanguageTranslationController {

    private final AppLanguageTranslationService appLanguageTranslationService;

    private final AppLanguageTranslationCriteriaService appLanguageTranslationCriteriaService;

    private final AppLanguageTranslationRepository repository;

    private static final String ENTITY_NAME = "APP_LANGUAGE_TRANSLATION";

    @GetMapping("/search")
    public ResponseEntity<Page<AppLanguageTranslationDTO>> getAllAppLanguageTranslationsByCriteria(@ParameterObject AppLanguageTranslationCriteria criteria,
                                                                                    @ParameterObject  Pageable pageable) {
        Page<AppLanguageTranslationDTO> page = appLanguageTranslationCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping
    public ResponseEntity<Page<AppLanguageTranslationDTO>> getAllByPaginationAppLanguageTranslations(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all app config translation, page: {}", pageable.getPageNumber());
        Page<AppLanguageTranslationDTO> page = appLanguageTranslationService.getAllAppLanguageTranslation(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getAllAppLanguageTranslations() {
        log.info("Fetching all app config translation");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getAllAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<AppLanguageTranslationDTO> saveAppLanguageTranslation(
            @Valid @RequestBody AppLanguageTranslationDTO dto) {
        log.info("Creating new app config translation");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new app language translation already have an ID", ENTITY_NAME, "idexists");
        }
        AppLanguageTranslationDTO saved = appLanguageTranslationService.saveAppLanguageTranslation(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppLanguageTranslationDTO> getAppLanguageTranslationById(@PathVariable Long id) {
        log.info("Fetching app config translation with ID: {}", id);
        return appLanguageTranslationService.getAppLanguageTranslationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("App config translation not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppLanguageTranslationDTO> updateAppLanguageTranslationn(
            @PathVariable Long id,
            @Valid @RequestBody AppLanguageTranslationDTO dto) {
        log.info("Updating app config translation with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        AppLanguageTranslationDTO updated = appLanguageTranslationService.updateAppLanguageTranslation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppLanguageTranslationById(@PathVariable Long id) {
        log.info("Deleting app config translation with ID: {}", id);
        boolean deleted = appLanguageTranslationService.deleteAppLanguageTranslationById(id);
        if (deleted) {
            return ResponseEntity.ok("AppLanguageTranslation deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("AppLanguageTranslation deletion failed - ID: " + id);
        }
    }

    // api to get labels by modules

    @GetMapping("/nav-menu")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getNavMenuAppLanguageTranslations() {
        log.info("Fetching all app config translation of nav menu");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getNavMenuAppLanguageTranslations();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/build-partner")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getBuildPartnerAppLanguageTranslationData() {
        log.info("Fetching all app config translation of build-partner");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getBuildPartnerAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/build-partner-assests")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getBuildPartnerAssestAppLanguageTranslationData() {
        log.info("Fetching all app config translation of build-partner-assests");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getBuildPartnerAssestAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/capital-partner")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getCapitalPartnerAppLanguageTranslationData() {
        log.info("Fetching all app config translation of capital-partner");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getCapitalPartnerAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getTransactionsAppLanguageTranslationData() {
        log.info("Fetching all app config translation of transactions");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getTransactionsAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/processed-transactions")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getTProcessedransactionsAppLanguageTranslationData() {
        log.info("Fetching all app config translation of processed transactions");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getTransactionsAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pending-transactions")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getPendingTransactionsAppLanguageTranslationData() {
        log.info("Fetching all app config translation of pending transactions");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getTransactionsAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/discarded-transactions")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getDiscardedTransactionsAppLanguageTranslationData() {
        log.info("Fetching all app config translation of discarded transactions");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getTransactionsAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getPaymentsAppLanguageTranslationData() {
        log.info("Fetching all app config translation of payments");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getPaymentsAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/surety-bond")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getSuretyBondAppLanguageTranslationData() {
        log.info("Fetching all app config translation of surety-bond");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getSuretyBondAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/fee-repush")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getFeeRepushAppLanguageTranslationData() {
        log.info("Fetching all app config translation of fee repush");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getFeeRepushAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user-management")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getUserdAppLanguageTranslationData() {
        log.info("Fetching all app config translation of user management");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getUserdAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/role-management")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getRoleAppLanguageTranslationData() {
        log.info("Fetching all app config translation of role management");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getRoleAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/group-management")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getSGroupAppLanguageTranslationData() {
        log.info("Fetching all app config translation of group management");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getSGroupAppLanguageTranslationData();
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteAppLanguageTranslationById(@PathVariable Long id) {
        log.info("Soft deleting app language code with ID: {}", id);

        boolean deleted = appLanguageTranslationService.softDeleteAppLanguageTranslationById(id);
        if (deleted) {
            return ResponseEntity.ok("AppLanguageCode soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("AppLanguageCode soft deletion failed - ID: " + id);
        }
    }


    @GetMapping("/reports")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getReportsTranslationData() {
        log.info("Fetching all app config translation of reports");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getReportTranslationData();
        return ResponseEntity.ok(page);
    }


    @GetMapping("/dashboard")
    public ResponseEntity<List<AppLanguageTranslationDTO>> getDashboardTranslationData() {
        log.info("Fetching all app config translation of dashboard");
        List<AppLanguageTranslationDTO> page = appLanguageTranslationService.getDashboardTranslationData();
        return ResponseEntity.ok(page);
    }
}
