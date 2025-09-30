package com.cdl.escrow.serviceimpl;


import com.cdl.escrow.dto.CapitalPartnerUnitPurchaseDTO;
import com.cdl.escrow.entity.CapitalPartnerUnitPurchase;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.CapitalPartnerUnitPurchaseMapper;
import com.cdl.escrow.repository.CapitalPartnerUnitPurchaseRepository;
import com.cdl.escrow.service.CapitalPartnerUnitPurchaseService;
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
public class CapitalPartnerUnitPurchaseServiceImpl implements CapitalPartnerUnitPurchaseService {
    private final CapitalPartnerUnitPurchaseRepository repository;

    private final CapitalPartnerUnitPurchaseMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<CapitalPartnerUnitPurchaseDTO> getAllCapitalPartnerUnitPurchase(Pageable pageable) {
        log.debug("Fetching all capital partner unit purchase, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnitPurchase> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CapitalPartnerUnitPurchaseDTO> getCapitalPartnerUnitPurchaseById(Long id) {
        log.debug("Fetching capital partner unit purchase with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitPurchaseDTO saveCapitalPartnerUnitPurchase(CapitalPartnerUnitPurchaseDTO capitalPartnerUnitPurchaseDTO) {
        log.info("Saving new capital partner unit purchase");
        CapitalPartnerUnitPurchase entity = mapper.toEntity(capitalPartnerUnitPurchaseDTO);
        CapitalPartnerUnitPurchase saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public CapitalPartnerUnitPurchaseDTO updateCapitalPartnerUnitPurchase(Long id, CapitalPartnerUnitPurchaseDTO capitalPartnerUnitPurchaseDTO) {
        log.info("Updating capital partner unit purchase with ID: {}", id);

        CapitalPartnerUnitPurchase existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Capital partner unit booking not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        CapitalPartnerUnitPurchase toUpdate = mapper.toEntity(capitalPartnerUnitPurchaseDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        CapitalPartnerUnitPurchase updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteCapitalPartnerUnitPurchaseById(Long id) {
        log.info("Deleting capital partner unit purchase  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Capital partner unit purchase not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softCapitalPartnerUnitPurchaseServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
