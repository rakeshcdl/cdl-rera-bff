/**
 * BankApiParameterRepository.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */


package com.cdl.escrow.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.BankApiParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankApiParameterRepository extends JpaRepository<BankApiParameter,Long> {
    Optional<BankApiParameter> findByIdAndDeletedFalse(Long id);
}
