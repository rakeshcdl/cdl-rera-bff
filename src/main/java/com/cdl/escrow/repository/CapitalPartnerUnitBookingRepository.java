package com.cdl.escrow.repository;

import com.cdl.escrow.entity.CapitalPartnerUnitBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalPartnerUnitBookingRepository extends JpaRepository<CapitalPartnerUnitBooking,Long> , JpaSpecificationExecutor<CapitalPartnerUnitBooking> {
    Optional<CapitalPartnerUnitBooking> findByIdAndDeletedFalse(Long id);
}
