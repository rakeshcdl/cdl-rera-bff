package com.cdl.escrow.repository;


import com.cdl.escrow.entity.CapitalPartnerUnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalPartnerUnitTypeRepository extends JpaRepository<CapitalPartnerUnitType,Long> , JpaSpecificationExecutor<CapitalPartnerUnitType> {
    Optional<CapitalPartnerUnitType> findByIdAndDeletedFalse(Long id);
}
