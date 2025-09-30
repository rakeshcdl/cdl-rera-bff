package com.cdl.escrow.repository;

import com.cdl.escrow.entity.PendingFundIngress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingFundIngressRepository extends JpaRepository<PendingFundIngress,Long>, JpaSpecificationExecutor<PendingFundIngress> {
    Optional<PendingFundIngress> findByIdAndDeletedFalse(Long id);
}