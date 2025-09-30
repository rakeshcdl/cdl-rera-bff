/**
 * BankTokenConfigRepository.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */


package com.cdl.escrow.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.BankTokenConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankTokenConfigRepository extends JpaRepository<BankTokenConfig,Long> {

    BankTokenConfig findByBankConfigId(Long id);

    Optional<BankTokenConfig> findByIdAndDeletedFalse(Long id);
}
