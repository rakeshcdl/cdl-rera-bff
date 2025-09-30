package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.BuildPartnerContactCriteria;
import com.cdl.escrow.criteriaservice.BuildPartnerContactCriteriaService;
import com.cdl.escrow.dto.BuildPartnerContactDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.BuildPartnerContactRepository;
import com.cdl.escrow.service.BuildPartnerContactService;
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
@RequestMapping("/api/v1/build-partner-contact")
@RequiredArgsConstructor
@Slf4j
public class BuildPartnerContactController {

   private final BuildPartnerContactService buildPartnerContactService;

   private final BuildPartnerContactCriteriaService buildPartnerContactCriteriaService;

    private final BuildPartnerContactRepository repository;

    private static final String ENTITY_NAME = "BUILD_PARTNER_CONTACT";

    @GetMapping
    public ResponseEntity<Page<BuildPartnerContactDTO>> getAllBuildPartnerContactsByCriteria(@ParameterObject BuildPartnerContactCriteria criteria,
                                                                                                    @ParameterObject  Pageable pageable) {
        Page<BuildPartnerContactDTO> page = buildPartnerContactCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<BuildPartnerContactDTO>> getAllBuildPartnerContacts(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all build partner contact , page: {}", pageable.getPageNumber());
        Page<BuildPartnerContactDTO> page = buildPartnerContactService.getAllBuildPartnerContact(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BuildPartnerContactDTO> saveBuildPartnerContact(
            @Valid @RequestBody BuildPartnerContactDTO dto) {
        log.info("Creating new build partner contact");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner contact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildPartnerContactDTO saved = buildPartnerContactService.saveBuildPartnerContact(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildPartnerContactDTO> getBuildPartnerContactById(@PathVariable Long id) {
        log.info("Fetching build partner contact with ID: {}", id);
        return buildPartnerContactService.getBuildPartnerContactById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Build partner contact not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildPartnerContactDTO> updateBuildPartnerContact(
            @PathVariable Long id,
            @Valid @RequestBody BuildPartnerContactDTO dto) {
        log.info("Updating build partner contact with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BuildPartnerContactDTO updated = buildPartnerContactService.updateBuildPartnerContact(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuildPartnerContactById(@PathVariable Long id) {
        log.info("Deleting build partner contact with ID: {}", id);
        boolean deleted = buildPartnerContactService.deleteBuildPartnerContactById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerContact deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerContact deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBuildPartnerContactServiceById(@PathVariable Long id) {
        log.info("Soft deleting BuildPartnerContact with ID: {}", id);

        boolean deleted = buildPartnerContactService.softBuildPartnerContactServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerContact soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerContact soft deletion failed - ID: " + id);
        }
    }
}
