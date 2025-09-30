package com.cdl.escrow.dto;

import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.enumeration.WorkflowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildPartnerBeneficiaryDTO implements Serializable {
    private Long id;

    private String bpbBeneficiaryId;

    private String bpbBeneficiaryType;

    private String bpbName;

    private String bpbBankName;

    private String bpbSwiftCode;

    private String bpbRoutingCode;

    private String bpbAccountNumber;

    private Boolean bpbIsActive;

    private Boolean bpbIsDeleted;

    private WorkflowStatus status;
    private Boolean deleted ;

    private boolean enabled ;
    private ApplicationSettingDTO bpbTransferTypeDTO;

   // private  Set<BankAccountDTO> bpbBankAccounts ;


    //private Set<Long> buildPartnerIds = new HashSet<>();

    private Set<BuildPartnerDTO> buildPartnerDTO= new HashSet<>();

}
