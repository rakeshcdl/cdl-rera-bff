package com.cdl.escrow.repository;

import com.cdl.escrow.entity.SuretyBond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuretyBondRepository extends JpaRepository<SuretyBond,Long> , JpaSpecificationExecutor<SuretyBond> {
    Optional<SuretyBond> findByIdAndDeletedFalse(Long id);
}
