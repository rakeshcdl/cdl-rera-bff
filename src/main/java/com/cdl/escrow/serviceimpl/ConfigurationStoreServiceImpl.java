package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.ConfigurationStoreDTO;
import com.cdl.escrow.entity.ConfigurationStore;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ConfigurationStoreMapper;
import com.cdl.escrow.repository.ConfigurationStoreRepository;
import com.cdl.escrow.service.ConfigurationStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigurationStoreServiceImpl implements ConfigurationStoreService {

   private final ConfigurationStoreRepository repository;

    private final ConfigurationStoreMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<ConfigurationStoreDTO> getAllConfigurationStore(Pageable pageable) {
        log.debug("Fetching all configuration store , page: {}", pageable.getPageNumber());
        Page<ConfigurationStore> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigurationStoreDTO> getConfigurationStoreById(Long id) {
        log.debug("Fetching configuration store with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public ConfigurationStoreDTO saveConfigurationStore(ConfigurationStoreDTO configurationStoreDTO) {
        log.info("Saving new configuration store");
        ConfigurationStore entity = mapper.toEntity(configurationStoreDTO);
        ConfigurationStore saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public ConfigurationStoreDTO updateConfigurationStore(Long id, ConfigurationStoreDTO configurationStoreDTO) {
        log.info("Updating configuration store with ID: {}", id);

        ConfigurationStore existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Configuration store not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ConfigurationStore toUpdate = mapper.toEntity(configurationStoreDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ConfigurationStore updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteConfigurationStoreById(Long id) {
        log.info("Deleting Configuration store  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Configuration store not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softConfigurationStoreServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
