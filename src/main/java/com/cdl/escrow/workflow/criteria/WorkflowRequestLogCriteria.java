package com.cdl.escrow.workflow.criteria;

import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkflowRequestLogCriteria implements Serializable {

    private LongFilter id;

    private StringFilter eventType;

    private StringFilter eventByUser;

    private StringFilter eventByGroup;

    private ZonedDateTimeFilter eventAt;

    private LongFilter workflowRequestId;
}
