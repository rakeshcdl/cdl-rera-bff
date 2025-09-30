package com.cdl.escrow.repository;


import com.cdl.escrow.entity.SecondaryBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecondaryBankAccountRepository extends JpaRepository<SecondaryBankAccount,Long> , JpaSpecificationExecutor<SecondaryBankAccount> {
    Optional<SecondaryBankAccount> findByIdAndDeletedFalse(Long id);
}
