
package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.AppLanguageCodeDTO;
import com.cdl.escrow.entity.AppLanguageCode;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.AppLanguageCodeMapper;
import com.cdl.escrow.repository.AppLanguageCodeRepository;
import com.cdl.escrow.service.AppLanguageCodeService;
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
public class AppLanguageCodeServiceImpl implements AppLanguageCodeService {

    private final AppLanguageCodeRepository repository;
    private final AppLanguageCodeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<AppLanguageCodeDTO> getAllAppLanguageCode(Pageable pageable) {
        log.debug("Fetching all application language code, page: {}", pageable.getPageNumber());
        Page<AppLanguageCode> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AppLanguageCodeDTO> getAppLanguageCodeById(Long id) {
        log.debug("Fetching application language code with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public AppLanguageCodeDTO saveAppLanguageCode(AppLanguageCodeDTO dto) {
        log.info("Saving new application language code");
        AppLanguageCode entity = mapper.toEntity(dto);
        AppLanguageCode saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public AppLanguageCodeDTO updateAppLanguageCode(Long id, AppLanguageCodeDTO dto) {
        log.info("Updating application language code with ID: {}", id);

        AppLanguageCode existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("language code not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        AppLanguageCode toUpdate = mapper.toEntity(dto);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        AppLanguageCode updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteAppLanguageCodeById(Long id) {
        log.info("Deleting application language code with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("language code not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softDeleteAppLanguageCodeById(Long id) {

        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
