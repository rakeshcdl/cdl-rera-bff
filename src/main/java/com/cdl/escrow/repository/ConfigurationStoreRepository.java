package com.cdl.escrow.repository;


import com.cdl.escrow.entity.ConfigurationStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationStoreRepository extends JpaRepository<ConfigurationStore,Long> , JpaSpecificationExecutor<ConfigurationStore> {
    Optional<ConfigurationStore> findByIdAndDeletedFalse(Long id);
}
