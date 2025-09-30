package com.cdl.escrow.service;

import com.cdl.escrow.dto.CapitalPartnerUnitBookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CapitalPartnerUnitBookingService {
    Page<CapitalPartnerUnitBookingDTO> getAllCapitalPartnerUnitBooking(final Pageable pageable);

    Optional<CapitalPartnerUnitBookingDTO> getCapitalPartnerUnitBookingById(Long id);

    CapitalPartnerUnitBookingDTO saveCapitalPartnerUnitBooking(CapitalPartnerUnitBookingDTO capitalPartnerUnitBookingDTO);

    CapitalPartnerUnitBookingDTO updateCapitalPartnerUnitBooking(Long id, CapitalPartnerUnitBookingDTO capitalPartnerUnitBookingDTO);

    Boolean deleteCapitalPartnerUnitBookingById(Long id);

    boolean softCapitalPartnerUnitBookingServiceById(Long id);
}
