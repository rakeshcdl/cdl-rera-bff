package com.cdl.escrow.dto.record;

import com.cdl.escrow.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildPartnerRecord {

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
    private Boolean deleted;
    private String status; // enum name
    private Boolean enabled;
    private String bpremark;

    // minimal related info (could be DTOs)
    private ApplicationSettingDTO bpRegulator;
    private ApplicationSettingDTO bpActiveStatus;
    private TaskStatusDTO taskStatus;

    // Collections
    private List<BuildPartnerContactDTO> buildPartnerContacts;
    private List<BuildPartnerFeesDTO> buildPartnerFees;
    private List<BuildPartnerAccountDTO> buildPartnerAccounts;
    private List<BuildPartnerBeneficiaryDTO> buildPartnerBeneficiary;
    // getters & setters (or use Lombok)
}
