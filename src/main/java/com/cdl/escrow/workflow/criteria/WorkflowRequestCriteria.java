package com.cdl.escrow.workflow.criteria;

import com.cdl.escrow.filter.*;
import lombok.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkflowRequestCriteria implements Serializable {

    private LongFilter id;

    private StringFilter referenceId;

    private StringFilter referenceType;

    private StringFilter moduleName;

    private StringFilter actionKey;

    private DoubleFilter amount;

    private StringFilter currency;

    private IntegerFilter currentStageOrder;

    private StringFilter createdBy;

    private ZonedDateTimeFilter createdAt;

    private ZonedDateTimeFilter lastUpdatedAt;

    private LongFilter version;

    private LongFilter workflowDefinitionId;

    private LongFilter taskStatusId;
}
