package com.cdl.escrow.dto;

import com.cdl.escrow.enumeration.WorkflowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildPartnerContactDTO implements Serializable {
    private Long id;

    private String bpcContactName;

    private String bpcFirstName;

    private String bpcLastName;

    private String bpcContactTelCode;

    private String bpcContactTelNo;

    private String bpcCountryMobCode;

    private String bpcContactMobNo;

    private String bpcContactEmail;

    private String bpcContactAddress;

    private String bpcContactAddressLine1;

    private String bpcContactAddressLine2;

    private String bpcContactPoBox;

    private String bpcContactFaxNo;

    private boolean enabled;

    private WorkflowStatus workflowStatus;

    private BuildPartnerDTO buildPartnerDTO;

    private Boolean deleted ;
}
