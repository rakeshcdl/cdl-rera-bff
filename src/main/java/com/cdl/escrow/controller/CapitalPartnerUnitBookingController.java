package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.CapitalPartnerUnitBookingCriteria;
import com.cdl.escrow.criteriaservice.CapitalPartnerUnitBookingCriteriaService;
import com.cdl.escrow.dto.CapitalPartnerUnitBookingDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.CapitalPartnerUnitBookingRepository;
import com.cdl.escrow.service.CapitalPartnerUnitBookingService;
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
@RequestMapping("/api/v1/capital-partner-unit-booking")
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerUnitBookingController {

   private final CapitalPartnerUnitBookingService capitalPartnerUnitBookingService;

   private final CapitalPartnerUnitBookingCriteriaService capitalPartnerUnitBookingCriteriaService;

    private final CapitalPartnerUnitBookingRepository repository;

    private static final String ENTITY_NAME = "CAPITAL_PARTNER_UNIT_BOOKING";

    @GetMapping
    public ResponseEntity<List<CapitalPartnerUnitBookingDTO>> getAllCapitalPartnerUnitBookingsByCriteria(@ParameterObject CapitalPartnerUnitBookingCriteria criteria,
                                                                                   @ParameterObject  Pageable pageable) {
        Page<CapitalPartnerUnitBookingDTO> page = capitalPartnerUnitBookingCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<CapitalPartnerUnitBookingDTO>> getAllCapitalPartnerUnitBookings(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all capital partner unit booking, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnitBookingDTO> page = capitalPartnerUnitBookingService.getAllCapitalPartnerUnitBooking(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CapitalPartnerUnitBookingDTO> saveCapitalPartnerUnitBooking(
            @Valid @RequestBody CapitalPartnerUnitBookingDTO dto) {
        log.info("Creating new capital partner unit booking");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new capital partner unit booking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalPartnerUnitBookingDTO saved = capitalPartnerUnitBookingService.saveCapitalPartnerUnitBooking(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitBookingDTO> getCapitalPartnerUnitBookingById(@PathVariable Long id) {
        log.info("Fetching capital partner unit booking with ID: {}", id);
        return capitalPartnerUnitBookingService.getCapitalPartnerUnitBookingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Capital partner unit booking not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitBookingDTO> updateCapitalPartnerUnitBooking(
            @PathVariable Long id,
            @Valid @RequestBody CapitalPartnerUnitBookingDTO dto) {
        log.info("Updating capital partner unit booking with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CapitalPartnerUnitBookingDTO updated = capitalPartnerUnitBookingService.updateCapitalPartnerUnitBooking(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapitalPartnerUnitBookingById(@PathVariable Long id) {
        log.info("Deleting capital partner unit booking with ID: {}", id);
        boolean deleted = capitalPartnerUnitBookingService.deleteCapitalPartnerUnitBookingById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnitBooking deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnitBooking deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteCapitalPartnerUnitBookingServiceById(@PathVariable Long id) {
        log.info("Soft deleting CapitalPartnerUnitBooking with ID: {}", id);

        boolean deleted = capitalPartnerUnitBookingService.softCapitalPartnerUnitBookingServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnitBooking soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnitBooking soft deletion failed - ID: " + id);
        }
    }

}
