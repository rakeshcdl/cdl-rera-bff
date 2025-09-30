package com.cdl.escrow.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowRequestLogDTO implements Serializable {

    private Long id;

    private String eventType;

    private String eventByUser;

    private String eventByGroup;

    private ZonedDateTime eventAt;

   // private String detailsJson;

    private Map<String, Object> detailsJson;

    private WorkflowRequestDTO workflowRequestDTO;
}
