/**
 * AppLanguageTranslationRepository.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 22/07/25
 */

package com.cdl.escrow.repository;

import com.cdl.escrow.entity.AppLanguageTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppLanguageTranslationRepository extends JpaRepository<AppLanguageTranslation,Long>, JpaSpecificationExecutor<AppLanguageTranslation> {

    // For full list
    @Query("SELECT t FROM AppLanguageTranslation t " +
            "JOIN t.applicationModule m " +
            "WHERE LOWER(m.moduleName) = LOWER(:moduleName)")
    List<AppLanguageTranslation> findAllByModuleName(@Param("moduleName") String moduleName);


    Optional<AppLanguageTranslation> findByIdAndDeletedFalse(Long id);
}
