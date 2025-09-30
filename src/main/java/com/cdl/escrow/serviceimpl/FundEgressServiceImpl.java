package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.FundEgressDTO;
import com.cdl.escrow.entity.FundEgress;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.FundEgressMapper;
import com.cdl.escrow.repository.FundEgressRepository;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.service.FundEgressService;
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
public class FundEgressServiceImpl implements FundEgressService {

    private final FundEgressRepository repository;

    private final FundEgressMapper mapper;

    private final TaskStatusRepository taskStatusRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<FundEgressDTO> getAllFundEgress(Pageable pageable) {
        log.debug("Fetching all Fund Egress , page: {}", pageable.getPageNumber());
        Page<FundEgress> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FundEgressDTO> getFundEgressById(Long id) {
        log.debug("Fetching Fund Egress with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public FundEgressDTO saveFundEgress(FundEgressDTO fundEgressDTO) {
        log.info("Saving new Fund Egress");
        FundEgress entity = mapper.toEntity(fundEgressDTO);
        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("IN_PROGRESS")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "IN_PROGRESS"));
        entity.setTaskStatus(ts);

        // set any default flags if needed
        entity.setDeleted(false);
        // entity.setEnabled(true/false) as per business rule
        entity.setEnabled(true);
        FundEgress saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public FundEgressDTO updateFundEgress(Long id, FundEgressDTO fundEgressDTO) {
        log.info("Updating Fund Egress with ID: {}", id);

        FundEgress existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Fund Egress unit not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        FundEgress toUpdate = mapper.toEntity(fundEgressDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        // set default task status to IN-PROGRESS on create
        TaskStatus ts = taskStatusRepository.findByName("DRAFT")
                .orElseThrow(() -> new EntityNotFoundException(
                        "TaskStatus not found: " + "DRAFT"));
        toUpdate.setTaskStatus(ts);

        FundEgress updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteFundEgressById(Long id) {
        log.info("Deleting Fund Egress  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Fund Egress not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public void finalizeFundEgress(Long moduleId, TaskStatus status) {

        FundEgress fundEgress =  repository.findById(moduleId)
                .orElseThrow(() -> new IllegalStateException("Fund Egress not found: " + moduleId));

        fundEgress.setTaskStatus(status);
        repository.save(fundEgress);

    }

    @Override
    @Transactional
    public boolean softFundEgressServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
