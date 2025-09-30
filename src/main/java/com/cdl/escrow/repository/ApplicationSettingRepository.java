package com.cdl.escrow.repository;


import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.ApplicationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationSettingRepository extends JpaRepository<ApplicationSetting,Long>, JpaSpecificationExecutor<ApplicationSetting> {
    Optional<ApplicationSetting> findByIdAndDeletedFalse(Long id);
}
