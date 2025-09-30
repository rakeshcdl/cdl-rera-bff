package com.cdl.escrow.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cdl.escrow.entity.ApplicationFormDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationFormDesignRepository extends JpaRepository<ApplicationFormDesign,Long>, JpaSpecificationExecutor<ApplicationFormDesign> {
    Optional<ApplicationFormDesign> findByIdAndDeletedFalse(Long id);
}
