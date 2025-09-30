package com.cdl.escrow.repository;

import com.cdl.escrow.entity.CapitalPartnerBankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalPartnerBankInfoRepository extends JpaRepository<CapitalPartnerBankInfo,Long> , JpaSpecificationExecutor<CapitalPartnerBankInfo> {
    Optional<CapitalPartnerBankInfo> findByIdAndDeletedFalse(Long id);
}
