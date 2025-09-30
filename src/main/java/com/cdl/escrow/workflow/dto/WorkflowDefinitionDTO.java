package com.cdl.escrow.workflow.dto;

import com.cdl.escrow.dto.ApplicationModuleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    private int version;

    private boolean isActive;

    private String createdBy;

    private ZonedDateTime createdAt;

    private boolean amountBased;

    private String moduleCode;

    private String actionCode;

    private ApplicationModuleDTO applicationModuleDTO;

    private WorkflowActionDTO workflowActionDTO;

    private List<WorkflowStageTemplateDTO> stageTemplates;

    private List<WorkflowAmountRuleDTO> amountRules;

    private Boolean enabled ;

    private Boolean deleted;

}
