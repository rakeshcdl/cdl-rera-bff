package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.ApplicationUserCriteria;
import com.cdl.escrow.criteriaservice.ApplicationUserCriteriaService;
import com.cdl.escrow.dto.ApplicationUserDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ApplicationUserRepository;
import com.cdl.escrow.service.ApplicationUserService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/application-user")
@RequiredArgsConstructor
@Slf4j
public class ApplicationUserController {
     private final ApplicationUserService applicationUserService;

     private final ApplicationUserCriteriaService applicationUserCriteriaService;

    private final ApplicationUserRepository repository;

    private static final String ENTITY_NAME = "APPLICATION_USER";

    @GetMapping
    public ResponseEntity<Page<ApplicationUserDTO>> getAllApplicationUsersByCriteria(@ParameterObject ApplicationUserCriteria criteria,
                                                                                                   @ParameterObject  Pageable pageable) {
        Page<ApplicationUserDTO> page = applicationUserCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ApplicationUserDTO>> getAllApplicationUsers(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all application user , page: {}", pageable.getPageNumber());
        Page<ApplicationUserDTO> page = applicationUserService.getAllApplicationUser(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ApplicationUserDTO> saveApplicationUser(
            @Valid @RequestBody ApplicationUserDTO dto) {
        log.info("Creating new application user");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new app user cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationUserDTO saved = applicationUserService.saveApplicationUser(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUserDTO> getApplicationUserById(@PathVariable Long id) {
        log.info("Fetching application user with ID: {}", id);
        return applicationUserService.getApplicationUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Application user not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationUserDTO> updateApplicationUser(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationUserDTO dto) {
        log.info("Updating application user with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ApplicationUserDTO updated = applicationUserService.updateApplicationUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationUserById(@PathVariable Long id) {
        log.info("Deleting application user with ID: {}", id);
        boolean deleted = applicationUserService.deleteApplicationUserById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationUser deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationUser deletion failed - ID: " + id);
        }
    }
}
