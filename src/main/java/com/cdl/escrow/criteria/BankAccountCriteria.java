/**
 * BankAccountCriteria.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 18/07/25
 */


package com.cdl.escrow.criteria;


import com.cdl.escrow.filter.DoubleFilter;
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
public class BankAccountCriteria  implements Serializable {

    private LongFilter id;

    private StringFilter bankAccountNumber;

    private DoubleFilter bankAccountBalance;

    private StringFilter bankAccountIbanNo;

    private ZonedDateTimeFilter bankAccountOpenedDate;

    private StringFilter bankAccountTitle;

    private StringFilter bankAccountSwiftcode;

    private StringFilter bankAccountRoutingCode;

    private StringFilter bankAccountSchemeType;

    private LongFilter bankBranchId;

    private LongFilter financialInstitutionId;

    private LongFilter bankCurrencyId;

    private LongFilter primaryBankAccountId;

    private LongFilter secondaryBankAccountId;

    private LongFilter buildPartnerBeneficiaryId;

    private LongFilter realEstateAssestId;

    private LongFilter realEstateAssestBeneficiaryId;

   // private Set<CapitalPartnerBankInfoDTO> capitalPartnerBankInfoDTOS;

    private LongFilter capitalPartnerUnitId;

    //private Set<ProcessedFundIngressDTO> processedFundIngressDTOS;

   // private Set<PendingFundIngressDTO> pendingFundIngressDTOS;

    //private Set<FundEgressDTO> fundEgressDTOS;
}
