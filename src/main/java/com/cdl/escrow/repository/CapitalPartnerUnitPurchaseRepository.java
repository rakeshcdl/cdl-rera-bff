package com.cdl.escrow.repository;


import com.cdl.escrow.entity.CapitalPartnerUnitPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalPartnerUnitPurchaseRepository extends JpaRepository<CapitalPartnerUnitPurchase,Long> , JpaSpecificationExecutor<CapitalPartnerUnitPurchase> {
    Optional<CapitalPartnerUnitPurchase> findByIdAndDeletedFalse(Long id);
}
