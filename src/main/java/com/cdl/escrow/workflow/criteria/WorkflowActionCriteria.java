package com.cdl.escrow.workflow.criteria;

import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkflowActionCriteria implements Serializable {

    private LongFilter id;

    private StringFilter actionKey;

    private StringFilter actionName;

    private StringFilter moduleCode;

    private StringFilter description;

    private StringFilter name;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
