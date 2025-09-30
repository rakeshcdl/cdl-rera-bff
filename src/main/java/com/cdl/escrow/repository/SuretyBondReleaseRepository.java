package com.cdl.escrow.repository;


import com.cdl.escrow.entity.SuretyBondRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuretyBondReleaseRepository extends JpaRepository<SuretyBondRelease,Long> , JpaSpecificationExecutor<SuretyBondRelease> {
    Optional<SuretyBondRelease> findByIdAndDeletedFalse(Long id);
}
