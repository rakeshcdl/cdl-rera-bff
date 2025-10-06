package com.cdl.escrow.report.dto;

import java.time.ZonedDateTime;

public record BuildPartnerRow(
        int serialNo,
        Long id,
        String bpDeveloperId,
        String bpCifrera,
        String bpDeveloperRegNo,
        String bpName,
        String bpMasterName,
        String bpNameLocal,
        ZonedDateTime bpOnboardingDate,
        String bpContactAddress,
        String bpContactTel,
        String bpPoBox,
        String bpMobile,
        String bpFax,
        String bpEmail,
        String bpLicenseNo,
        ZonedDateTime bpLicenseExpDate,
        String bpWorldCheckFlag,
        String bpWorldCheckRemarks,
        Boolean bpMigratedData,
        Boolean enabled,
        Boolean deleted,
        String bpremark,
        String bpRegulator,
        String bpActiveStatus,
        String taskStatus
) {}
