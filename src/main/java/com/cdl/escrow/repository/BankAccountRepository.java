/**
 * BankAccountRepository.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */

package com.cdl.escrow.repository;

import com.cdl.escrow.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long>, JpaSpecificationExecutor<BankAccount> {
}
