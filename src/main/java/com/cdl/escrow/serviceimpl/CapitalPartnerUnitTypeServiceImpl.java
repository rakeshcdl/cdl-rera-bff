package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.CapitalPartnerUnitTypeDTO;
import com.cdl.escrow.entity.CapitalPartnerUnitType;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.CapitalPartnerUnitTypeMapper;
import com.cdl.escrow.repository.CapitalPartnerUnitTypeRepository;
import com.cdl.escrow.service.CapitalPartnerUnitTypeService;
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
public class CapitalPartnerUnitTypeServiceImpl implements CapitalPartnerUnitTypeService {

   private final CapitalPartnerUnitTypeRepository repository;

    private final CapitalPartnerUnitTypeMapper mapper;



    @Override
    @Transactional(readOnly = true)
    public Page<CapitalPartnerUnitTypeDTO> getAllCapitalPartnerUnitType(Pageable pageable) {
        log.debug("Fetching all capital partner unit type , page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnitType> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CapitalPartnerUnitTypeDTO> getCapitalPartnerUnitTypeById(Long id) {
        log.debug("Fetching capital partner unit type with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitTypeDTO saveCapitalPartnerUnitType(CapitalPartnerUnitTypeDTO capitalPartnerUnitTypeDTO) {
        log.info("Saving new capital partner unit type");
        CapitalPartnerUnitType entity = mapper.toEntity(capitalPartnerUnitTypeDTO);
        CapitalPartnerUnitType saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitTypeDTO updateCapitalPartnerUnitType(Long id, CapitalPartnerUnitTypeDTO capitalPartnerUnitTypeDTO) {
        log.info("Updating capital partner unit type with ID: {}", id);

        CapitalPartnerUnitType existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Capital partner unit type not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        CapitalPartnerUnitType toUpdate = mapper.toEntity(capitalPartnerUnitTypeDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        CapitalPartnerUnitType updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteCapitalPartnerUnitTypeById(Long id) {
        log.info("Deleting capital partner unit type with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Capital partner unit type not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softCapitalPartnerUnitTypeServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
