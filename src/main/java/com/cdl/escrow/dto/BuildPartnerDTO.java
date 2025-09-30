package com.cdl.escrow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildPartnerDTO implements Serializable {
 private Long id;

 private String bpDeveloperId;

 private String bpCifrera;

 private String bpDeveloperRegNo;

 private String bpName;

 private String bpMasterName;

 private String bpNameLocal;

 private ZonedDateTime bpOnboardingDate;

 private String bpContactAddress;

 private String bpContactTel;

 private String bpPoBox;

 private String bpMobile;

 private String bpFax;

 private String bpEmail;

 private String bpLicenseNo;

 private ZonedDateTime bpLicenseExpDate;

 private String bpWorldCheckFlag;

 private String bpWorldCheckRemarks;

 private Boolean bpMigratedData;

 private String bpremark;

 private ApplicationSettingDTO bpRegulatorDTO;

 private ApplicationSettingDTO bpActiveStatusDTO;

 //private Set<Long> beneficiaryIds = new HashSet<>();
 private Boolean deleted ;

 private boolean enabled ;

 private TaskStatusDTO taskStatusDTO;

  //private Set<BuildPartnerBeneficiaryDTO> buildPartnerBeneficiaryDTOS ;

 //private Set<BuildPartnerContactDTO> buildPartnerContactDTOS;

// private Set<RealEstateAssestFinancialSummaryDTO> realEstateAssestFinancialSummaryDTOS ;

// private Set<FundEgressDTO> fundEgressDTOS;
}
