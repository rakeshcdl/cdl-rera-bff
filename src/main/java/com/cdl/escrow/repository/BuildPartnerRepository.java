package com.cdl.escrow.repository;


import com.cdl.escrow.entity.BuildPartner;
import org.hibernate.validator.constraints.Normalized;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildPartnerRepository extends JpaRepository<BuildPartner,Long> , JpaSpecificationExecutor<BuildPartner> {
    Optional<BuildPartner> findByIdAndDeletedFalse(Long id);

   /* @org.jetbrains.annotations.NotNull
    @EntityGraph(attributePaths = {
            "bpRegulator.languageTranslation",
            "bpActiveStatus.languageTranslation",
            "taskStatus",
            "buildPartnerContacts",
            "buildPartnerFees",
            "buildPartnerAccounts",
            "buildPartnerBeneficiary"
    })
    java.util.Optional<BuildPartner> findById(@NotNull Long id);*/
}
