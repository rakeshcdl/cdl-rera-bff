package com.cdl.escrow.repository;


import com.cdl.escrow.entity.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureFlagRepository extends JpaRepository<FeatureFlag,Long> , JpaSpecificationExecutor<FeatureFlag> {
    Optional<FeatureFlag> findByIdAndDeletedFalse(Long id);
}
