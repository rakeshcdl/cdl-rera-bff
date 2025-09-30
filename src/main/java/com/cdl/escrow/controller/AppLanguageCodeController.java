
package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.AppLanguageCodeCriteria;
import com.cdl.escrow.criteriaservice.AppLanguageCodeCriteriaService;
import com.cdl.escrow.dto.AppLanguageCodeDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.AppLanguageCodeRepository;
import com.cdl.escrow.service.AppLanguageCodeService;
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
@RequestMapping("/api/v1/app-language-code")
@RequiredArgsConstructor
@Slf4j
public class AppLanguageCodeController {

    private final AppLanguageCodeService appLanguageCodeService;

    private final AppLanguageCodeCriteriaService appLanguageCodeCriteriaService;

    private final AppLanguageCodeRepository repository;

    private static final String ENTITY_NAME = "APP_LANGUAGE_CODE";

    @GetMapping
    public ResponseEntity<Page<AppLanguageCodeDTO>> getAllApplicationConfigurations( @ParameterObject AppLanguageCodeCriteria criteria,
                                                                                  @ParameterObject  Pageable pageable) {
        Page<AppLanguageCodeDTO> page = appLanguageCodeCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<AppLanguageCodeDTO>> getAllApplicationConfigurations(
         @ParameterObject   @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all app language code, page: {}", pageable.getPageNumber());
        Page<AppLanguageCodeDTO> page = appLanguageCodeService.getAllAppLanguageCode(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<AppLanguageCodeDTO> saveApplicationConfiguration(
            @Valid @RequestBody AppLanguageCodeDTO dto) {
        log.info("Creating new app language code");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new app language cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppLanguageCodeDTO saved = appLanguageCodeService.saveAppLanguageCode(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppLanguageCodeDTO> getApplicationConfigurationById(@PathVariable Long id) {
        log.info("Fetching app language code with ID: {}", id);
        return appLanguageCodeService.getAppLanguageCodeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("App language code not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppLanguageCodeDTO> updateApplicationConfiguration(
            @PathVariable Long id,
            @Valid @RequestBody AppLanguageCodeDTO dto) {
        log.info("Updating app language code with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        AppLanguageCodeDTO updated = appLanguageCodeService.updateAppLanguageCode(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationConfigurationById(@PathVariable Long id) {
        log.info("Deleting app language code with ID: {}", id);
        boolean deleted = appLanguageCodeService.deleteAppLanguageCodeById(id);
        if (deleted) {
            return ResponseEntity.ok("AppLanguageCode deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("AppLanguageCode deletion failed - ID: " + id);
        }
    }
    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteApplicationConfigurationById(@PathVariable Long id) {
        log.info("Soft deleting app language code with ID: {}", id);

        boolean deleted = appLanguageCodeService.softDeleteAppLanguageCodeById(id);
        if (deleted) {
            return ResponseEntity.ok("AppLanguageCode soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("AppLanguageCode soft deletion failed - ID: " + id);
        }
    }

}
