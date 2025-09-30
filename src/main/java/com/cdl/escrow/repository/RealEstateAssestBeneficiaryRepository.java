package com.cdl.escrow.repository;


import com.cdl.escrow.entity.RealEstateAssestBeneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateAssestBeneficiaryRepository extends JpaRepository<RealEstateAssestBeneficiary,Long> , JpaSpecificationExecutor<RealEstateAssestBeneficiary> {
    Optional<RealEstateAssestBeneficiary> findByIdAndDeletedFalse(Long id);
}
