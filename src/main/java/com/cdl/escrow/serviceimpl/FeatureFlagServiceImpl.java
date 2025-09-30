package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.FeatureFlagDTO;
import com.cdl.escrow.entity.FeatureFlag;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.FeatureFlagMapper;
import com.cdl.escrow.repository.FeatureFlagRepository;
import com.cdl.escrow.service.FeatureFlagService;
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
public class FeatureFlagServiceImpl implements FeatureFlagService {

    private final FeatureFlagRepository repository;

   private final FeatureFlagMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<FeatureFlagDTO> getAllFeatureFlag(Pageable pageable) {
        log.debug("Fetching all feature flag , page: {}", pageable.getPageNumber());
        Page<FeatureFlag> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FeatureFlagDTO> getFeatureFlagById(Long id) {
        log.debug("Fetching feature flag with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public FeatureFlagDTO saveFeatureFlag(FeatureFlagDTO featureFlagDTO) {
        log.info("Saving new feature flag");
        FeatureFlag entity = mapper.toEntity(featureFlagDTO);
        FeatureFlag saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public FeatureFlagDTO updateFeatureFlag(Long id, FeatureFlagDTO featureFlagDTO) {
        log.info("Updating feature flag with ID: {}", id);

        FeatureFlag existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Feature Flag not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        FeatureFlag toUpdate = mapper.toEntity(featureFlagDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        FeatureFlag updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteFeatureFlagById(Long id) {
        log.info("Deleting feature flag with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Feature flag not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softFeatureFlagServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
