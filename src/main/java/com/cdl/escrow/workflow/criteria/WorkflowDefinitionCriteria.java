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
public class WorkflowDefinitionCriteria implements Serializable {

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter version;

    private BooleanFilter isActive;

    private StringFilter createdBy;

    private ZonedDateTimeFilter createdAt;

    private BooleanFilter amountBased;

    private StringFilter moduleCode;

    private StringFilter actionCode;

    private LongFilter applicationModuleId;

    private LongFilter workflowActionId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
