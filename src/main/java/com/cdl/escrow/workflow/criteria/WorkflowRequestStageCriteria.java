package com.cdl.escrow.workflow.criteria;

import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.filter.*;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageApprovalDTO;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkflowRequestStageCriteria implements Serializable {

    private LongFilter id;

    private IntegerFilter stageOrder;

    private StringFilter stageKey;

    private StringFilter keycloakGroup;

    private IntegerFilter requiredApprovals;

    private IntegerFilter approvalsObtained ;

    private ZonedDateTimeFilter startedAt;

    private ZonedDateTimeFilter completedAt;

    private LongFilter version;

    private LongFilter workflowRequestId;

    private LongFilter taskStatusId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
