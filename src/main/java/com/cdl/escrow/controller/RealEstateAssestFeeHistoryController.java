package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.RealEstateAssestFeeHistoryCriteria;
import com.cdl.escrow.criteriaservice.RealEstateAssestFeeHistoryCriteriaService;
import com.cdl.escrow.dto.RealEstateAssestFeeHistoryDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateAssestFeeHistoryRepository;
import com.cdl.escrow.service.RealEstateAssestFeeHistoryService;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/real-estate-asset-fee-history")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestFeeHistoryController {

    private final RealEstateAssestFeeHistoryService realEstateAssestFeeHistoryService;

    private final RealEstateAssestFeeHistoryCriteriaService realEstateAssestFeeHistoryCriteriaService;

    private final RealEstateAssestFeeHistoryRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_FEE_HISTORY";

    @GetMapping
    public ResponseEntity<Page<RealEstateAssestFeeHistoryDTO>> getAllRealEstateAssestFeeHistoryByCriteria(@ParameterObject RealEstateAssestFeeHistoryCriteria criteria,
                                                                                            @ParameterObject  Pageable pageable) {
        Page<RealEstateAssestFeeHistoryDTO> page = realEstateAssestFeeHistoryCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateAssestFeeHistoryDTO>> getAllRealEstateAssestFeeHistory(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate assest fee history, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestFeeHistoryDTO> page = realEstateAssestFeeHistoryService.getAllRealEstateAssestFeeHistory(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateAssestFeeHistoryDTO> saveRealEstateAssestFeeHistory(
            @Valid @RequestBody RealEstateAssestFeeHistoryDTO dto) {
        log.info("Creating new real estate assest fee history");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate fee history cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstateAssestFeeHistoryDTO saved = realEstateAssestFeeHistoryService.saveRealEstateAssestFeeHistory(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateAssestFeeHistoryDTO> getRealEstateAssestFeeHistoryById(@PathVariable Long id) {
        log.info("Fetching real estate assest fee history with ID: {}", id);
        return realEstateAssestFeeHistoryService.getRealEstateAssestFeeHistoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate assest fee history not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateAssestFeeHistoryDTO> updateRealEstateAssestFeeHistory(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateAssestFeeHistoryDTO dto) {
        log.info("Updating real estate assest fee history with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateAssestFeeHistoryDTO updated = realEstateAssestFeeHistoryService.updateRealEstateAssestFeeHistory(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateAssestFeeHistoryById(@PathVariable Long id) {
        log.info("Deleting real estate assest fee history with ID: {}", id);
        boolean deleted = realEstateAssestFeeHistoryService.deleteRealEstateAssestFeeHistoryById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestFeeHistory deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestFeeHistory deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateAssestFeeHistoryServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateAssestFeeHistory with ID: {}", id);

        boolean deleted = realEstateAssestFeeHistoryService.softRealEstateAssestFeeHistoryServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestFeeHistory soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestFeeHistory soft deletion failed - ID: " + id);
        }
    }


    @PutMapping("/fee-repush/{id}")
    public ResponseEntity<RealEstateAssestFeeHistoryDTO> realEstateAssestFeeHistoryFeeRepush(@PathVariable(value = "id", required = false) final Long id)
            throws URISyntaxException {
        log.debug("REST request to update ProjectFeeHistory fee repush: {}", id);
      /*  ProjectFeeHistoryDTO response = null;
        LongFilter projectHIdFilter = new LongFilter();
        projectHIdFilter.setEquals(id);
        ProjectFeeHistoryCriteriaExt projectFeeHCriteria = new ProjectFeeHistoryCriteriaExt();
        projectFeeHCriteria.setId(projectHIdFilter);
        List<ProjectFeeHistoryDTO> projectFeeHDtos = this.projectFeeHistoryQueryServiceExt
                .findByCriteria(projectFeeHCriteria);
        if (projectFeeHDtos.size() > 0) {
            ProjectFeeHistoryDTO projectFeeHDto = projectFeeHDtos.get(0);
            Gson g = new Gson();
            VatCalculationDTO vatDto = g.fromJson(projectFeeHDto.getFeeRequestBody(), VatCalculationDTO.class);

            String narration = vatDto.getTransactions().get(0).getNarrationDetails().get(0).getValue();
            response = bankIntegrationService.feeCharges(projectFeeHDto.getProjectFee(), projectFeeHDto,
                    projectFeeHDto.getProjectFee().getProjectFeeCategory().getOptionValue(),narration,null);
        }*/
        return ResponseEntity.ok(null);
    }
}
