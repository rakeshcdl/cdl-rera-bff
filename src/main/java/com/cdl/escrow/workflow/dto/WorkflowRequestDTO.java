package com.cdl.escrow.workflow.dto;

import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowRequestDTO implements Serializable {

    private Long id;

    private String referenceId;

    private String referenceType;

    private String moduleName;

    private String actionKey;

    private BigDecimal amount;

    private String currency;

    //private String payloadJson; // Store entire module request payload

    private Map<String, Object> payloadJson;

    private Integer currentStageOrder;

    private String createdBy;

    private ZonedDateTime createdAt;

    private ZonedDateTime lastUpdatedAt;

    private Long version;

    private WorkflowDefinitionDTO workflowDefinitionDTO;

   private List<WorkflowRequestStageDTO> workflowRequestStageDTOS;

    private TaskStatusDTO taskStatusDTO;
}
