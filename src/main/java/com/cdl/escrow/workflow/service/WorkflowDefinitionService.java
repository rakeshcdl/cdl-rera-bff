package com.cdl.escrow.workflow.service;

import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.entity.WorkflowAction;
import com.cdl.escrow.workflow.dto.WorkflowDefinitionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkflowDefinitionService {

    Page<WorkflowDefinitionDTO> getAllWorkflowDefinition(final Pageable pageable);

    Optional<WorkflowDefinitionDTO> getWorkflowDefinitionById(Long id);

    WorkflowDefinitionDTO saveWorkflowDefinition(WorkflowDefinitionDTO workflowDefinitionDTO);

    WorkflowDefinitionDTO updateWorkflowDefinition(Long id, WorkflowDefinitionDTO workflowDefinitionDTO);

    Boolean deleteWorkflowDefinitionById(Long id);

    Page<WorkflowDefinitionDTO> search(String moduleCode, String actionCode, String keyword, Pageable pageable);

    WorkflowDefinitionDTO create(WorkflowDefinitionDTO dto);

    WorkflowDefinitionDTO update(Long id, WorkflowDefinitionDTO dto);

    Page<WorkflowDefinitionDTO> advanceSearch(ApplicationModule module, WorkflowAction action, boolean active, Pageable pageable);
}
