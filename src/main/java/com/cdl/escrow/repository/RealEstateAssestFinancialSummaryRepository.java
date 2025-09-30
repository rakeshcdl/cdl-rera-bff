package com.cdl.escrow.repository;

import com.cdl.escrow.entity.RealEstateAssestFinancialSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateAssestFinancialSummaryRepository extends JpaRepository<RealEstateAssestFinancialSummary,Long> , JpaSpecificationExecutor<RealEstateAssestFinancialSummary> {
    Optional<RealEstateAssestFinancialSummary> findByIdAndDeletedFalse(Long id);
}
