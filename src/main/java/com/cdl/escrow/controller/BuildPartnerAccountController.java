package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.BuildPartnerAccountCriteria;
import com.cdl.escrow.criteriaservice.BuildPartnerAccountCriteriaService;
import com.cdl.escrow.dto.BuildPartnerAccountDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.BuildPartnerAccountRepository;
import com.cdl.escrow.service.BuildPartnerAccountService;
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
@RequestMapping("/api/v1/build-partner-account")
@RequiredArgsConstructor
@Slf4j
public class BuildPartnerAccountController {

   private final BuildPartnerAccountService buildPartnerAccountService;

   private final BuildPartnerAccountCriteriaService buildPartnerAccountCriteriaService;

    private final BuildPartnerAccountRepository repository;

    private static final String ENTITY_NAME = "BUILD_PARTNER_ACCOUNT";

    @GetMapping
    public ResponseEntity<Page<BuildPartnerAccountDTO>> getAllBuildPartnerAccountsByCriteria(@ParameterObject BuildPartnerAccountCriteria criteria,
                                                                                     @ParameterObject  Pageable pageable) {
        Page<BuildPartnerAccountDTO> page = buildPartnerAccountCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<BuildPartnerAccountDTO>> getAllBuildPartnerAccounts(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all build partner account , page: {}", pageable.getPageNumber());
        Page<BuildPartnerAccountDTO> page = buildPartnerAccountService.getAllBuildPartnerAccount(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BuildPartnerAccountDTO> saveBuildPartnerAccount(
            @Valid @RequestBody BuildPartnerAccountDTO dto) {
        log.info("Creating new build partner account");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner account cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildPartnerAccountDTO saved = buildPartnerAccountService.saveBuildPartnerAccount(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildPartnerAccountDTO> getBuildPartnerAccountById(@PathVariable Long id) {
        log.info("Fetching build partner account with ID: {}", id);
        return buildPartnerAccountService.getBuildPartnerAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Build partner account not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildPartnerAccountDTO> updateBuildPartnerAccount(
            @PathVariable Long id,
            @Valid @RequestBody BuildPartnerAccountDTO dto) {
        log.info("Updating build partner account with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BuildPartnerAccountDTO updated = buildPartnerAccountService.updateBuildPartnerAccount(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuildPartnerAccountById(@PathVariable Long id) {
        log.info("Deleting build partner account with ID: {}", id);
        boolean deleted = buildPartnerAccountService.deleteBuildPartnerAccountById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerAccount deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerAccount deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBuildPartnerAccountServiceById(@PathVariable Long id) {
        log.info("Soft deleting BuildPartnerAccount with ID: {}", id);

        boolean deleted = buildPartnerAccountService.softBuildPartnerAccountServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerAccount soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerAccount soft deletion failed - ID: " + id);
        }
    }

}
