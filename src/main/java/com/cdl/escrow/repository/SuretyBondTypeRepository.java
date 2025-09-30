package com.cdl.escrow.repository;


import com.cdl.escrow.entity.SuretyBondType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuretyBondTypeRepository extends JpaRepository<SuretyBondType,Long> , JpaSpecificationExecutor<SuretyBondType> {
    Optional<SuretyBondType> findByIdAndDeletedFalse(Long id);
}
