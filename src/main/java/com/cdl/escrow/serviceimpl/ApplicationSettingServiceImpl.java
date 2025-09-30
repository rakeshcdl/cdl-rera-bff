package com.cdl.escrow.serviceimpl;


import com.cdl.escrow.dto.ApplicationSettingDTO;
import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ApplicationSettingMapper;
import com.cdl.escrow.repository.ApplicationSettingRepository;
import com.cdl.escrow.service.ApplicationSettingService;
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
public class ApplicationSettingServiceImpl implements ApplicationSettingService {

    private final ApplicationSettingRepository repository;

    private final ApplicationSettingMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationSettingDTO> getAllApplicationSetting(Pageable pageable) {
        log.debug("Fetching all application settings, page: {}", pageable.getPageNumber());
        Page<ApplicationSetting> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationSettingDTO> getApplicationSettingById(Long id) {
        log.debug("Fetching application settings with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public ApplicationSettingDTO saveApplicationSetting(ApplicationSettingDTO applicationSettingDTO) {
        log.info("Saving new application setting");
        ApplicationSetting entity = mapper.toEntity(applicationSettingDTO);
        ApplicationSetting saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public ApplicationSettingDTO updateApplicationSetting(Long id, ApplicationSettingDTO applicationSettingDTO) {
        log.info("Updating application setting with ID: {}", id);

        ApplicationSetting existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Application Setting not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ApplicationSetting toUpdate = mapper.toEntity(applicationSettingDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ApplicationSetting updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteApplicationSettingById(Long id) {
        log.info("Deleting application setting with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Setting not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softApplicationSettingById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}