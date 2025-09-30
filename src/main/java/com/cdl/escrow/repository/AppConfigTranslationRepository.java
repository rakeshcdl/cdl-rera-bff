/**
 * AppConfigTranslationRepository.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 11/06/25
 */

package com.cdl.escrow.repository;

import com.cdl.escrow.entity.AppLanguageTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfigTranslationRepository extends JpaRepository <AppLanguageTranslation,Long> , JpaSpecificationExecutor<AppLanguageTranslation> {
}
