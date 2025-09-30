package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.CapitalPartnerBankInfoCriteria;
import com.cdl.escrow.criteriaservice.CapitalPartnerBankInfoCriteriaService;
import com.cdl.escrow.dto.CapitalPartnerBankInfoDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.CapitalPartnerBankInfoRepository;
import com.cdl.escrow.service.CapitalPartnerBankInfoService;
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
@RequestMapping("/api/v1/capital-partner-bank-info")
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerBankInfoController {

   private final CapitalPartnerBankInfoService capitalPartnerBankInfoService;

   private final CapitalPartnerBankInfoCriteriaService capitalPartnerBankInfoCriteriaService;

    private final CapitalPartnerBankInfoRepository repository;

    private static final String ENTITY_NAME = "CAPITAL_PARTNER_BANK_INFO";

    @GetMapping
    public ResponseEntity<List<CapitalPartnerBankInfoDTO>> getAllCapitalPartnerBankInfosByCriteria(@ParameterObject CapitalPartnerBankInfoCriteria criteria,
                                                                                      @ParameterObject  Pageable pageable) {
        Page<CapitalPartnerBankInfoDTO> page = capitalPartnerBankInfoCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<CapitalPartnerBankInfoDTO>> getAllCapitalPartnerBankInfos(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all capital partner bank info, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerBankInfoDTO> page = capitalPartnerBankInfoService.getAllCapitalPartnerBankInfo(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CapitalPartnerBankInfoDTO> saveCapitalPartnerBankInfo(
            @Valid @RequestBody CapitalPartnerBankInfoDTO dto) {
        log.info("Creating new capital partner bank infos");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new capital partner bank info cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalPartnerBankInfoDTO saved = capitalPartnerBankInfoService.saveCapitalPartnerBankInfo(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapitalPartnerBankInfoDTO> getCapitalPartnerBankInfoById(@PathVariable Long id) {
        log.info("Fetching capital partner bank info with ID: {}", id);
        return capitalPartnerBankInfoService.getCapitalPartnerBankInfoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Capital partner bank info not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapitalPartnerBankInfoDTO> updateCapitalPartnerBankInfo(
            @PathVariable Long id,
            @Valid @RequestBody CapitalPartnerBankInfoDTO dto) {
        log.info("Updating capital partner bank info with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CapitalPartnerBankInfoDTO updated = capitalPartnerBankInfoService.updateCapitalPartnerBankInfo(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapitalPartnerBankInfoById(@PathVariable Long id) {
        log.info("Deleting capital partner bank info with ID: {}", id);
        boolean deleted = capitalPartnerBankInfoService.deleteCapitalPartnerBankInfoById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerBankInfo deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerBankInfo deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteCapitalPartnerBankInfoServiceById(@PathVariable Long id) {
        log.info("Soft deleting CapitalPartnerBankInfo with ID: {}", id);

        boolean deleted = capitalPartnerBankInfoService.softCapitalPartnerBankInfoServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerBankInfo soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerBankInfo soft deletion failed - ID: " + id);
        }
    }
}
