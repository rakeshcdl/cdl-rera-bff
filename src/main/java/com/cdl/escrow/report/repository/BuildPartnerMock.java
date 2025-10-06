package com.cdl.escrow.report.repository;

import java.time.ZonedDateTime;
import java.util.List;

public final class BuildPartnerMock {
    private BuildPartnerMock(){}

    public record BP(
            Long id, String bpDeveloperId, String bpCifrera, String bpDeveloperRegNo,
            String bpName, String bpMasterName, String bpNameLocal, ZonedDateTime bpOnboardingDate,
            String bpContactAddress, String bpContactTel, String bpPoBox, String bpMobile,
            String bpFax, String bpEmail, String bpLicenseNo, ZonedDateTime bpLicenseExpDate,
            String bpWorldCheckFlag, String bpWorldCheckRemarks, Boolean bpMigratedData,
            Boolean enabled, Boolean deleted, String bpremark, String bpRegulator,
            String bpActiveStatus, String taskStatus
    ) {}

    public static final List<BP> LIST = List.of(
            new BP(1L,"DEV-001","CIF-111","REG-001","Code Decode Labs","CDL Group","CDL Group",
                    ZonedDateTime.now().minusMonths(6),
                    "Line 1, India","+971-4-1234567","12345","+971-50-1111111","+971-4-7654321","info@cdl.com",
                    "LIC-9988", ZonedDateTime.now().plusYears(1),
                    "N","Onboarded successfully", true,true,false,"Active status","RERA","Active","Approved"),

            new BP(2L,"DEV-002","CIF-222","REG-002","GreenField Estates","GFE Group","GFE Group",
                    ZonedDateTime.now().minusMonths(4),
                    "Line 2, India","+971-4-2222222","67890","+971-50-2222222","+971-4-3333333","contact@gfe.com",
                    "LIC-8877", ZonedDateTime.now().plusMonths(8),
                    "Y","Watchlist cleared", false,true,false,"Follow-up required","RERA","Inactive","Pending"),

            new BP(3L,"DEV-003","CIF-333","REG-003","Bloom Technology","BT Group","BT Group",
                    ZonedDateTime.now().minusMonths(5),
                    "Line 3, India","+971-4-3333333","54321","+971-50-3333333","+971-4-1111111","info@bloom.com",
                    "LIC-7766", ZonedDateTime.now().plusYears(2),
                    "N","New client added", true,false,false,"Under review","RERA","Active","Approved"),

            new BP(4L,"DEV-004","CIF-444","REG-004","Shivam Builders","SB Group","SB Group",
                    ZonedDateTime.now().minusMonths(2),
                    "Line 4, India","+971-4-4444444","23456","+971-50-4444444","+971-4-8888888","shivam@sb.com",
                    "LIC-6655", ZonedDateTime.now().plusMonths(10),
                    "N","Review completed", true,true,true,"In progress","RERA","Active","Pending"),

            new BP(5L,"DEV-005","CIF-555","REG-005","Divya Holdings","DH Group","DH Group",
                    ZonedDateTime.now().minusMonths(7),
                    "Line 5, India","+971-4-5555555","34567","+971-50-5555555","+971-4-2222222","contact@dh.com",
                    "LIC-5544", ZonedDateTime.now().plusMonths(6),
                    "Y","Follow-up done", false,true,false,"Next review pending","RERA","Inactive","Approved"),

            new BP(6L,"DEV-006","CIF-666","REG-006","Gaurav Associates","GA Group","GA Group",
                    ZonedDateTime.now().minusMonths(8),
                    "Line 6, India","+971-4-6666666","98765","+971-50-6666666","+971-4-3333333","info@gaurav.com",
                    "LIC-4433", ZonedDateTime.now().plusYears(1),
                    "N","Process completed", true,false,true,"Actively engaged","RERA","Active","Approved"),

            new BP(7L,"DEV-007","CIF-777","REG-007","Sunita Developers","SD Group","SD Group",
                    ZonedDateTime.now().minusMonths(3),
                    "Line 7, India","+971-4-7777777","11223","+971-50-7777777","+971-4-9999999","info@sd.com",
                    "LIC-3322", ZonedDateTime.now().plusMonths(9),
                    "Y","Documents verified", false,false,false,"Verification passed","RERA","Inactive","Pending"),

            new BP(8L,"DEV-008","CIF-888","REG-008","NavBharat Infra","NI Group","NI Group",
                    ZonedDateTime.now().minusMonths(9),
                    "Line 8, India","+971-4-8888888","55667","+971-50-8888888","+971-4-5555555","info@ni.com",
                    "LIC-2211", ZonedDateTime.now().plusMonths(7),
                    "N","Verification pending", true,true,false,"Inspection required","RERA","Active","Pending"),

            new BP(9L,"DEV-009","CIF-999","REG-009","Shakti BuildTech","SBT Group","SBT Group",
                    ZonedDateTime.now().minusMonths(1),
                    "Line 9, India","+971-4-9999999","77889","+971-50-9999999","+971-4-4444444","contact@sbt.com",
                    "LIC-1100", ZonedDateTime.now().plusMonths(5),
                    "Y","Client approved", false,true,false,"Engagement ongoing","RERA","Active","Approved"),

            new BP(10L,"DEV-010","CIF-101","REG-010","Zenith Realtors","ZR Group","ZR Group",
                    ZonedDateTime.now().minusMonths(10),
                    "Line 10, India","+971-4-1010101","99887","+971-50-1010101","+971-4-2020202","info@zr.com",
                    "LIC-0099", ZonedDateTime.now().plusYears(1),
                    "N","Profile verification done", true,true,true,"Completed","RERA","Active","Approved")
    );

}
