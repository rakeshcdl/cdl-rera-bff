package com.cdl.escrow.criteria;

import com.cdl.escrow.dto.ApplicationSettingDTO;
import com.cdl.escrow.dto.BankAccountDTO;
import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.enumeration.WorkflowStatus;
import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.DoubleFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BuildPartnerFeesCriteria implements Serializable {

    private LongFilter id;

    private DoubleFilter debitAmount;

    private DoubleFilter totalAmount;

    private ZonedDateTimeFilter feeCollectionDate;

    private ZonedDateTimeFilter feeNextRecoveryDate;

    private DoubleFilter feePercentage;

    private DoubleFilter vatPercentage;

    private BooleanFilter feeCollected;

    private LongFilter bpFeeCategoryId;

    private LongFilter bpFeeFrequencyId;

    private LongFilter bpFeeCurrencyId;

    private LongFilter bpAccountTypeId;

    private WorkflowStatus status;

    private BooleanFilter enabled ;

    private LongFilter buildPartnerId;
}
