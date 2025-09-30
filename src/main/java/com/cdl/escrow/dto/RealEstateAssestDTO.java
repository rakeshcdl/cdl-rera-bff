package com.cdl.escrow.dto;


import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.enumeration.WorkflowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateAssestDTO implements Serializable {
    private Long id;

    private String reaId;

    private String reaCif;

    private String reaName;

    private String reaNameLocal;

    private String reaLocation;

    private String reaReraNumber;

    private ZonedDateTime reaStartDate;

    private ZonedDateTime reaCompletionDate;

    private String reaPercentComplete;

    private Double reaConstructionCost;

    private ZonedDateTime reaAccStatusDate;

    private ZonedDateTime reaRegistrationDate;

    private Integer reaNoOfUnits;

    private String reaRemarks;

    private String reaSpecialApproval;

    private String reaManagedBy;

    private String reaBackupUser;

    private String reaRetentionPercent;

    private String reaAdditionalRetentionPercent;

    private String reaTotalRetentionPercent;

    private ZonedDateTime reaRetentionEffectiveDate;

    private String reaManagementExpenses;

    private String reaMarketingExpenses;

    private ZonedDateTime reaAccoutStatusDate;

    private String reaTeamLeadName;

    private String reaRelationshipManagerName;

    private String reaAssestRelshipManagerName;

    private Double reaRealEstateBrokerExp;

    private Double reaAdvertisementExp;

    private String reaLandOwnerName;

    private BuildPartnerDTO buildPartnerDTO;

    private ApplicationSettingDTO reaStatusDTO;

    private ApplicationSettingDTO reaTypeDTO;

    private ApplicationSettingDTO reaAccountStatusDTO;

    private ApplicationSettingDTO reaConstructionCostCurrencyDTO;

    private WorkflowStatus status;

    private ApplicationSettingDTO reaBlockPaymentTypeDTO;
    private Boolean deleted ;

    private boolean enabled ;
    // private transient Set<BankAccountDTO> bankAccountDTOS ;

    //private Set<RealEstateAssestBeneficiaryDTO> realEstateAssestBeneficiaryDTOS ;

    //private Set<RealEstateAssestClosureDTO> realEstateAssestClosureDTOS ;

    //private Set<RealEstateAssestFeeDTO> realEstateAssestFeeDTOS ;

   // private Set<RealEstateAssestFeeHistoryDTO> realEstateAssestFeeHistoryDTOS ;

   // private Set<RealEstateAssestFinancialSummaryDTO> realEstateAssestFinancialSummaryDTOS;

   // private Set<CapitalPartnerUnitDTO> capitalPartnerUnitDTOS ;

   // private Set<ProcessedFundIngressDTO> processedFundIngressDTOS ;

   // private Set<PendingFundIngressDTO> pendingFundIngressDTOS;

   // private Set<FundEgressDTO> fundEgressDTOS ;

   // private Set<SuretyBondDTO> suretyBondDTOS;

    private TaskStatusDTO taskStatusDTO;
}
