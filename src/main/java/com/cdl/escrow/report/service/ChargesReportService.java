package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.ChargesReportRequest;
import com.cdl.escrow.report.dto.ChargesReportRow;
import com.cdl.escrow.report.repository.ChargesMockData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ChargesReportService {

    public List<ChargesReportRow> generate(ChargesReportRequest req) {
        String projectId = (req.projectId() == null || req.projectId().isBlank()) ? "ALL" : req.projectId();

      /*  var filtered = ChargesMockData.CHARGES.stream()
                .filter(c -> (projectId.equalsIgnoreCase("ALL") || c.projectId().equals(projectId)))
                .filter(c -> !c.transactionDate().isBefore(req.fromDate()) && !c.transactionDate().isAfter(req.toDate()))
                .sorted((a,b) -> a.transactionDate().compareTo(b.transactionDate()))
                .collect(Collectors.toList());*/

        var filtered = ChargesMockData.CHARGES.stream()
                .sorted((a, b) -> a.transactionDate().compareTo(b.transactionDate()))
                .toList();

        AtomicInteger i = new AtomicInteger(1);
        return filtered.stream()
                .map(c -> new ChargesReportRow(
                        i.getAndIncrement(),
                        c.transactionDate(),
                        c.developerName(),
                        c.projectName(),
                        c.chargeType(),
                        c.frequency(),
                        c.perUnit(),
                        c.transactionStatus(),
                        c.rejectReason()
                ))
                .collect(Collectors.toList());
    }

    public byte[] exportToExcel(ChargesReportRequest req, List<ChargesReportRow> rows) {
        try (InputStream in = new ClassPathResource("templates/charges-report-template.xlsx").getInputStream();
             Workbook wb = new XSSFWorkbook(in)) {

            Sheet sheet = wb.getSheetAt(0); // template has one sheet titled "Charges Report"

            // Write selection criteria placeholders
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            String projectName = req.projectId() == null ? "ALL" : req.projectId();
            String period = df.format(req.fromDate()) + " to " + df.format(req.toDate());

            // A2: Selection criteria
            Row r2 = sheet.getRow(1);
            if (r2 == null) r2 = sheet.createRow(1);
            Cell c2 = r2.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            c2.setCellValue("Selection Criteria : Project Name : " + projectName + " , Period : " + period);

            // A4: Records Found
            Row r4 = sheet.getRow(3);
            if (r4 == null) r4 = sheet.createRow(3);
            Cell c4 = r4.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            c4.setCellValue(rows.size() + " Record(s) Found");

            // Data start row (after headers) = row index 6 (0-based), i.e., Excel row 7
            int startRow = 6;

            // Remove any old data rows under header
            int last = sheet.getLastRowNum();
            for (int i = last; i >= startRow; i--) {
                Row rr = sheet.getRow(i);
                if (rr != null) sheet.removeRow(rr);
            }

            CreationHelper helper = wb.getCreationHelper();
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(helper.createDataFormat().getFormat("dd/mmm/yyyy"));

            int r = startRow;
            for (ChargesReportRow row : rows) {
                Row excelRow = sheet.createRow(r++);
                int col = 0;

                excelRow.createCell(col++).setCellValue(row.serialNo());

                Cell dt = excelRow.createCell(col++);
                dt.setCellValue(java.sql.Date.valueOf(row.transactionDate()));
                dt.setCellStyle(dateStyle);

                excelRow.createCell(col++).setCellValue(row.developerName());
                excelRow.createCell(col++).setCellValue(row.projectName());
                excelRow.createCell(col++).setCellValue(row.chargeType());
                excelRow.createCell(col++).setCellValue(row.frequency());
                excelRow.createCell(col++).setCellValue(row.perUnit());
                excelRow.createCell(col++).setCellValue(row.transactionStatus());
                excelRow.createCell(col++).setCellValue(row.rejectReason());
            }

            // Autosize columns 1..9
            for (int i = 0; i < 9; i++) sheet.autoSizeColumn(i);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
