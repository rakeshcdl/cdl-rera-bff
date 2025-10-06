package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.FinancialDataRequest;
import com.cdl.escrow.report.dto.FinancialDataRow;
import com.cdl.escrow.report.repository.FinancialDataMock;
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
public class FinancialDataService {

    private static final String[] HEADERS = {
            "S.No","Project","Unit No","Owner Name",
            "Opening Balance","Collections","Refunds","Transfers",
            "Paid Out of Escrow","Paid Within Escrow","Total Cash Received","Owner Balance",
            "Oqood Amount","DLD Amount","Last Payment Date","Status","Remarks"
    };

    public Page<FinancialDataRow> page(FinancialDataRequest req) {
        String proj = req.project()==null ? "" : req.project();

        var filtered = FinancialDataMock.ROWS.stream()
                .filter(r -> proj.isBlank() || r.project().equalsIgnoreCase(proj))
                .sorted((a,b) -> a.unitNo().compareToIgnoreCase(b.unitNo()))
                .toList();

        int page = (req.page()==null || req.page()<0) ? 0 : req.page();
        int size = (req.size()==null || req.size()<1) ? 50 : req.size();
        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filtered.size());
        int end   = Math.min(start + pageable.getPageSize(), filtered.size());
        return new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());
    }

    public byte[] exportExcel(FinancialDataRequest req, List<FinancialDataRow> rows) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sh = wb.createSheet("Financial Data Report");

            // styles
            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);
            CellStyle currency = wb.createCellStyle();
            DataFormat fmt = wb.createDataFormat();
            currency.setDataFormat(fmt.getFormat("#,##0.00"));
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(fmt.getFormat("dd/mmm/yyyy"));

            // title
            Row r0 = sh.createRow(0);
            Cell c0 = r0.createCell(0);
            c0.setCellValue("Financial Data Report");
            c0.setCellStyle(bold);

            // selection criteria
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String proj = req.project()==null ? "ALL" : req.project();
            String asOn = req.asOnDate()==null ? df.format(java.time.LocalDate.now())
                    : df.format(req.asOnDate());

            Row r1 = sh.createRow(1);
            r1.createCell(0).setCellValue("Selection Criteria : Project : " + proj + " , As on date : " + asOn);

            // records found
            Row r3 = sh.createRow(3);
            r3.createCell(0).setCellValue(rows.size() + " Record(s) Found");

            // header
            Row h = sh.createRow(5);
            for (int i=0;i<HEADERS.length;i++) {
                Cell cell = h.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(bold);
            }

            // data + totals accumulation
            int r = 6;
            double totOpening=0, totColl=0, totRefunds=0, totTransfers=0,
                    totOut=0, totIn=0, totCash=0, totOwnerBal=0, totOqood=0, totDld=0;

            for (FinancialDataRow row : rows) {
                Row rr = sh.createRow(r++);
                int c = 0;
                rr.createCell(c++).setCellValue(row.serialNo());
                rr.createCell(c++).setCellValue(row.project());
                rr.createCell(c++).setCellValue(row.unitNo());
                rr.createCell(c++).setCellValue(row.ownerName());

                Cell oc = rr.createCell(c++); oc.setCellValue(row.openingBalance()); oc.setCellStyle(currency);
                Cell cc = rr.createCell(c++); cc.setCellValue(row.collections());     cc.setCellStyle(currency);
                Cell rf = rr.createCell(c++); rf.setCellValue(row.refunds());        rf.setCellStyle(currency);
                Cell tr = rr.createCell(c++); tr.setCellValue(row.transfers());      tr.setCellStyle(currency);
                Cell oe = rr.createCell(c++); oe.setCellValue(row.paidOutEscrow());  oe.setCellStyle(currency);
                Cell ie = rr.createCell(c++); ie.setCellValue(row.paidWithinEscrow()); ie.setCellStyle(currency);
                Cell tc = rr.createCell(c++); tc.setCellValue(row.totalCashReceived()); tc.setCellStyle(currency);
                Cell ob = rr.createCell(c++); ob.setCellValue(row.ownerBalance());   ob.setCellStyle(currency);
                Cell oa = rr.createCell(c++); oa.setCellValue(row.oqoodAmount());    oa.setCellStyle(currency);
                Cell da = rr.createCell(c++); da.setCellValue(row.dldAmount());      da.setCellStyle(currency);

                Cell lpd = rr.createCell(c++);
                if (row.lastPaymentDate()!=null) { lpd.setCellValue(java.sql.Date.valueOf(row.lastPaymentDate())); lpd.setCellStyle(dateStyle); }
                else { lpd.setCellValue(""); }

                rr.createCell(c++).setCellValue(row.status());
                rr.createCell(c++).setCellValue(row.remarks());

                // accumulate
                totOpening += row.openingBalance();
                totColl    += row.collections();
                totRefunds += row.refunds();
                totTransfers += row.transfers();
                totOut     += row.paidOutEscrow();
                totIn      += row.paidWithinEscrow();
                totCash    += row.totalCashReceived();
                totOwnerBal+= row.ownerBalance();
                totOqood   += row.oqoodAmount();
                totDld     += row.dldAmount();
            }

            // totals row
            Row t = sh.createRow(r);
            Cell t0 = t.createCell(0); t0.setCellValue("TOTALS"); t0.setCellStyle(bold);
            t.createCell(1).setCellValue(""); // project
            t.createCell(2).setCellValue(""); // unit
            t.createCell(3).setCellValue(""); // owner

            int tc = 4;
            double[] totals = {totOpening, totColl, totRefunds, totTransfers, totOut, totIn, totCash, totOwnerBal, totOqood, totDld};
            for (double val : totals) {
                Cell c = t.createCell(tc++); c.setCellValue(val); c.setCellStyle(currency);
            }
            // skip last payment date
            t.createCell(tc++).setCellValue(""); // last payment date
            t.createCell(tc++).setCellValue(""); // status
            t.createCell(tc).setCellValue("Summed amounts"); // remarks

            // sizes
            for (int i=0;i<HEADERS.length;i++) sh.autoSizeColumn(i);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to build Financial Data Excel", e);
        }
    }

    /** Optional blank template (no rows, just title/criteria/headers). */
    public byte[] buildBlankTemplate() {
        return exportExcel(new FinancialDataRequest("{{PROJECT}}", null, 0, 1), List.of());
    }
}
