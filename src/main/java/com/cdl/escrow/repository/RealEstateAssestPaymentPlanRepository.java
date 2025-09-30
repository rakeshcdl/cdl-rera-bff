package com.cdl.escrow.repository;

import com.cdl.escrow.entity.RealEstateAssestPaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RealEstateAssestPaymentPlanRepository extends JpaRepository<RealEstateAssestPaymentPlan,Long>, JpaSpecificationExecutor<RealEstateAssestPaymentPlan> {
    Optional<RealEstateAssestPaymentPlan> findByIdAndDeletedFalse(Long id);
}