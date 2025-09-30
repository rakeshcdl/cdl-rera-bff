/**
 * AppLanguageCodeRepository.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 11/06/25
 */

package com.cdl.escrow.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.AppLanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppLanguageCodeRepository extends JpaRepository<AppLanguageCode, Long>, JpaSpecificationExecutor<AppLanguageCode> {

    Optional<AppLanguageCode> findByIdAndDeletedFalse(Long id);
}
