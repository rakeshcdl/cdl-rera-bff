package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.SuretyBondDTO;
import com.cdl.escrow.entity.PendingFundIngress;
import com.cdl.escrow.entity.SuretyBond;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.SuretyBondMapper;
import com.cdl.escrow.repository.SuretyBondRepository;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.service.SuretyBondService;
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
public class SuretyBondServiceImpl implements SuretyBondService {

    private final SuretyBondRepository repository;

    private final SuretyBondMapper mapper;

    private final TaskStatusRepository taskStatusRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<SuretyBondDTO> getAllSuretyBond(Pageable pageable) {
        log.debug("Fetching all Surety Bond  , page: {}", pageable.getPageNumber());
        Page<SuretyBond> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SuretyBondDTO> getSuretyBondById(Long id) {
        log.debug("Fetching Surety Bond with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public SuretyBondDTO saveSuretyBond(SuretyBondDTO suretyBondDTO) {
        log.info("Saving new Surety Bond");
        SuretyBond entity = mapper.toEntity(suretyBondDTO);

        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "IN_PROGRESS"));
        entity.setTaskStatus(ts);

        // set any default flags if needed
        entity.setDeleted(false);
        // entity.setEnabled(true/false) as per business rule
        entity.setEnabled(true);

        SuretyBond saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public SuretyBondDTO updateSuretyBond(Long id, SuretyBondDTO suretyBondDTO) {
        log.info("Updating Surety Bond  with ID: {}", id);

        SuretyBond existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Surety Bond not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        SuretyBond toUpdate = mapper.toEntity(suretyBondDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved
        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("DRAFT")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "DRAFT"));
        toUpdate.setTaskStatus(ts);
        SuretyBond updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteSuretyBondById(Long id) {
        log.info("Deleting Surety Bond  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Surety Bond not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softSuretyBondServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }

    @Override
    public void finalizeSuretyBond(Long moduleId, TaskStatus status) {

        SuretyBond suretyBond =  repository.findById(moduleId)
                .orElseThrow(() -> new IllegalStateException("Surety Bond not found: " + moduleId));

        suretyBond.setTaskStatus(status);
        repository.save(suretyBond);

    }
}
