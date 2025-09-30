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
public class SuretyBondCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter suretyBondReferenceNumber;

    private ZonedDateTimeFilter suretyBondDate;

    private StringFilter suretyBondName;

    private BooleanFilter suretyBondOpenEnded;

    private ZonedDateTimeFilter suretyBondExpirationDate;

    private DoubleFilter suretyBondAmount;

    private ZonedDateTimeFilter suretyBondProjectCompletionDate;

    private StringFilter suretyBondNoOfAmendment;

    private StringFilter suretyBondContractor;

    private LongFilter suretyBondTypeId;

    private LongFilter realEstateAssestId;

    private LongFilter issuerBankId;

    private LongFilter accountNumberId;

    private LongFilter suretyBondStatusId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;

   // private Set<SuretyBondRecoveryDTO> suretyBondRecoveryDTOS;

   // private Set<SuretyBondReleaseDTO> suretyBondReleaseDTOS;
}
