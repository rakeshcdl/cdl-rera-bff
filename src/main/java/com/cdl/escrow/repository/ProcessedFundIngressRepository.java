package com.cdl.escrow.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.ProcessedFundIngress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessedFundIngressRepository extends JpaRepository<ProcessedFundIngress,Long>, JpaSpecificationExecutor<ProcessedFundIngress> {
    Optional<ProcessedFundIngress> findByIdAndDeletedFalse(Long id);
}