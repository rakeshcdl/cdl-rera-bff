package com.cdl.escrow.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowActionDTO implements Serializable {

    private Long id;

    private String actionKey;

    private String actionName;

    private String moduleCode;

    private String description;

    private String name;
}
