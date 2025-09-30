package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.WorkflowAmountStageOverride;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowAmountStageOverrideDTO;
import com.cdl.escrow.workflow.mapper.WorkflowAmountStageOverrideMapper;
import com.cdl.escrow.workflow.repository.WorkflowAmountStageOverrideRepository;
import com.cdl.escrow.workflow.service.WorkflowAmountStageOverrideService;
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
public class WorkflowAmountStageOverrideServiceImpl implements WorkflowAmountStageOverrideService {

    private final WorkflowAmountStageOverrideRepository repository;

    private final WorkflowAmountStageOverrideMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<WorkflowAmountStageOverrideDTO> getAllWorkflowAmountStageOverride(Pageable pageable) {
        log.debug("Fetching all workflow amount stage override, page: {}", pageable.getPageNumber());
        Page<WorkflowAmountStageOverride> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkflowAmountStageOverrideDTO> getWorkflowAmountStageOverrideById(Long id) {
        log.debug("Fetching workflow amount stage override with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public WorkflowAmountStageOverrideDTO saveWorkflowAmountStageOverride(WorkflowAmountStageOverrideDTO workflowAmountStageOverrideDTO) {
        log.info("Saving new workflow amount stage override");
        WorkflowAmountStageOverride entity = mapper.toEntity(workflowAmountStageOverrideDTO);
        WorkflowAmountStageOverride saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public WorkflowAmountStageOverrideDTO updateWorkflowAmountStageOverride(Long id, WorkflowAmountStageOverrideDTO workflowAmountStageOverrideDTO) {
        log.info("Updating workflow amount stage override with ID: {}", id);

        WorkflowAmountStageOverride existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow amount stage override not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowAmountStageOverride toUpdate = mapper.toEntity(workflowAmountStageOverrideDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowAmountStageOverride updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteWorkflowAmountStageOverrideById(Long id) {
        log.info("Deleting workflow amount stage override with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow amount stage override not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }
}
