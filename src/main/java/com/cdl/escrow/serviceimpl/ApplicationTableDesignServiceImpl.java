package com.cdl.escrow.serviceimpl;


import com.cdl.escrow.dto.ApplicationTableDesignDTO;
import com.cdl.escrow.entity.ApplicationTableDesign;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.ApplicationTableDesignMapper;
import com.cdl.escrow.repository.ApplicationTableDesignRepository;
import com.cdl.escrow.service.ApplicationTableDesignService;
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
public class ApplicationTableDesignServiceImpl implements ApplicationTableDesignService {

    private final ApplicationTableDesignRepository repository;

    private final ApplicationTableDesignMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationTableDesignDTO> getAllApplicationTableDesign(Pageable pageable) {
        log.debug("Fetching all application table design, page: {}", pageable.getPageNumber());
        Page<ApplicationTableDesign> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationTableDesignDTO> getApplicationTableDesignById(Long id) {
        log.debug("Fetching application table design with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public ApplicationTableDesignDTO saveApplicationTableDesign(ApplicationTableDesignDTO applicationTableDesignDTO) {
        log.info("Saving new application table design");
        ApplicationTableDesign entity = mapper.toEntity(applicationTableDesignDTO);
        ApplicationTableDesign saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public ApplicationTableDesignDTO updateApplicationTableDesign(Long id, ApplicationTableDesignDTO applicationTableDesignDTO) {
        log.info("Updating application table design with ID: {}", id);

        ApplicationTableDesign existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Application table design not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        ApplicationTableDesign toUpdate = mapper.toEntity(applicationTableDesignDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        ApplicationTableDesign updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteApplicationTableDesignById(Long id) {
        log.info("Deleting application table design with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Application table design not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }
}