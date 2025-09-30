package com.cdl.escrow.repository;


import com.cdl.escrow.entity.SuretyBondRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuretyBondRecoveryRepository extends JpaRepository<SuretyBondRecovery,Long> , JpaSpecificationExecutor<SuretyBondRecovery> {
    Optional<SuretyBondRecovery> findByIdAndDeletedFalse(Long id);
}
