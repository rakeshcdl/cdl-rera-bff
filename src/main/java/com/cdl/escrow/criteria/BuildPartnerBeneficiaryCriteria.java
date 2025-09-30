package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.BooleanFilter;
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
public class BuildPartnerBeneficiaryCriteria implements Serializable {

    private LongFilter id;

    private StringFilter bpbBeneficiaryId;

    private StringFilter bpbBeneficiaryType;

    private StringFilter bpbName;

    private BooleanFilter bpbIsActive;

    private BooleanFilter bpbIsDeleted;

    private LongFilter bpbTranferTypeId;

    //private Set<BankAccountDTO> bpbBankAccountDTOS ;

    private LongFilter buildPartnerId;


}
