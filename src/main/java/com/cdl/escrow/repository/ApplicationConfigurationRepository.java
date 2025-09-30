package com.cdl.escrow.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.ApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationConfigurationRepository extends JpaRepository<ApplicationConfiguration,Long>, JpaSpecificationExecutor<ApplicationConfiguration> {
   Optional<ApplicationConfiguration> findByConfigKeyAndEnabledTrue(String configKey);

    Optional<ApplicationConfiguration> findByIdAndDeletedFalse(Long id);
}
