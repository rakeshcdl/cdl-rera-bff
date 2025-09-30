package com.cdl.escrow.repository;

import com.cdl.escrow.entity.BuildPartnerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildPartnerContactRepository extends JpaRepository<BuildPartnerContact,Long>, JpaSpecificationExecutor<BuildPartnerContact> {
    Optional<BuildPartnerContact> findByIdAndDeletedFalse(Long id);
}
