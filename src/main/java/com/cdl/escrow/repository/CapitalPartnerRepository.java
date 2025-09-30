package com.cdl.escrow.repository;


import com.cdl.escrow.entity.CapitalPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalPartnerRepository extends JpaRepository<CapitalPartner,Long> , JpaSpecificationExecutor<CapitalPartner> {
    Optional<CapitalPartner> findByIdAndDeletedFalse(Long id);
}
