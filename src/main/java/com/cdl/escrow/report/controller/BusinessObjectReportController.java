package com.cdl.escrow.report.controller;

import com.cdl.escrow.report.dto.*;
import com.cdl.escrow.report.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business-objects")
@Slf4j
@RequiredArgsConstructor
public class BusinessObjectReportController {
    private final BuildPartnerReportService service;

    private final TrustReportService trustReportService;

    private final UnitHistoryReportService unitHistoryReportService;

    private final TasBatchStatusService tasBatchStatusService;

    private final MonthlyReraService monthlyReraService;

    private final MonthlyTasService monthlyTasService;

    private final FinancialDataService financialDataService;

    private final EscrowTasService escrowTasService;

    private final EscrowTxnReportService escrowTxnReportService;

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

    ///  Unit History Report

// 1) JSON (paged listing)
    @PostMapping("/unit-history-report")
    public Page<UnitHistoryListRow> list(@RequestBody UnitHistoryRequest req) {
        return unitHistoryReportService.page(req);
    }

    // 2) Excel download (listing + details with hyperlinks)
    @PostMapping("/unit-history-report/download")
    public ResponseEntity<byte[]> download(@RequestBody UnitHistoryRequest req) {
        var page = unitHistoryReportService.page(req);
        byte[] file = unitHistoryReportService.exportExcel(req, page.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(
                ContentDisposition.attachment().filename("unit-history-report.xlsx").build());

        return ResponseEntity.ok().headers(headers).body(file);
    }

    // TAS Batch Status Report

    @PostMapping("/tas-batch-status-report")
    public Page<TasBatchStatusRow> list(@RequestBody TasBatchStatusRequest req) {
        return tasBatchStatusService.page(req);
    }

    @PostMapping("/tas-batch-status-report/download")
    public ResponseEntity<byte[]> download(@RequestBody TasBatchStatusRequest req) {
        Page<TasBatchStatusRow> page = tasBatchStatusService.page(req);
        byte[] file = tasBatchStatusService.exportExcel(page.getContent(), req);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("tas-batch-status-report.xlsx").build());

        return ResponseEntity.ok().headers(headers).body(file);
    }

    ///  Monthly Rera Reports

    // 1) JSON (paged)
    @PostMapping("/monthly-rera-report")
    public Page<MonthlyReraRow> list(@RequestBody MonthlyReraRequest req) {
        return monthlyReraService.page(req);
    }

    // 2) Excel download (filtered rows)
    @PostMapping("/monthly-rera-report/download")
    public ResponseEntity<byte[]> download(@RequestBody MonthlyReraRequest req) {
        Page<MonthlyReraRow> page = monthlyReraService.page(req);
        byte[] file = monthlyReraService.exportExcel(req, page.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("monthly-rera-report.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // 3) Optional: blank template
    @GetMapping("/monthly-rera-report/template")
    public ResponseEntity<byte[]> template() {
        byte[] file = monthlyReraService.buildBlankTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("monthly-rera-template.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    ///  Monthly TAS report

    // 1) JSON (paged)
    @PostMapping("/monthly-tas-report")
    public Page<MonthlyTasRow> list(@RequestBody MonthlyTasRequest req) {
        return monthlyTasService.page(req);
    }

    // 2) Excel download (filtered rows)
    @PostMapping("/monthly-tas-report/download")
    public ResponseEntity<byte[]> download(@RequestBody MonthlyTasRequest req) {
        Page<MonthlyTasRow> page = monthlyTasService.page(req);
        byte[] file = monthlyTasService.exportExcel(req, page.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("monthly-tas-report.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // 3) Optional: blank template
    @GetMapping("/monthly-tas-report/template")
    public ResponseEntity<byte[]> monthlyTasTemplate() {
        byte[] file = monthlyTasService.buildBlankTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("monthly-tas-template.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // Financial Data Summary Report

    // Optional: JSON listing (paged)
    @PostMapping("/financial-data-summary")
    public Page<FinancialDataRow> list(@RequestBody FinancialDataRequest req) {
        return financialDataService.page(req);
    }

    // Excel download (uses current page rows; change to all if preferred)
    @PostMapping("/financial-data-summary/download")
    public ResponseEntity<byte[]> download(@RequestBody FinancialDataRequest req) {
        Page<FinancialDataRow> page = financialDataService.page(req);
        byte[] file = financialDataService.exportExcel(req, page.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("financial-data-report.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // Blank template (structure only)
    @GetMapping("/financial-data-summary/template")
    public ResponseEntity<byte[]> financialDataTemplate() {
        byte[] file = financialDataService.buildBlankTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("financial-data-template.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // Escrow Regulatory TAS REPORT

    // 1) JSON listing (paged)
    @PostMapping("/escrow-regulatory-tas-report")
    public Page<EscrowTasRow> list(@RequestBody EscrowTasRequest req) {
        return escrowTasService.page(req);
    }

    // 2) Excel download (filtered/page rows; switch to all if you prefer)
    @PostMapping("/escrow-regulatory-tas-report/download")
    public ResponseEntity<byte[]> download(@RequestBody EscrowTasRequest req) {
        Page<EscrowTasRow> page = escrowTasService.page(req);
        byte[] file = escrowTasService.exportExcel(req, page.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("escrow-regulatory-tas-report.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // 3) Blank template (structure only)
    @GetMapping("/escrow-regulatory-tas-report/template")
    public ResponseEntity<byte[]> escrowRegulatoryTasTemplate() {
        byte[] file = escrowTasService.buildBlankTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("escrow-regulatory-tas-template.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // Escrow TXN report

    // 1) JSON (paged)
    @PostMapping("/escrow-txn-report")
    public Page<EscrowTxnRow> list(@RequestBody EscrowTxnRequest req) {
        return escrowTxnReportService.page(req);
    }

    // 2) Excel download (current page; switch to all if preferred)
    @PostMapping("/escrow-txn-report/download")
    public ResponseEntity<byte[]> download(@RequestBody EscrowTxnRequest req) {
        Page<EscrowTxnRow> page = escrowTxnReportService.page(req);                  // or fetch ALL if you want everything
        byte[] file = escrowTxnReportService.exportExcel(req, page.getContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(
                ContentDisposition.attachment().filename("escrow-transaction-detailed.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

    // 3) Blank template (structure only)
    @GetMapping("/escrow-txn-report/template")
    public ResponseEntity<byte[]> escrowTxnTemplate() {
        byte[] file = escrowTxnReportService.buildBlankTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(
                ContentDisposition.attachment().filename("escrow-transaction-template.xlsx").build());
        return ResponseEntity.ok().headers(headers).body(file);
    }

}
