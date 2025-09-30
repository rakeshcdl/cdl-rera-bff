package com.cdl.escrow.repository;


import com.cdl.escrow.entity.PrimaryBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrimaryBankAccountRepository  extends JpaRepository<PrimaryBankAccount,Long> , JpaSpecificationExecutor<PrimaryBankAccount> {
    Optional<PrimaryBankAccount> findByIdAndDeletedFalse(Long id);
}
