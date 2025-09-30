package com.cdl.escrow.repository;


import com.cdl.escrow.entity.FundEgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundEgressRepository extends JpaRepository<FundEgress,Long>, JpaSpecificationExecutor<FundEgress> {
    Optional<FundEgress> findByIdAndDeletedFalse(Long id);
}
