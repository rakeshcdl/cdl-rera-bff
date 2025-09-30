package com.cdl.escrow.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.ApplicationModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationModuleRepository extends JpaRepository<ApplicationModule,Long> , JpaSpecificationExecutor<ApplicationModule> {
    Optional<ApplicationModule> findByIdAndDeletedFalse(Long id);
}
