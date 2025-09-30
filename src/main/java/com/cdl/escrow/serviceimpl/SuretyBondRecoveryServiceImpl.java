package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.SuretyBondRecoveryDTO;
import com.cdl.escrow.entity.SuretyBondRecovery;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.SuretyBondRecoveryMapper;
import com.cdl.escrow.repository.SuretyBondRecoveryRepository;
import com.cdl.escrow.service.SuretyBondRecoveryService;
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
public class SuretyBondRecoveryServiceImpl implements SuretyBondRecoveryService {

    private final SuretyBondRecoveryRepository repository;

   private final SuretyBondRecoveryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<SuretyBondRecoveryDTO> getAllSuretyBondRecovery(Pageable pageable) {
        log.debug("Fetching all Surety Bond Recovery , page: {}", pageable.getPageNumber());
        Page<SuretyBondRecovery> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SuretyBondRecoveryDTO> getSuretyBondRecoveryById(Long id) {
        log.debug("Fetching Surety Bond Recovery with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public SuretyBondRecoveryDTO saveSuretyBondRecovery(SuretyBondRecoveryDTO suretyBondRecoveryDTO) {
        log.info("Saving new Surety Bond Recovery");
        SuretyBondRecovery entity = mapper.toEntity(suretyBondRecoveryDTO);
        SuretyBondRecovery saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public SuretyBondRecoveryDTO updateSuretyBondRecovery(Long id, SuretyBondRecoveryDTO suretyBondRecoveryDTO) {
        log.info("Updating Surety Bond Recovery with ID: {}", id);

        SuretyBondRecovery existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Surety Bond Recovery not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        SuretyBondRecovery toUpdate = mapper.toEntity(suretyBondRecoveryDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        SuretyBondRecovery updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteSuretyBondRecoveryById(Long id) {
        log.info("Deleting Surety Bond Recovery  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Surety Bond Recovery not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softSuretyBondRecoveryServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
