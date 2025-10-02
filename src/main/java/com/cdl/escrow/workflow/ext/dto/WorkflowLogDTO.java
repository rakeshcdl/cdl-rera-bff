package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class WorkflowLogDTO {

    private Long id;
    private String eventType;
    private String eventByUser;
    private String eventByGroup;
    private ZonedDateTime eventAt;
    private Map<String, Object> detailsJson;
}
