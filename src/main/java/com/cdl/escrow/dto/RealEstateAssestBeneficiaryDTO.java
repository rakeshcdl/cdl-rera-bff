package com.cdl.escrow.dto;

import com.cdl.escrow.entity.BankAccount;
import com.cdl.escrow.entity.FundEgress;
import com.cdl.escrow.entity.RealEstateAssest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateAssestBeneficiaryDTO implements Serializable {
    private Long id;

    private String reabBeneficiaryId;

    private String reabName;

    private Double reabContractAmount;

    private Double reabActualLandPrice;

    private String reabContractorName;

    private String reabType;

    private String reabBank;

    private String reabSwift;

    private String reabRoutingCode;

    private String reabAddress;

    private String reabBankAddress;

    private Boolean reabIsActive;

    private Boolean reabIsDeleted;

    private String reabBeneAccount;

    private String reabBeneIban;

    private ApplicationSettingDTO reabTranferTypeDTO;

    private ApplicationSettingDTO reabExpenseTypeDTO;

    private ApplicationSettingDTO reabVendorSubTypeDTO;

    private ApplicationSettingDTO reabContractorSubTypeDTO;

    private ApplicationSettingDTO reabInfrastructureCategoryDTO;

    private ApplicationSettingDTO reabSalesCategoryDTO;

   private Set<RealEstateAssestDTO> realEstateAssestDTO = new HashSet<>() ;

   // private Set<Long> realEstateAssestIds = new HashSet<>();

    private Boolean deleted ;

    private boolean enabled ;
    //private transient Set<BankAccount> bankAccountDTOS ;

   // private Set<FundEgress> fundEgressDTOS;
}
