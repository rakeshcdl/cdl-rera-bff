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
public class CapitalPartnerCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter capitalPartnerId;

    private StringFilter capitalPartnerName;

    private StringFilter capitalPartnerMiddleName;

    private StringFilter capitalPartnerLastName;

    private FloatFilter capitalPartnerOwnershipPercentage;

    private StringFilter capitalPartnerIdNo;

    private StringFilter capitalPartnerTelephoneNo;

    private StringFilter capitalPartnerMobileNo;

    private StringFilter capitalPartnerEmail;

    private IntegerFilter capitalPartnerOwnerNumber;

    private BooleanFilter isCurrent;

    private ZonedDateTimeFilter idExpiaryDate;

    private StringFilter capitalPartnerLocaleName;

    private LongFilter documentTypeId;

    private LongFilter countryOptionId;

    private LongFilter investorTypeId;

    //private Set<CapitalPartnerBankInfoDTO> capitalPartnerBankInfoDTOS ;

    private LongFilter capitalPartnerUnitId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
