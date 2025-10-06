package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.BuildPartnerReportRequest;
import com.cdl.escrow.report.dto.BuildPartnerRow;
import com.cdl.escrow.report.repository.BuildPartnerMock;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BuildPartnerReportService {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

    // Replace this with your JPA repository query; using mock for demo
    private List<BuildPartnerMock.BP> fetchAll() {
        return BuildPartnerMock.LIST;
    }

    public Page<BuildPartnerRow> page(BuildPartnerReportRequest req) {
        var all = fetchAll();

        // map to rows with serial numbers
        AtomicInteger serial = new AtomicInteger(1);
        List<BuildPartnerRow> rows = all.stream().map(bp -> new BuildPartnerRow(
                serial.getAndIncrement(),
                bp.id(), bp.bpDeveloperId(), bp.bpCifrera(), bp.bpDeveloperRegNo(),
                bp.bpName(), bp.bpMasterName(), bp.bpNameLocal(), bp.bpOnboardingDate(),
                bp.bpContactAddress(), bp.bpContactTel(), bp.bpPoBox(), bp.bpMobile(),
                bp.bpFax(), bp.bpEmail(), bp.bpLicenseNo(), bp.bpLicenseExpDate(),
                bp.bpWorldCheckFlag(), bp.bpWorldCheckRemarks(), bp.bpMigratedData(),
                bp.enabled(), bp.deleted(), bp.bpremark(), bp.bpRegulator(),
                bp.bpActiveStatus(), bp.taskStatus()
        )).toList();

        int page = (req.page() == null || req.page() < 0) ? 0 : req.page();
        int size = (req.size() == null || req.size() < 1) ? 50 : req.size();
        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), rows.size());
        int end = Math.min(start + pageable.getPageSize(), rows.size());
        List<BuildPartnerRow> content = rows.subList(start, end);

        return new PageImpl<>(content, pageable, rows.size());
    }

    public byte[] exportExcel(BuildPartnerReportRequest req, List<BuildPartnerRow> rows) {
        try (InputStream in = new ClassPathResource("templates/build-partner-master-template.xlsx").getInputStream();
             Workbook wb = new XSSFWorkbook(in)) {

            Sheet sheet = wb.getSheetAt(0); // "Build Partner Report"

            // Header placeholders
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            String from = (req.fromDate() != null) ? df.format(req.fromDate()) : "N/A";
            String to   = (req.toDate()   != null) ? df.format(req.toDate())   : "N/A";
            String status = (req.status() != null && !req.status().isBlank()) ? req.status() : "ALL";

            // A2 selection criteria
            Row r2 = sheet.getRow(1); if (r2 == null) r2 = sheet.createRow(1);
            Cell c2 = r2.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            c2.setCellValue("Selection Criteria : Period : " + from + " to " + to + " ; Status : " + status);

            // A4 records
            Row r4 = sheet.getRow(3); if (r4 == null) r4 = sheet.createRow(3);
            Cell c4 = r4.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            c4.setCellValue(rows.size() + " Record(s) Found");

            // Start writing under headers (row index 6 => Excel row 7)
            int r = 6;
            CreationHelper helper = wb.getCreationHelper();
            CellStyle dateTimeStyle = wb.createCellStyle();
            dateTimeStyle.setDataFormat(helper.createDataFormat().getFormat("dd/mmm/yyyy hh:mm"));

            for (BuildPartnerRow bp : rows) {
                Row row = sheet.createRow(r++);
                int col = 0;
                row.createCell(col++).setCellValue(bp.serialNo());
                row.createCell(col++).setCellValue(String.valueOf(bp.id() == null ? "" : bp.id()));

                row.createCell(col++).setCellValue(nvl(bp.bpDeveloperId()));
                row.createCell(col++).setCellValue(nvl(bp.bpCifrera()));
                row.createCell(col++).setCellValue(nvl(bp.bpDeveloperRegNo()));
                row.createCell(col++).setCellValue(nvl(bp.bpName()));
                row.createCell(col++).setCellValue(nvl(bp.bpMasterName()));
                row.createCell(col++).setCellValue(nvl(bp.bpNameLocal()));

                var cOnb = row.createCell(col++);
                if (bp.bpOnboardingDate() != null) {
                    cOnb.setCellValue(java.util.Date.from(bp.bpOnboardingDate().toInstant()));
                    cOnb.setCellStyle(dateTimeStyle);
                } else cOnb.setCellValue("");

                row.createCell(col++).setCellValue(nvl(bp.bpContactAddress()));
                row.createCell(col++).setCellValue(nvl(bp.bpContactTel()));
                row.createCell(col++).setCellValue(nvl(bp.bpPoBox()));
                row.createCell(col++).setCellValue(nvl(bp.bpMobile()));
                row.createCell(col++).setCellValue(nvl(bp.bpFax()));
                row.createCell(col++).setCellValue(nvl(bp.bpEmail()));
                row.createCell(col++).setCellValue(nvl(bp.bpLicenseNo()));

                var cLic = row.createCell(col++);
                if (bp.bpLicenseExpDate() != null) {
                    cLic.setCellValue(java.util.Date.from(bp.bpLicenseExpDate().toInstant()));
                    cLic.setCellStyle(dateTimeStyle);
                } else cLic.setCellValue("");

                row.createCell(col++).setCellValue(nvl(bp.bpWorldCheckFlag()));
                row.createCell(col++).setCellValue(nvl(bp.bpWorldCheckRemarks()));
                row.createCell(col++).setCellValue(bp.bpMigratedData() != null && bp.bpMigratedData() ? "Yes" : "No");
                row.createCell(col++).setCellValue(bp.enabled() != null && bp.enabled() ? "Yes" : "No");
                row.createCell(col++).setCellValue(bp.deleted() != null && bp.deleted() ? "Yes" : "No");
                row.createCell(col++).setCellValue(nvl(bp.bpremark()));
                row.createCell(col++).setCellValue(nvl(bp.bpRegulator()));
                row.createCell(col++).setCellValue(nvl(bp.bpActiveStatus()));
                row.createCell(col++).setCellValue(nvl(bp.taskStatus()));
            }

            // autosize columns
            for (int i = 0; i < 26; i++) sheet.autoSizeColumn(i);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String nvl(Object s) {
        return (s == null) ? "" : s.toString();
    }

    ///  To generate word

    public byte[] generateLetter(Map<String, String> values, LocalDate letterDate) {
        try (InputStream in = new ClassPathResource("templates/build-partner-letter-template.docx").getInputStream();
             XWPFDocument doc = new XWPFDocument(in)) {

            values.putIfAbsent("{{LETTER_DATE}}", DF.format(letterDate != null ? letterDate : LocalDate.now()));
            replaceAll(doc, values);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                doc.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    private static void replaceAll(XWPFDocument doc, Map<String, String> map) {
        for (XWPFParagraph p : doc.getParagraphs()) replaceInParagraph(p, map);
        for (XWPFTable t : doc.getTables())
            for (XWPFTableRow row : t.getRows())
                for (XWPFTableCell c : row.getTableCells())
                    for (XWPFParagraph p : c.getParagraphs()) replaceInParagraph(p, map);
    }

    private static void replaceInParagraph(XWPFParagraph p, Map<String, String> map) {
        for (XWPFRun run : p.getRuns()) {
            String t = run.getText(0);
            if (t == null) continue;
            String nt = t;
            for (var e : map.entrySet()) if (nt.contains(e.getKey())) nt = nt.replace(e.getKey(), e.getValue());
            if (!nt.equals(t)) run.setText(nt, 0);
        }
    }
}
