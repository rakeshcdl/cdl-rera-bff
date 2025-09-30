package com.cdl.escrow.repository;

import com.cdl.escrow.entity.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch,Long>, JpaSpecificationExecutor<BankBranch> {
    Optional<BankBranch> findByIdAndDeletedFalse(Long id);
}
