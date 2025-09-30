package com.cdl.escrow.repository;


import com.cdl.escrow.entity.CapitalPartnerUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalPartnerUnitRepository extends JpaRepository<CapitalPartnerUnit,Long> , JpaSpecificationExecutor<CapitalPartnerUnit> {
    Optional<CapitalPartnerUnit> findByIdAndDeletedFalse(Long id);
}
