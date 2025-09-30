package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.SuretyBondTypeDTO;
import com.cdl.escrow.entity.SuretyBondType;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.SuretyBondTypeMapper;
import com.cdl.escrow.repository.SuretyBondTypeRepository;
import com.cdl.escrow.service.SuretyBondTypeService;
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
public class SuretyBondTypeServiceImpl implements SuretyBondTypeService {

   private final SuretyBondTypeRepository repository;

    private final SuretyBondTypeMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<SuretyBondTypeDTO> getAllSuretyBondType(Pageable pageable) {
        log.debug("Fetching all Surety Bond Type , page: {}", pageable.getPageNumber());
        Page<SuretyBondType> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SuretyBondTypeDTO> getSuretyBondTypeById(Long id) {
        log.debug("Fetching Surety Bond type with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public SuretyBondTypeDTO saveSuretyBondType(SuretyBondTypeDTO suretyBondTypeDTO) {
        log.info("Saving new Surety Bond Type");
        SuretyBondType entity = mapper.toEntity(suretyBondTypeDTO);
        SuretyBondType saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public SuretyBondTypeDTO updateSuretyBondType(Long id, SuretyBondTypeDTO suretyBondTypeDTO) {
        log.info("Updating Surety Bond Type with ID: {}", id);

        SuretyBondType existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Surety Bond Type not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        SuretyBondType toUpdate = mapper.toEntity(suretyBondTypeDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        SuretyBondType updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteSuretyBondTypeById(Long id) {
        log.info("Deleting Surety Bond Type  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Surety Bond Type not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softSuretyBondTypeServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
