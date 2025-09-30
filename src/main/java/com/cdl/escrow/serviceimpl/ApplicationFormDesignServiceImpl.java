package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.ApplicationFormDesignDTO;
import com.cdl.escrow.entity.ApplicationFormDesign;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ApplicationFormDesignMapper;
import com.cdl.escrow.repository.ApplicationFormDesignRepository;
import com.cdl.escrow.service.ApplicationFormDesignService;
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
public class ApplicationFormDesignServiceImpl implements ApplicationFormDesignService {

    private final ApplicationFormDesignRepository repository;

    private final ApplicationFormDesignMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationFormDesignDTO> getAllApplicationFormDesign(Pageable pageable) {
        log.debug("Fetching all application form design, page: {}", pageable.getPageNumber());
        Page<ApplicationFormDesign> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationFormDesignDTO> getApplicationFormDesignById(Long id) {
        log.debug("Fetching application form design with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public ApplicationFormDesignDTO saveApplicationFormDesign(ApplicationFormDesignDTO dto) {
        log.info("Saving new application form design");
        ApplicationFormDesign entity = mapper.toEntity(dto);
        ApplicationFormDesign saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public ApplicationFormDesignDTO updateApplicationFormDesign(Long id, ApplicationFormDesignDTO dto) {
        log.info("Updating application form design with ID: {}", id);

        ApplicationFormDesign existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Form design not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ApplicationFormDesign toUpdate = mapper.toEntity(dto);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ApplicationFormDesign updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteApplicationFormDesignById(Long id) {
        log.info("Deleting application form design with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Form design not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softDeleteApplicationFormDesignById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}

