package com.cdl.escrow.serviceimpl;


import com.cdl.escrow.dto.ApplicationConfigurationDTO;
import com.cdl.escrow.entity.ApplicationConfiguration;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ApplicationConfigurationMapper;
import com.cdl.escrow.repository.ApplicationConfigurationRepository;
import com.cdl.escrow.service.ApplicationConfigurationService;
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
public class ApplicationConfigurationServiceImpl implements ApplicationConfigurationService {

    private final ApplicationConfigurationRepository repository;
    private final ApplicationConfigurationMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationConfigurationDTO> getAllApplicationConfiguration(Pageable pageable) {
        log.debug("Fetching all application configurations, page: {}", pageable.getPageNumber());
        Page<ApplicationConfiguration> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationConfigurationDTO> getApplicationConfigurationById(Long id) {
        log.debug("Fetching application configuration with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public ApplicationConfigurationDTO saveApplicationConfiguration(ApplicationConfigurationDTO dto) {
        log.info("Saving new application configuration");
        ApplicationConfiguration entity = mapper.toEntity(dto);
        ApplicationConfiguration saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public ApplicationConfigurationDTO updateApplicationConfiguration(Long id, ApplicationConfigurationDTO dto) {
        log.info("Updating application configuration with ID: {}", id);

        ApplicationConfiguration existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Configuration not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ApplicationConfiguration toUpdate = mapper.toEntity(dto);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ApplicationConfiguration updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteApplicationConfigurationById(Long id) {
        log.info("Deleting application configuration with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Configuration not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softDeleteApplicationConfigurationById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }

   /* @Override
    public Optional<ApplicationConfigurationDTO> findByKeyAndEnabledTrue(String key) {

        Optional<ApplicationConfiguration> applicationConfiguration = repository.findByConfigKeyAndEnabledTrue(key);
        return applicationConfiguration.map(configuration -> Optional.ofNullable(mapper.toDto(configuration))).orElse(null);

    }*/
}
