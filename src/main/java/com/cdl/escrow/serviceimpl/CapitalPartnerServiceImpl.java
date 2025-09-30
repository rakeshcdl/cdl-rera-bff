package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.FundEgress;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.CapitalPartnerMapper;
import com.cdl.escrow.repository.CapitalPartnerRepository;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.service.CapitalPartnerService;
import jakarta.persistence.EntityNotFoundException;
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
public class CapitalPartnerServiceImpl implements CapitalPartnerService {
    private final CapitalPartnerRepository repository;

    private final CapitalPartnerMapper mapper;

    private final TaskStatusRepository taskStatusRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CapitalPartnerDTO> getAllCapitalPartner(Pageable pageable) {
        log.debug("Fetching all capital partner, page: {}", pageable.getPageNumber());
        Page<CapitalPartner> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CapitalPartnerDTO> getCapitalPartnerById(Long id) {
        log.debug("Fetching capital partner with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public CapitalPartnerDTO saveCapitalPartner(CapitalPartnerDTO capitalPartnerDTO) {
        log.info("Saving new capital partner");
        CapitalPartner entity = mapper.toEntity(capitalPartnerDTO);
        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "IN_PROGRESS"));
        entity.setTaskStatus(ts);

        // set any default flags if needed
        entity.setDeleted(false);
        // entity.setEnabled(true/false) as per business rule
        entity.setEnabled(true);
        CapitalPartner saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public CapitalPartnerDTO updateCapitalPartner(Long id, CapitalPartnerDTO capitalPartnerDTO) {
        log.info("Updating capital partner with ID: {}", id);

        CapitalPartner existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Capital partner not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        CapitalPartner toUpdate = mapper.toEntity(capitalPartnerDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved
        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("DRAFT")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "DRAFT"));
        toUpdate.setTaskStatus(ts);
        CapitalPartner updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteCapitalPartnerById(Long id) {
        log.info("Deleting capital partner  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Capital partner not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softCapitalPartnerServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }

    @Override
    public void finalizeCapitalPartner(Long moduleId, TaskStatus status) {

        CapitalPartner capitalPartner =  repository.findById(moduleId)
                .orElseThrow(() -> new IllegalStateException("Capital Partner not found: " + moduleId));

        capitalPartner.setTaskStatus(status);
        repository.save(capitalPartner);
    }
}
