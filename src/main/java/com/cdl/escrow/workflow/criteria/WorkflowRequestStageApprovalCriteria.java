package com.cdl.escrow.workflow.criteria;


import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageDTO;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkflowRequestStageApprovalCriteria implements Serializable {


    private LongFilter id;

    private StringFilter approverUserId;

    private StringFilter approverUsername;

    private StringFilter approverGroup;

    private StringFilter remarks;

    private ZonedDateTimeFilter decidedAt;

    private LongFilter workflowRequestStageId;

    private LongFilter taskStatusId;
}
