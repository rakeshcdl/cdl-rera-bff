package com.cdl.escrow.workflow.service;

import com.cdl.escrow.workflow.dto.MyEngagementDTO;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkflowRequestService {

    Page<WorkflowRequestDTO> getAllWorkflowRequest(final Pageable pageable);

    Optional<WorkflowRequestDTO> getWorkflowRequestById(Long id);

    WorkflowRequestDTO saveWorkflowRequest(WorkflowRequestDTO workflowRequestDTO);

    WorkflowRequestDTO updateWorkflowRequest(Long id, WorkflowRequestDTO workflowRequestDTO);

    Boolean deleteWorkflowRequestById(Long id);

    WorkflowRequestDTO createRequest(WorkflowRequestDTO dto, String name);

}
