package com.cdl.escrow.repository;



import com.cdl.escrow.entity.CapitalPartnerPaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalPartnerPaymentPlanRepository  extends JpaRepository<CapitalPartnerPaymentPlan,Long>, JpaSpecificationExecutor<CapitalPartnerPaymentPlan> {
   Optional<CapitalPartnerPaymentPlan> findByIdAndDeletedFalse(Long id);
}


