package com.cdl.escrow.repository;


import com.cdl.escrow.entity.RealEstateAssestFeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateAssestFeeHistoryRepository extends JpaRepository<RealEstateAssestFeeHistory,Long> , JpaSpecificationExecutor<RealEstateAssestFeeHistory> {
    Optional<RealEstateAssestFeeHistory> findByIdAndDeletedFalse(Long id);
}
