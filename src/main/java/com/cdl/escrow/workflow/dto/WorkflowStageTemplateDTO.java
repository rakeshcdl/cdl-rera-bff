package com.cdl.escrow.workflow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowStageTemplateDTO implements Serializable {

    private Long id;

    private Integer stageOrder;

    private String stageKey;

    private String keycloakGroup;

    private int requiredApprovals ;

    private String name;

    private String description;

    private Integer slaHours;

    private WorkflowDefinitionDTO workflowDefinitionDTO;
}
