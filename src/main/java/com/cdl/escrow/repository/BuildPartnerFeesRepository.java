package com.cdl.escrow.repository;



import com.cdl.escrow.entity.BuildPartnerFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildPartnerFeesRepository extends JpaRepository<BuildPartnerFees,Long>, JpaSpecificationExecutor<BuildPartnerFees> {
    Optional<BuildPartnerFees> findByIdAndDeletedFalse(Long id);
}
