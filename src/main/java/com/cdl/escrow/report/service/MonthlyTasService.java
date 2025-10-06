package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.MonthlyTasRequest;
import com.cdl.escrow.report.dto.MonthlyTasRow;
import com.cdl.escrow.report.repository.MonthlyTasMock;
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
public class MonthlyTasService {
    private static final String[] HEADERS = {
            "S.No", "Date", "Batch No.", "TAS Reference No", "Developer", "Project",
            "Unit No", "Owner Name", "Payment Type", "Payment Mode",
            "Amount", "Bank", "Finacle Ref No.", "Service", "Error Descp."
    };

    public Page<MonthlyTasRow> page(MonthlyTasRequest req) {
        String dev  = req.developer() == null ? "" : req.developer();
        String proj = (req.project() == null || req.project().equalsIgnoreCase("ALL")) ? "" : req.project();

        var filtered = MonthlyTasMock.ROWS.stream()
                .filter(r -> dev.isBlank()  || r.developer().equalsIgnoreCase(dev))
                .filter(r -> proj.isBlank() || r.project().equalsIgnoreCase(proj))
                .filter(r -> req.fromDate()==null || !r.txnDate().isBefore(req.fromDate()))
                .filter(r -> req.toDate()==null   || !r.txnDate().isAfter(req.toDate()))
                .sorted((a,b) -> a.txnDate().compareTo(b.txnDate()))
                .toList();

        int page = (req.page()==null || req.page()<0) ? 0 : req.page();
        int size = (req.size()==null || req.size()<1) ? 50 : req.size();
        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filtered.size());
        int end   = Math.min(start + pageable.getPageSize(), filtered.size());
        return new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());
    }

    public byte[] exportExcel(MonthlyTasRequest req, List<MonthlyTasRow> rows) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sh = wb.createSheet("Monthly TAS Report");

            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);

            // Title
            Row r0 = sh.createRow(0);
            Cell c0 = r0.createCell(0);
            c0.setCellValue("Monthly TAS Report");
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
            Row h = sh.createRow(5);
            for (int i=0;i<HEADERS.length;i++) {
                Cell cell = h.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(bold);
            }

            // Data
            int r = 6;
            for (MonthlyTasRow row : rows) {
                Row rr = sh.createRow(r++);
                int c = 0;
                rr.createCell(c++).setCellValue(row.serialNo());
                rr.createCell(c++).setCellValue(df.format(row.txnDate()));
                rr.createCell(c++).setCellValue(row.batchNo());
                rr.createCell(c++).setCellValue(row.tasReferenceNo());
                rr.createCell(c++).setCellValue(row.developer());
                rr.createCell(c++).setCellValue(row.project());
                rr.createCell(c++).setCellValue(row.unitNo());
                rr.createCell(c++).setCellValue(row.ownerName());
                rr.createCell(c++).setCellValue(row.paymentType());
                rr.createCell(c++).setCellValue(row.paymentMode());
                rr.createCell(c++).setCellValue(row.amount());
                rr.createCell(c++).setCellValue(row.bankName());
                rr.createCell(c++).setCellValue(row.finacleRefNo());
                rr.createCell(c++).setCellValue(row.serviceStatus());
                rr.createCell(c++).setCellValue(row.errorDescription());
            }

            for (int i=0;i<HEADERS.length;i++) sh.autoSizeColumn(i);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to build Monthly TAS Excel", e);
        }
    }

    /** Optional: blank template without data (headers + selection criteria placeholders). */
    public byte[] buildBlankTemplate() {
        return exportExcel(
                new MonthlyTasRequest("{{DEVELOPER}}","{{PROJECT}}", null, null, null, 0, 1),
                List.of()
        );
    }
}
