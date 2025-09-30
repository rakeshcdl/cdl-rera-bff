/**
 * BankConfigRepository.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */


package com.cdl.escrow.repository;


import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.BankConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankConfigRepository extends JpaRepository<BankConfig,Long> {
    BankConfig findByBankCodeAndApiName(String bankCode, String apiName);

    Optional<BankConfig> findByIdAndDeletedFalse(Long id);
}
