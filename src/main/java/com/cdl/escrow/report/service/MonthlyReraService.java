package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.MonthlyReraRequest;
import com.cdl.escrow.report.dto.MonthlyReraRow;
import com.cdl.escrow.report.repository.MonthlyReraMock;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MonthlyReraService {

    public Page<MonthlyReraRow> page(MonthlyReraRequest req) {
        String dev  = req.developer() == null ? "" : req.developer();
        String proj = (req.project() == null || req.project().equalsIgnoreCase("ALL")) ? "" : req.project();

        var filtered = MonthlyReraMock.ROWS.stream()
                .filter(r -> dev.isBlank() || r.developer().equalsIgnoreCase(dev))
                .filter(r -> proj.isBlank() || r.project().equalsIgnoreCase(proj))
                .filter(r -> (req.fromDate()==null || !r.date().isBefore(req.fromDate())))
                .filter(r -> (req.toDate()==null   || !r.date().isAfter(req.toDate())))
                .sorted((a,b) -> a.date().compareTo(b.date()))
                .toList();

        int page = (req.page()==null || req.page()<0) ? 0 : req.page();
        int size = (req.size()==null || req.size()<1) ? 50 : req.size();
        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filtered.size());
        int end   = Math.min(start + pageable.getPageSize(), filtered.size());
        return new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());
    }

    public byte[] exportExcel(MonthlyReraRequest req, List<MonthlyReraRow> rows) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sh = wb.createSheet("Monthly RERA Report");

            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);

            // Title
            Row r0 = sh.createRow(0);
            Cell c0 = r0.createCell(0);
            c0.setCellValue("Monthly RERA Report");
            c0.setCellStyle(bold);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dev  = req.developer()==null ? "ALL" : req.developer();
            String proj = (req.project()==null) ? "ALL" : req.project();
            String asOn = (req.asOnDate()==null) ? df.format(java.time.LocalDate.now()) : df.format(req.asOnDate());
            String range = (req.fromDate()!=null && req.toDate()!=null)
                    ? df.format(req.fromDate()) + " – " + df.format(req.toDate())
                    : "N/A";

            Row r1 = sh.createRow(1);
            r1.createCell(0).setCellValue(
                    "Selection Criteria : Developer : " + dev +
                            " , Project : " + proj +
                            " , As on date : " + asOn +
                            " , Date range : " + range
            );

            // Records found
            Row r3 = sh.createRow(3);
            r3.createCell(0).setCellValue(rows.size() + " Record(s) Found");

            // Header row
            String[] headers = {
                    "S.No","Date","Developer","Project","Unit No","Owner Name",
                    "Activity","Payment Mode","Amount","Bank","TAS Ref","Status"
            };
            Row h = sh.createRow(5);
            for (int i=0;i<headers.length;i++) {
                Cell cell = h.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(bold);
            }

            // Data
            int r = 6;
            for (MonthlyReraRow row : rows) {
                Row rr = sh.createRow(r++);
                int c = 0;
                rr.createCell(c++).setCellValue(row.serialNo());
                rr.createCell(c++).setCellValue(df.format(row.date()));
                rr.createCell(c++).setCellValue(row.developer());
                rr.createCell(c++).setCellValue(row.project());
                rr.createCell(c++).setCellValue(row.unitNo());
                rr.createCell(c++).setCellValue(row.ownerName());
                rr.createCell(c++).setCellValue(row.activity());
                rr.createCell(c++).setCellValue(row.paymentMode());
                rr.createCell(c++).setCellValue(row.amount());
                rr.createCell(c++).setCellValue(row.bankName());
                rr.createCell(c++).setCellValue(row.tasRef());
                rr.createCell(c++).setCellValue(row.status());
            }

            for (int i=0;i<headers.length;i++) sh.autoSizeColumn(i);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to build Monthly RERA Excel", e);
        }
    }

    /** Optional: blank template without data (headers + selection criteria placeholders). */
    public byte[] buildBlankTemplate() {
        return exportExcel(
                new MonthlyReraRequest("{{DEVELOPER}}","{{PROJECT}}", null, null, null, 0, 1),
                List.of() // no rows
        );
    }
}
