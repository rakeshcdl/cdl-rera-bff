package com.cdl.escrow.repository;

import com.cdl.escrow.entity.RealEstateAssestClosure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateAssestClosureRepository extends JpaRepository<RealEstateAssestClosure,Long> , JpaSpecificationExecutor<RealEstateAssestClosure> {
    Optional<RealEstateAssestClosure> findByIdAndDeletedFalse(Long id);
}
