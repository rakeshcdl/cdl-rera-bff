package com.cdl.escrow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalPartnerUnitDTO implements Serializable {
    private Long id ;

    private String unitRefId;

    private String altUnitRefId;

    private String name;

    private Boolean isResale;

    private ZonedDateTime resaleDate;

    private String unitSysId;

    private String otherFormatUnitNo;

    private String virtualAccNo;

    private String towerName;

    private String unitPlotSize;

    private String floor;

    private String noofBedroom;

    private Boolean isModified;

    private CapitalPartnerUnitDTO partnerUnitDTO;

    private CapitalPartnerUnitTypeDTO capitalPartnerUnitTypeDTO;

    private RealEstateAssestDTO realEstateAssestDTO;

    private ApplicationSettingDTO unitStatusDTO;

    private ApplicationSettingDTO propertyIdDTO;

    private ApplicationSettingDTO paymentPlanTypeDTO;

    private CapitalPartnerUnitBookingDTO capitalPartnerUnitBookingDTO;

    //private Set<RealEstateAssestFeeHistoryDTO> projectFeeHistoryDTOS ;

    private Set<CapitalPartnerUnitDTO> childCapitalPartnerUnitDTO ;

    //private transient Set<BankAccountDTO> bankAccountDTOS ;

    private Set<CapitalPartnerDTO> capitalPartnerDTOS ;

   // private Set<CapitalPartnerUnitPurchaseDTO> capitalPartnerUnitPurchaseDTOS ;

   // private Set<ProcessedFundIngressDTO> processedFundIngressDTOS;

    //private Set<PendingFundIngressDTO> pendingFundIngressDTOS;

   // private Set<FundEgressDTO> fundEgressDTOS;

  //  private Set<FundEgressDTO> transferFundEgressDTO;

    private Boolean deleted ;

    private boolean enabled ;
}
