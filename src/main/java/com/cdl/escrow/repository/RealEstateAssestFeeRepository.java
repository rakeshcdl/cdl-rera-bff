package com.cdl.escrow.repository;

import com.cdl.escrow.entity.RealEstateAssestFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateAssestFeeRepository extends JpaRepository<RealEstateAssestFee,Long> , JpaSpecificationExecutor<RealEstateAssestFee> {
    Optional<RealEstateAssestFee> findByIdAndDeletedFalse(Long id);
}
