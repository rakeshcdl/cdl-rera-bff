package com.cdl.escrow.criteria;

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
public class BuildPartnerCriteria implements Serializable {

    private LongFilter id;

    private StringFilter bpDeveloperId;

    private StringFilter bpCifrera;

    private StringFilter bpDeveloperRegNo;

    private StringFilter bpName;

    private StringFilter bpMasterName;

    private StringFilter bpNameLocal;

    private ZonedDateTimeFilter bpOnboardingDate;

    private StringFilter bpContactAddress;

    private StringFilter bpContactTel;

    private StringFilter bpPoBox;

    private StringFilter bpMobile;

    private StringFilter bpFax;

    private StringFilter bpEmail;

    private StringFilter bpLicenseNo;

    private ZonedDateTimeFilter bpLicenseExpDate;

    private StringFilter bpWorldCheckFlag;

    private StringFilter bpWorldCheckRemarks;

    private BooleanFilter bpMigratedData;

    private StringFilter bpremark;

    private LongFilter bpRegulatorId;

    private LongFilter bpActiveStatusId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;

   // private Set<BuildPartnerBeneficiaryDTO> buildPartnerBeneficiaryDTOS ;

    //private Set<BuildPartnerContactDTO> buildPartnerContactDTOS;

    //private Set<RealEstateAssestFinancialSummaryDTO> realEstateAssestFinancialSummaryDTOS ;

    //private Set<FundEgressDTO> fundEgressDTOS;
}
