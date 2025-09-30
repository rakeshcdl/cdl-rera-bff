package com.cdl.escrow.criteria;


import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.DoubleFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RealEstateAssestBeneficiaryCriteria implements Serializable {

    private LongFilter id;

    private StringFilter reabBeneficiaryId;

    private StringFilter reabName;

    private DoubleFilter reabContractAmount;

    private DoubleFilter reabActualLandPrice;

    private StringFilter reabContractorName;

    private StringFilter reabType;

    private StringFilter reabBank;

    private StringFilter reabSwift;

    private StringFilter reabRoutingCode;

    private StringFilter reabAddress;

    private StringFilter reabBankAddress;

    private BooleanFilter reabIsActive;

    private BooleanFilter reabIsDeleted;

    private LongFilter reabTranferTypeId;

    private LongFilter reabExpenseTypeId;

    private LongFilter reabVendorSubTypeId;

    private LongFilter reabContractorSubTypeId;

    private LongFilter reabInfrastructureCategoryId;

    private LongFilter reabSalesCategoryId;

   private LongFilter realEstateAssestId ;

   // private Set<BankAccount> bankAccountDTOS ;

   // private Set<FundEgress> fundEgressDTOS;
}
