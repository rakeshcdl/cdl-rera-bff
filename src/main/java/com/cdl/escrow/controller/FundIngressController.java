package com.cdl.escrow.controller;


import com.cdl.escrow.dto.FundIngressDTO;
import com.cdl.escrow.service.FundIngressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fund-ingress")
@RequiredArgsConstructor
@Slf4j
public class FundIngressController {

    private final FundIngressService fundIngressService;


    @GetMapping("/find-all")
    public ResponseEntity<Page<FundIngressDTO>> getAllFundIngress(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all fund ingress, page: {}", pageable.getPageNumber());
        Page<FundIngressDTO> page = fundIngressService.getAllFundIngress(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<FundIngressDTO> saveFundIngress(
            @Valid @RequestBody FundIngressDTO dto) {
        log.info("Creating new fund ingress");
        FundIngressDTO saved = fundIngressService.saveFundIngress(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundIngressDTO> getFundIngressById(@PathVariable Long id) {
        log.info("Fetching fund ingress with ID: {}", id);
        return fundIngressService.getFundIngressById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Fund ingress not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<FundIngressDTO> updateFundIngress(
            @PathVariable Long id,
            @Valid @RequestBody FundIngressDTO dto) {
        log.info("Updating fund ingress with ID: {}", id);
        FundIngressDTO updated = fundIngressService.updateFundIngress(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFundIngressById(@PathVariable Long id) {
        log.info("Deleting fund ingress with ID: {}", id);
        boolean deleted = fundIngressService.deleteFundIngressById(id);
        if (deleted) {
            return ResponseEntity.ok("ProcessedFundIngress deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ProcessedFundIngress deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteFundIngressServiceById(@PathVariable Long id) {
        log.info("Soft deleting FundIngress with ID: {}", id);

        boolean deleted = fundIngressService.softFundIngressServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("FundIngress soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("FundIngress soft deletion failed - ID: " + id);
        }
    }
}
