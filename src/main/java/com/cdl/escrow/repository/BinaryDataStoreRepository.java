package com.cdl.escrow.repository;


import com.cdl.escrow.entity.BinaryDataStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BinaryDataStoreRepository extends JpaRepository<BinaryDataStore,Long>, JpaSpecificationExecutor<BinaryDataStore> {
    Optional<BinaryDataStore> findByIdAndDeletedFalse(Long id);
}
