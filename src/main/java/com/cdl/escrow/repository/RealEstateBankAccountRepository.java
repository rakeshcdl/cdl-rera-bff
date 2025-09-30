package com.cdl.escrow.repository;

import com.cdl.escrow.entity.RealEstateBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealEstateBankAccountRepository extends JpaRepository<RealEstateBankAccount,Long> , JpaSpecificationExecutor<RealEstateBankAccount> {
    Optional<RealEstateBankAccount> findByIdAndDeletedFalse(Long id);
}
