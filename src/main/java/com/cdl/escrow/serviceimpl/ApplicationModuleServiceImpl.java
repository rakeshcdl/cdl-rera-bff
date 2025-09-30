package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.ApplicationModuleDTO;
import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ApplicationModuleMapper;
import com.cdl.escrow.repository.ApplicationModuleRepository;
import com.cdl.escrow.service.ApplicationModuleService;
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
public class ApplicationModuleServiceImpl implements ApplicationModuleService {

    private final ApplicationModuleRepository repository;

    private final ApplicationModuleMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationModuleDTO> getAllApplicationModule(Pageable pageable) {
        log.debug("Fetching all application module, page: {}", pageable.getPageNumber());
        Page<ApplicationModule> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationModuleDTO> getApplicationModuleById(Long id) {
        log.debug("Fetching application module with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public ApplicationModuleDTO saveApplicationModule(ApplicationModuleDTO applicationModuleDTO) {
        log.info("Saving new application module");
        ApplicationModule entity = mapper.toEntity(applicationModuleDTO);
        ApplicationModule saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public ApplicationModuleDTO updateApplicationModule(Long id, ApplicationModuleDTO applicationModuleDTO) {
        log.info("Updating application form design with ID: {}", id);

        ApplicationModule existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Module not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ApplicationModule toUpdate = mapper.toEntity(applicationModuleDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ApplicationModule updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteApplicationModuleById(Long id) {
        log.info("Deleting application module with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Module not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softDeleteApplicationModuleById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}