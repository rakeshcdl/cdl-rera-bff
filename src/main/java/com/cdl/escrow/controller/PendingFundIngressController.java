package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.PendingFundIngressCriteria;
import com.cdl.escrow.criteriaservice.PendingFundIngressCriteriaService;
import com.cdl.escrow.dto.PendingFundIngressDTO;
import com.cdl.escrow.dto.PendingFundIngressExtDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.PendingFundIngressRepository;
import com.cdl.escrow.service.PendingFundIngressService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/pending-fund-ingress")
@RequiredArgsConstructor
@Slf4j
public class PendingFundIngressController {

    private final PendingFundIngressService pendingFundIngressService;

    private final PendingFundIngressCriteriaService criteriaService;

    private final PendingFundIngressRepository repository;

    private static final String ENTITY_NAME = "PENDING_FUND_INGRESS";

    @GetMapping
    public ResponseEntity<Page<PendingFundIngressDTO>> getAllPendingFundIngressByCriteria(@ParameterObject PendingFundIngressCriteria criteria,
                                                                                            @ParameterObject Pageable pageable) {
        Page<PendingFundIngressDTO> page = criteriaService.findByCriteria(criteria,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<PendingFundIngressDTO>> getAllPendingFundIngress(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all Pending fund ingress, page: {}", pageable.getPageNumber());
        Page<PendingFundIngressDTO> page = pendingFundIngressService.getAllPendingFundIngress(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<PendingFundIngressDTO> savePendingFundIngress(
            @Valid @RequestBody PendingFundIngressDTO dto) {
        log.info("Creating new Pending fund ingress");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new pending fund ingress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PendingFundIngressDTO saved = pendingFundIngressService.savePendingFundIngress(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PendingFundIngressDTO> getPendingFundIngressById(@PathVariable Long id) {
        log.info("Fetching Pending fund ingress with ID: {}", id);
        return pendingFundIngressService.getPendingFundIngressById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Pending Fund ingress not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<PendingFundIngressDTO> updatePendingFundIngress(
            @PathVariable Long id,
            @Valid @RequestBody PendingFundIngressDTO dto) {
        log.info("Updating Pending fund ingress with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        PendingFundIngressDTO updated = pendingFundIngressService.updatePendingFundIngress(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePendingFundIngressById(@PathVariable Long id) {
        log.info("Deleting Pending fund ingress with ID: {}", id);
        boolean deleted = pendingFundIngressService.deletePendingFundIngressById(id);
        if (deleted) {
            return ResponseEntity.ok("Pending FundIngress deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("Pending FundIngress deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeletePendingFundIngressServiceById(@PathVariable Long id) {
        log.info("Soft deleting PendingFundIngress with ID: {}", id);

        boolean deleted = pendingFundIngressService.softPendingFundIngressServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("PendingFundIngress soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("PendingFundIngress soft deletion failed - ID: " + id);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<PendingFundIngressDTO> uploadSplitFundIngressTemplate(
            @RequestParam("file") MultipartFile file) {
        log.info("Bulk upload build split fund ingress");

       /* if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        // BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
        // return ResponseEntity.ok(saved);
        return null;
    }

    // For split record

    @PutMapping("/{id}/split-data")
    public ResponseEntity<PendingFundIngressExtDTO> updatePendingFundSplitTransaction(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody PendingFundIngressExtDTO pendingFundIngressExtDTO, Principal principal)
    {
        log.debug("REST request to update pending fund split record : {}, {}", id, pendingFundIngressExtDTO);
        if (pendingFundIngressExtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pendingFundIngressExtDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        String currentUserName = principal.getName();
        pendingFundIngressExtDTO.getWorkflowRequestDTO().setCreatedBy(currentUserName);
        pendingFundIngressExtDTO.getWorkflowRequestDTO().setReferenceId(pendingFundIngressExtDTO.getId().toString());

        PendingFundIngressExtDTO result = pendingFundIngressService.updateSplitData(pendingFundIngressExtDTO);
        return ResponseEntity.ok(result);
    }
}
