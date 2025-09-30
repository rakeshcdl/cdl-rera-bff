package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.SuretyBondReleaseDTO;
import com.cdl.escrow.entity.SuretyBondRelease;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.SuretyBondReleaseMapper;
import com.cdl.escrow.repository.SuretyBondReleaseRepository;
import com.cdl.escrow.service.SuretyBondReleaseService;
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
public class SuretyBondReleaseServiceImpl implements SuretyBondReleaseService {

   private final SuretyBondReleaseRepository repository;

    private final SuretyBondReleaseMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<SuretyBondReleaseDTO> getAllSuretyBondRelease(Pageable pageable) {
        log.debug("Fetching all Surety Bond Releaser , page: {}", pageable.getPageNumber());
        Page<SuretyBondRelease> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SuretyBondReleaseDTO> getSuretyBondReleaseById(Long id) {
        log.debug("Fetching Surety Bond Releaser with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public SuretyBondReleaseDTO saveSuretyBondRelease(SuretyBondReleaseDTO suretyBondReleaseDTO) {
        log.info("Saving new Surety Bond Releaser");
        SuretyBondRelease entity = mapper.toEntity(suretyBondReleaseDTO);
        SuretyBondRelease saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public SuretyBondReleaseDTO updateSuretyBondRelease(Long id, SuretyBondReleaseDTO suretyBondReleaseDTO) {
        log.info("Updating Surety Bond Releaser with ID: {}", id);

        SuretyBondRelease existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Surety Bond Releaser not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        SuretyBondRelease toUpdate = mapper.toEntity(suretyBondReleaseDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        SuretyBondRelease updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteSuretyBondReleaseById(Long id) {
        log.info("Deleting Surety Bond Recovery  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Surety Bond Recovery not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softSuretyBondReleaseServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
