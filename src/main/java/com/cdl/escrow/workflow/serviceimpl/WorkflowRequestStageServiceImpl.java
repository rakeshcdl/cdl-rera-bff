package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestStageMapper;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageRepository;
import com.cdl.escrow.workflow.service.WorkflowRequestStageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowRequestStageServiceImpl implements WorkflowRequestStageService {


    private final WorkflowRequestStageRepository repository;

    private final WorkflowRequestStageMapper mapper;

    @Override
    public Page<WorkflowRequestStageDTO> getAllWorkflowRequestStage(Pageable pageable) {
        log.debug("Fetching all workflow request stage , page: {}", pageable.getPageNumber());
        Page<WorkflowRequestStage> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public Optional<WorkflowRequestStageDTO> getWorkflowRequestStageById(Long id) {
        log.debug("Fetching workflow request stage with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public WorkflowRequestStageDTO saveWorkflowRequestStage(WorkflowRequestStageDTO workflowRequestStageDTO) {
        log.info("Saving new workflow request stage");
        WorkflowRequestStage entity = mapper.toEntity(workflowRequestStageDTO);
        WorkflowRequestStage saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public WorkflowRequestStageDTO updateWorkflowRequestStage(Long id, WorkflowRequestStageDTO workflowRequestStageDTO) {
        log.info("Updating workflow request stage with ID: {}", id);

        WorkflowRequestStage existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow request stage not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowRequestStage toUpdate = mapper.toEntity(workflowRequestStageDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowRequestStage updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    public Boolean deleteWorkflowRequestStageById(Long id) {
        log.info("Deleting workflow request stage with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow request stage not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }
}
