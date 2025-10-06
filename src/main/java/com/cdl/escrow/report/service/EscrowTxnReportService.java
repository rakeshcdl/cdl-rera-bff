package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.EscrowTxnRequest;
import com.cdl.escrow.report.dto.EscrowTxnRow;
import com.cdl.escrow.report.repository.EscrowTxnMock;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EscrowTxnReportService {

    private static final String[] HEADERS = {
            "S.No","Developer Name","Project Name","Project RERA ID","Transaction Reference",
            "Transaction Desc","Transaction Amount","Transaction Date","Narration","Entity",
            "Scheme Code","Segment","CIF Number","Account Number","Account Name",
            "Account Currency Code (Debit)","RM Name","2nd RM Name","Team leader","Part Tran Type",
            "Ref amount","Ref Currency Code","Posted Status (Payments)","Posted UserID",
            "Verified UserID","Account Sol Id","Dth Init Sol ID","TRAN currency Code",
            "Debit Amount","Credit Amount","Tran Type","Tran SubType"
    };

    public Page<EscrowTxnRow> page(EscrowTxnRequest req) {
        String dev  = req.developer()==null ? "" : req.developer();
        String proj = (req.project()==null || req.project().equalsIgnoreCase("ALL")) ? "" : req.project();

        var filtered = EscrowTxnMock.ROWS.stream()
                .filter(r -> dev.isBlank()  || r.developerName().equalsIgnoreCase(dev))
                .filter(r -> proj.isBlank() || r.projectName().equalsIgnoreCase(proj))
                .filter(r -> req.fromDate()==null || !r.transactionDate().isBefore(req.fromDate()))
                .filter(r -> req.toDate()==null   || !r.transactionDate().isAfter(req.toDate()))
                .sorted((a,b) -> a.transactionDate().compareTo(b.transactionDate()))
                .toList();

        int page = (req.page()==null || req.page()<0) ? 0 : req.page();
        int size = (req.size()==null || req.size()<1) ? 50 : req.size();
        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filtered.size());
        int end   = Math.min(start + pageable.getPageSize(), filtered.size());
        return new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());
    }

    public byte[] exportExcel(EscrowTxnRequest req, List<EscrowTxnRow> rows) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sh = wb.createSheet("Escrow Txn Detailed");
            DataFormat fmt = wb.createDataFormat();

            // styles
            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);
            CellStyle money = wb.createCellStyle(); money.setDataFormat(fmt.getFormat("#,##0.00"));
            CellStyle date = wb.createCellStyle(); date.setDataFormat(fmt.getFormat("dd/mmm/yyyy"));

            // Title
            Row r0 = sh.createRow(0);
            Cell c0 = r0.createCell(0);
            c0.setCellValue("Escrow Transaction Detailed Report");
            c0.setCellStyle(bold);

            // Selection criteria
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String criteria = "Selection Criteria : Developer : " + nv(req.developer()) +
                    " , Project : " + nv(req.project()) +
                    " , Date range : " + (req.fromDate()!=null ? df.format(req.fromDate()) : "N/A") +
                    " – " + (req.toDate()!=null ? df.format(req.toDate()) : "N/A");
            Row r1 = sh.createRow(1);
            r1.createCell(0).setCellValue(criteria);

            // Count
            Row r3 = sh.createRow(3);
            r3.createCell(0).setCellValue(rows.size() + " Record(s) Found");

            // Headers
            Row h = sh.createRow(5);
            for (int i=0;i<HEADERS.length;i++) {
                Cell cell = h.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(bold);
            }

            // Data
            int r = 6;
            for (EscrowTxnRow row : rows) {
                Row rr = sh.createRow(r++);
                int c = 0;
                rr.createCell(c++).setCellValue(row.serialNo());
                rr.createCell(c++).setCellValue(row.developerName());
                rr.createCell(c++).setCellValue(row.projectName());
                rr.createCell(c++).setCellValue(row.projectReraId());
                rr.createCell(c++).setCellValue(row.transactionReference());
                rr.createCell(c++).setCellValue(row.transactionDesc());
                Cell amt = rr.createCell(c++); amt.setCellValue(row.transactionAmount()); amt.setCellStyle(money);
                Cell d = rr.createCell(c++); if (row.transactionDate()!=null) { d.setCellValue(java.sql.Date.valueOf(row.transactionDate())); d.setCellStyle(date); } else d.setCellValue("");
                rr.createCell(c++).setCellValue(row.narration());
                rr.createCell(c++).setCellValue(row.entity());
                rr.createCell(c++).setCellValue(row.schemeCode());
                rr.createCell(c++).setCellValue(row.segment());
                rr.createCell(c++).setCellValue(row.cifNumber());
                rr.createCell(c++).setCellValue(row.accountNumber());
                rr.createCell(c++).setCellValue(row.accountName());
                rr.createCell(c++).setCellValue(row.accountCurrencyCode());
                rr.createCell(c++).setCellValue(row.rmName());
                rr.createCell(c++).setCellValue(row.secondRmName());
                rr.createCell(c++).setCellValue(row.teamLeader());
                rr.createCell(c++).setCellValue(row.partTranType());
                Cell ra = rr.createCell(c++); ra.setCellValue(row.refAmount()); ra.setCellStyle(money);
                rr.createCell(c++).setCellValue(row.refCurrencyCode());
                rr.createCell(c++).setCellValue(row.postedStatus());
                rr.createCell(c++).setCellValue(row.postedUserId());
                rr.createCell(c++).setCellValue(row.verifiedUserId());
                rr.createCell(c++).setCellValue(row.accountSolId());
                rr.createCell(c++).setCellValue(row.dthInitSolId());
                rr.createCell(c++).setCellValue(row.tranCurrencyCode());
                Cell da = rr.createCell(c++); if (row.debitAmount()!=null) { da.setCellValue(row.debitAmount()); da.setCellStyle(money); } else da.setCellValue("");
                Cell ca = rr.createCell(c++); if (row.creditAmount()!=null){ ca.setCellValue(row.creditAmount()); ca.setCellStyle(money);} else ca.setCellValue("");
                rr.createCell(c++).setCellValue(row.tranType());
                rr.createCell(c++).setCellValue(row.tranSubType());
            }

            for (int i=0;i<HEADERS.length;i++) sh.autoSizeColumn(i);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to build Escrow Txn Excel", e);
        }
    }

    /** Blank template (no data). */
    public byte[] buildBlankTemplate() {
        return exportExcel(new EscrowTxnRequest("{{DEVELOPER}}","{{PROJECT}}", null, null, 0, 1), List.of());
    }

    private static String nv(String s){ return (s==null || s.isBlank()) ? "ALL" : s; }
}
