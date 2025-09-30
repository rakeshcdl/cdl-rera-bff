package com.cdl.escrow.report.controller;

import com.cdl.escrow.report.dto.*;
import com.cdl.escrow.report.reportenum.ReportStatus;
import com.cdl.escrow.report.repository.AccountClosureMockData;
import com.cdl.escrow.report.service.BalanceConfirmationDocService;
import com.cdl.escrow.report.service.ChargesReportService;
import com.cdl.escrow.report.service.DocTemplateService;
import com.cdl.escrow.report.service.OpeningDocTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/account-banking")
@RequiredArgsConstructor
@Slf4j
public class AccountBankingReportController {

    private final DocTemplateService docTemplateService;

    private final OpeningDocTemplateService openingDocTemplateService;

    private final BalanceConfirmationDocService balanceConfirmationDocService;

    private final ChargesReportService chargesReportService;

    // ---- Document generator ----
    @PostMapping("/closure-documents")
    public ResponseEntity<byte[]> generate(@Valid @RequestBody ClosureRequest req) {
        // Pick random developer
        BuildPartnerReportDTO dev = AccountClosureMockData.DEVELOPERS.get(
                ThreadLocalRandom.current().nextInt(AccountClosureMockData.DEVELOPERS.size())
        );

// Pick random project
        BuildPartnerAssestReportDTO prj = AccountClosureMockData.PROJECTS.get(
                ThreadLocalRandom.current().nextInt(AccountClosureMockData.PROJECTS.size())
        );
        byte[] bytes = docTemplateService.generate(prj, dev.name(), req.status(), req.asOnDate());

        String fname = "Account-Closure-" + prj.name().replaceAll("\\s+","_") + "-" + req.asOnDate() + ".docx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentDisposition(ContentDisposition.attachment().filename(fname).build());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    // ---- Quick manual test (no body) ----
    @GetMapping("/closure-documents/sample")
    public ResponseEntity<byte[]> sample() {
        ClosureRequest req = new ClosureRequest("dev-1","prj-1",
                ReportStatus.CLOSE_ESCROW_AND_RETENTION, LocalDate.now());
        return generate(req);
    }

    @PostMapping("/opening-documents")
    public ResponseEntity<byte[]> generate(@RequestBody OpeningRequest req) {
        // Random developer
        List<BuildPartnerReportDTO> devs = AccountClosureMockData.DEVELOPERS;
        BuildPartnerReportDTO dev = devs.get(ThreadLocalRandom.current().nextInt(devs.size()));

        // Random project belonging to that developer (fallback: any project)
        List<BuildPartnerAssestReportDTO> sameDevProjects = AccountClosureMockData.projectsByDeveloper().getOrDefault(dev.id(), List.of());
        BuildPartnerAssestReportDTO prj = sameDevProjects.isEmpty()
                ? AccountClosureMockData.PROJECTS.get(ThreadLocalRandom.current().nextInt(AccountClosureMockData.PROJECTS.size()))
                : sameDevProjects.get(ThreadLocalRandom.current().nextInt(sameDevProjects.size()));

        LocalDate letterDate = (req.asOnDate() != null) ? req.asOnDate() : LocalDate.now();

        byte[] bytes = openingDocTemplateService.generate(
                prj,
                dev.name(),
                "P O Box XXXX, Dubai, UAE",           // mock address
                letterDate,
                "DR-" + dev.id().toUpperCase(),       // mock developer reg id
                prj.id(),                              // project id
                "Dubai"                                // mock location
        );

        String fname = "Account-Opening-" + prj.name().replaceAll("\\s+","_") + "-" + letterDate + ".docx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentDisposition(ContentDisposition.attachment().filename(fname).build());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @PostMapping("/balance-confirmation")
    public ResponseEntity<byte[]> generate(@RequestBody BalanceConfirmationRequest req) {
        LocalDate date = (req.asOnDate() != null) ? req.asOnDate() : LocalDate.now();

        // Random developer
        List<BuildPartnerReportDTO> devs = AccountClosureMockData.DEVELOPERS;
        BuildPartnerReportDTO dev = devs.get(ThreadLocalRandom.current().nextInt(devs.size()));

        // Random project belonging to that developer (fallback: any project)
        List<BuildPartnerAssestReportDTO> sameDevProjects = AccountClosureMockData.projectsByDeveloper().getOrDefault(dev.id(), List.of());
        BuildPartnerAssestReportDTO prj = sameDevProjects.isEmpty()
                ? AccountClosureMockData.PROJECTS.get(ThreadLocalRandom.current().nextInt(AccountClosureMockData.PROJECTS.size()))
                : sameDevProjects.get(ThreadLocalRandom.current().nextInt(sameDevProjects.size()));

        byte[] bytes = balanceConfirmationDocService.generate(
                "Code Decode Labs Pvt Ltd",     // mock developer
                "John Investor",                // mock investor
                "EBI Heights",                  // mock project
                "UNIT-101",
                "1111111111",                   // mock escrow acc no
                date
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("Balance-Confirmation-" + date + ".docx").build());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    // 1) Generate report (JSON)
    @PostMapping("/charges-report")
    public Page<ChargesReportRow> report(@RequestBody ChargesReportRequest req) {
        // basic defaults for quick testing
        LocalDate from = req.fromDate() != null ? req.fromDate() : LocalDate.now().minusMonths(1);
        LocalDate to   = req.toDate()   != null ? req.toDate()   : LocalDate.now();

        ChargesReportRequest normalized = new ChargesReportRequest(
                (req.projectId() == null ? "ALL" : req.projectId()), from, to,req.page(),req.page());


        // --- Step 2: Pagination setup ---
        int page = (req.page() == null || req.page() < 0) ? 0 : req.page();   // Spring pageable is 0-based
        int size = (req.size() == null || req.size() < 1) ? 50 : req.size();

        Pageable pageable = PageRequest.of(page, size, Sort.by("transactionDate").ascending());
        List<ChargesReportRow> pageRows = chargesReportService.generate(normalized);
        int start = Math.min((int) pageable.getOffset(), pageRows.size());
        int end = Math.min(start + pageable.getPageSize(), pageRows.size());



        // --- Step 3: Wrap in PageImpl ---
        return new PageImpl<>(pageRows, pageable, pageRows.size());
       // return chargesReportService.generate(normalized);
    }

    // 2) Download XLSX
    @PostMapping("/charges-report/download")
    public ResponseEntity<byte[]> reportXlsx(@RequestBody ChargesReportRequest req) {
        // reuse normalized dates like JSON endpoint
        LocalDate from = req.fromDate() != null ? req.fromDate() : LocalDate.now().minusMonths(1);
        LocalDate to   = req.toDate()   != null ? req.toDate()   : LocalDate.now();
        ChargesReportRequest normalized = new ChargesReportRequest(
                (req.projectId() == null ? "ALL" : req.projectId()), from, to,req.page(),req.size());

        List<ChargesReportRow> rows = chargesReportService.generate(normalized);
        byte[] file = chargesReportService.exportToExcel(normalized, rows);

        String fname = "Charges-Report-" + normalized.fromDate() + "_to_" + normalized.toDate() + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment().filename(fname).build());
        return ResponseEntity.ok().headers(headers).body(file);
    }
}
