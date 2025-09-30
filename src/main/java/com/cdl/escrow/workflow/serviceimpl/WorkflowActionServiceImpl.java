package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.WorkflowAction;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowActionDTO;
import com.cdl.escrow.workflow.mapper.WorkflowActionMapper;
import com.cdl.escrow.workflow.repository.WorkflowActionRepository;
import com.cdl.escrow.workflow.service.WorkflowActionService;
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
public class WorkflowActionServiceImpl implements WorkflowActionService {

    private final WorkflowActionRepository repository;

    private final WorkflowActionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<WorkflowActionDTO> getAllWorkflowAction(Pageable pageable) {
        log.debug("Fetching all workflow action, page: {}", pageable.getPageNumber());
        Page<WorkflowAction> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkflowActionDTO> getWorkflowActionById(Long id) {
        log.debug("Fetching workflow action with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public WorkflowActionDTO saveWorkflowAction(WorkflowActionDTO workflowActionDTO) {
        log.info("Saving new workflow action");
        WorkflowAction entity = mapper.toEntity(workflowActionDTO);
        WorkflowAction saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public WorkflowActionDTO updateWorkflowAction(Long id, WorkflowActionDTO workflowActionDTO) {
        log.info("Updating workflow action with ID: {}", id);

        WorkflowAction existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow action not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowAction toUpdate = mapper.toEntity(workflowActionDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowAction updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deleteWorkflowActionById(Long id) {
        log.info("Deleting workflow action with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow action not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    public Page<WorkflowActionDTO> search(String moduleCode, String keyword, Pageable pageable) {
        Page<WorkflowAction> result;

        if (moduleCode != null && keyword != null) {
            result = repository.findByModuleCodeAndActionNameContainingIgnoreCase(moduleCode, keyword, pageable);
        } else if (moduleCode != null) {
            result = repository.findByModuleCode(moduleCode, pageable);
        } else if (keyword != null) {
            result = repository.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            result = repository.findAll(pageable);
        }

        return result.map(mapper::toDto);
    }

}
