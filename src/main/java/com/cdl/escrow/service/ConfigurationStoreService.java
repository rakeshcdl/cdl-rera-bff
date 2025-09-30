package com.cdl.escrow.service;

import com.cdl.escrow.dto.ConfigurationStoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ConfigurationStoreService {
    Page<ConfigurationStoreDTO> getAllConfigurationStore(final Pageable pageable);

    Optional<ConfigurationStoreDTO> getConfigurationStoreById(Long id);

    ConfigurationStoreDTO saveConfigurationStore(ConfigurationStoreDTO configurationStoreDTO);

    ConfigurationStoreDTO updateConfigurationStore(Long id, ConfigurationStoreDTO configurationStoreDTO);

    Boolean deleteConfigurationStoreById(Long id);

    boolean softConfigurationStoreServiceById(Long id);
}
