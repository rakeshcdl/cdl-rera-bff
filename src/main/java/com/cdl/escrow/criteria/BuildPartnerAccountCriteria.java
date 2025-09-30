package com.cdl.escrow.criteria;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BuildPartnerAccountCriteria implements Serializable {

    private LongFilter id;
    private StringFilter accountType;
    private StringFilter accountNumber;
    private StringFilter ibanNumber;
    private ZonedDateTimeFilter dateOpened;
    private StringFilter accountTitle;
    private StringFilter currencyCode;
    private BooleanFilter isValidated;
    private ZonedDateTimeFilter createdAt;
    private ZonedDateTimeFilter updatedAt;
    private WorkflowStatus status;
    private BooleanFilter enabled;
    private LongFilter buildPartnerId;
}
