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
public class CapitalPartnerUnitCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter unitRefId;

    private StringFilter altUnitRefId;

    private StringFilter name;

    private BooleanFilter isResale;

    private ZonedDateTimeFilter resaleDate;

    private StringFilter unitSysId;

    private StringFilter otherFormatUnitNo;

    private StringFilter virtualAccNo;

    private StringFilter towerName;

    private StringFilter unitPlotSize;

    private StringFilter floor;

    private StringFilter noofBedroom;

    private BooleanFilter isModified;

    private LongFilter partnerUnitId;

    private LongFilter capitalPartnerUnitTypeId;

    private LongFilter realEstateAssestId;

    private LongFilter unitStatusId;

    private LongFilter propertyId;

    private LongFilter paymentPlanTypeId;

    private LongFilter capitalPartnerUnitBookingId;

    private LongFilter capitalPartnerId;

    //private Set<RealEstateAssestFeeHistoryDTO> projectFeeHistoryDTOS ;

   // private Set<CapitalPartnerUnitDTO> childCapitalPartnerUnitDTO ;

   // private Set<BankAccountDTO> bankAccountDTOS ;

    //private Set<CapitalPartnerDTO> capitalPartnerDTOS ;

    //private Set<CapitalPartnerUnitPurchaseDTO> capitalPartnerUnitPurchaseDTOS ;

   // private Set<ProcessedFundIngressDTO> processedFundIngressDTOS;

    //private Set<PendingFundIngressDTO> pendingFundIngressDTOS;

    //private Set<FundEgressDTO> fundEgressDTOS;

    //private Set<FundEgressDTO> transferFundEgressDTO;
}
