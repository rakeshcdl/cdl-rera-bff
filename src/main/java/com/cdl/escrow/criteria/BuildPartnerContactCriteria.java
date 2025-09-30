package com.cdl.escrow.criteria;

import com.cdl.escrow.enumeration.WorkflowStatus;
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
public class BuildPartnerContactCriteria implements Serializable {

    private LongFilter id;

    private StringFilter bpcContactName;

    private StringFilter bpcCcontactTelCode;

    private StringFilter bpcCcontactTelNo;

    private StringFilter bpcCcountryMobCode;

    private StringFilter bpcCcontactMobNo;

    private StringFilter bpcCcontactEmail;

    private StringFilter bpcCcontactAddress;

    private StringFilter bpcCcontactPoBox;

    private StringFilter bpcCcontactFaxNo;

    private WorkflowStatus workflowStatus;

    private LongFilter buildPartnerId;
}
