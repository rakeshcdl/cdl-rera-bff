package com.cdl.escrow.report.dto;

public record BuildPartnerAssestReportDTO (String id, String developerId, String name,
                                          String escrowAccountNo, String retentionAccountNo,
                                          String subConstructionAccountNo, String constructionAccountNo) {}

