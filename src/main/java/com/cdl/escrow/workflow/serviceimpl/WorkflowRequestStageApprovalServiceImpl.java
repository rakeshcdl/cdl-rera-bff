package com.cdl.escrow.workflow.serviceimpl;


import com.cdl.escrow.entity.WorkflowRequestStageApproval;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageApprovalDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestStageApprovalMapper;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageApprovalRepository;
import com.cdl.escrow.workflow.service.WorkflowRequestStageApprovalService;
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
public class WorkflowRequestStageApprovalServiceImpl implements WorkflowRequestStageApprovalService {

    private final WorkflowRequestStageApprovalRepository repository;

    private final WorkflowRequestStageApprovalMapper mapper;

    @Override
    public Page<WorkflowRequestStageApprovalDTO> getAllWorkflowRequestStageApproval(Pageable pageable) {
        log.debug("Fetching all workflow request stage approval , page: {}", pageable.getPageNumber());
        Page<WorkflowRequestStageApproval> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public Optional<WorkflowRequestStageApprovalDTO> getWorkflowRequestStageApprovalById(Long id) {
        log.debug("Fetching workflow request stage approval with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public WorkflowRequestStageApprovalDTO saveWorkflowRequestStageApproval(WorkflowRequestStageApprovalDTO workflowRequestStageApprovalDTO) {
        log.info("Saving new workflow request stage approval");
        WorkflowRequestStageApproval entity = mapper.toEntity(workflowRequestStageApprovalDTO);
        WorkflowRequestStageApproval saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public WorkflowRequestStageApprovalDTO updateWorkflowRequestStageApproval(Long id, WorkflowRequestStageApprovalDTO workflowRequestStageApprovalDTO) {
        log.info("Updating workflow request stage approval with ID: {}", id);

        WorkflowRequestStageApproval existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow request not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowRequestStageApproval toUpdate = mapper.toEntity(workflowRequestStageApprovalDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowRequestStageApproval updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    public Boolean deleteWorkflowRequestStageApprovalById(Long id) {
        log.info("Deleting workflow request stage approval with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow request stage approval not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }
}
