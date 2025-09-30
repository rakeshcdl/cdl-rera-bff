package com.cdl.escrow.repository;


import com.cdl.escrow.entity.FinancialInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinancialInstitutionRepository extends JpaRepository<FinancialInstitution,Long> , JpaSpecificationExecutor<FinancialInstitution> {
    Optional<FinancialInstitution> findByIdAndDeletedFalse(Long id);
}
