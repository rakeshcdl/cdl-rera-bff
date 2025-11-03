package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CapitalPartnerPaymentPlanCriteria implements Serializable {

    private LongFilter id;

    private IntegerFilter cpppInstallmentNumber;

    private ZonedDateTimeFilter cpppInstallmentDate;

    private DoubleFilter cpppBookingAmount;

    private LongFilter capitalPartnerId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
