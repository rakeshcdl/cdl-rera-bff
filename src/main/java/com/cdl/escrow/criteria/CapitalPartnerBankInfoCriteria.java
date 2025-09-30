package com.cdl.escrow.criteria;

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
public class CapitalPartnerBankInfoCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter cpbiPayeeName;

    private StringFilter cpbiPayeeAddress;

    private StringFilter cpbiBankName;

    private StringFilter cpbiBankAddress;

    private StringFilter cpbiBicCode;

    private StringFilter cpbiBeneRoutingCode;

    private LongFilter bankAccountId;

    private LongFilter capitalPartnerId;

    private LongFilter payModeId;
}
