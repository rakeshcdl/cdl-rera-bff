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
public class CapitalPartnerDTO implements Serializable {
    private Long id ;

    private String capitalPartnerId;

    private String capitalPartnerName;

    private String capitalPartnerMiddleName;

    private String capitalPartnerLastName;

    private Float capitalPartnerOwnershipPercentage;

    private String capitalPartnerIdNo;

    private String capitalPartnerTelephoneNo;

    private String capitalPartnerMobileNo;

    private String capitalPartnerEmail;

    private Integer capitalPartnerOwnerNumber;

    private Boolean isCurrent;

    private ZonedDateTime idExpiaryDate;

    private String capitalPartnerLocaleName;

    private ApplicationSettingDTO documentTypeDTO;

    private ApplicationSettingDTO countryOptionDTO;

    private ApplicationSettingDTO investorTypeDTO;

   private Set<CapitalPartnerBankInfoDTO> capitalPartnerBankInfoDTOS ;

    private CapitalPartnerUnitDTO capitalPartnerUnitDTO;

    private Boolean deleted ;

    private boolean enabled ;

    private TaskStatusDTO taskStatusDTO;
}
