package com.cdl.escrow.report.controller;

import com.cdl.escrow.report.dto.BuildPartnerReportRequest;
import com.cdl.escrow.report.dto.BuildPartnerRow;
import com.cdl.escrow.report.dto.TrustReportRequest;
import com.cdl.escrow.report.dto.TrustReportRow;
import com.cdl.escrow.report.service.BuildPartnerReportService;
import com.cdl.escrow.report.service.TrustReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business-objects")
@Slf4j
@RequiredArgsConstructor
public class BusinessObjectReportController {
    private final BuildPartnerReportService service;

    private final TrustReportService trustReportService;

    // 1) JSON (Page<BuildPartnerRow>)
    @PostMapping("/build-partner")
    public Page<BuildPartnerRow> report(@RequestBody BuildPartnerReportRequest req) {
        return service.page(req);
    }

    // 2) Excel (.xlsx) downloadable
    @PostMapping("/build-partner/download")
    public ResponseEntity<byte[]> reportXlsx(@RequestBody BuildPartnerReportRequest req) {
        Page<BuildPartnerRow> page = service.page(req);
        // choose: export current page or all rows; here we export ALL for master reports
        List<BuildPartnerRow> rows = page.getContent(); // or service.page(new BuildPartnerReportRequest(...)) to get all
        byte[] file = service.exportExcel(req, rows);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("Build-Partner-Report.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    //  Trust Report

    @PostMapping("/trust-report")
    public Page<TrustReportRow> report(@RequestBody TrustReportRequest req) {
        return trustReportService.page(req);
    }

    @PostMapping("/trust-report/download")
    public ResponseEntity<byte[]> download(@RequestBody TrustReportRequest req) {
        Page<TrustReportRow> page = trustReportService.page(req);
        byte[] file = trustReportService.exportExcel(page.getContent(), req);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("trust-report.xlsx").build());

        return ResponseEntity.ok().headers(headers).body(file);
    }

}
