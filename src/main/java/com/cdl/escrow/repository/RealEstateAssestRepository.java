package com.cdl.escrow.repository;


import com.cdl.escrow.entity.RealEstateAssest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateAssestRepository extends JpaRepository<RealEstateAssest,Long> , JpaSpecificationExecutor<RealEstateAssest> {
    Optional<RealEstateAssest> findByIdAndDeletedFalse(Long id);
}
