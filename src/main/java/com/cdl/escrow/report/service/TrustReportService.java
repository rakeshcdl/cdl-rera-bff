package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.TrustReportRequest;
import com.cdl.escrow.report.dto.TrustReportRow;
import com.cdl.escrow.report.repository.TrustReportMockData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class TrustReportService {

    public Page<TrustReportRow> page(TrustReportRequest req) {
        int page = req.page() == null ? 0 : req.page();
        int size = req.size() == null ? 20 : req.size();

        List<TrustReportRow> all = TrustReportMockData.ROWS;
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int)pageable.getOffset(), all.size());
        int end = Math.min(start + pageable.getPageSize(), all.size());
        return new PageImpl<>(all.subList(start, end), pageable, all.size());
    }

    public byte[] exportExcel(List<TrustReportRow> rows, TrustReportRequest req) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("RTO4Trust_Report");

            Row title = sheet.createRow(0);
            title.createCell(0).setCellValue("RTO4Trust_Report");

            Row criteria = sheet.createRow(1);
            criteria.createCell(0).setCellValue(
                    "Selection Criteria : BUILD PARTNER : " + req.developerName() +
                            " , BUILD PARTNER ASSETS : " + req.projectName() +
                            " , Unit Holder Name : " + (req.unitHolderName()==null?"All":req.unitHolderName())
            );

            Row countRow = sheet.createRow(3);
            countRow.createCell(0).setCellValue(rows.size() + " Record(s) Found");

            // Headers
            String[] headers = {
                    "S.No","Building Name","Unit Number","Owner1","Owner2","Owner3","Owner4","Owner5",
                    "Owner6","Owner7","Owner8","Owner9","Owner10","Gross Sales Value",
                    "Cash Collection out Escrow","Cash Collection within Escrow",
                    "Total Cash in Escrow","Total Cash from Unit Holder","Owner Balance",
                    "Unit Status","Oqood Paid","SPA","Reservation Form",
                    "Oqood Amount","DLD Amount","Balance Letter Sent","Balance Letter Issued","Remarks"
            };

            Row headerRow = sheet.createRow(5);
            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);
            for (int i=0; i<headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(bold);
            }

            int r = 6;
            for (TrustReportRow row : rows) {
                Row rr = sheet.createRow(r++);
                int c = 0;
                rr.createCell(c++).setCellValue(row.serialNo());
                rr.createCell(c++).setCellValue(row.buildingName());
                rr.createCell(c++).setCellValue(row.unitNumber());
                rr.createCell(c++).setCellValue(row.owner1());
                rr.createCell(c++).setCellValue(row.owner2());
                rr.createCell(c++).setCellValue(nvl(row.owner3()));
                rr.createCell(c++).setCellValue(nvl(row.owner4()));
                rr.createCell(c++).setCellValue(nvl(row.owner5()));
                rr.createCell(c++).setCellValue(nvl(row.owner6()));
                rr.createCell(c++).setCellValue(nvl(row.owner7()));
                rr.createCell(c++).setCellValue(nvl(row.owner8()));
                rr.createCell(c++).setCellValue(nvl(row.owner9()));
                rr.createCell(c++).setCellValue(nvl(row.owner10()));
                rr.createCell(c++).setCellValue(row.grossSalesValue());
                rr.createCell(c++).setCellValue(row.cashCollectionOutEscrow());
                rr.createCell(c++).setCellValue(row.cashCollectionWithinEscrow());
                rr.createCell(c++).setCellValue(row.totalCashReceivedInEscrow());
                rr.createCell(c++).setCellValue(row.totalCashFromUnitHolder());
                rr.createCell(c++).setCellValue(row.ownerBalance());
                rr.createCell(c++).setCellValue(row.unitStatus());
                rr.createCell(c++).setCellValue(row.oqoodPaid() ? "Y":"N");
                rr.createCell(c++).setCellValue(row.spa() ? "Y":"N");
                rr.createCell(c++).setCellValue(row.reservationForm() ? "Y":"N");
                rr.createCell(c++).setCellValue(row.oqoodAmount());
                rr.createCell(c++).setCellValue(row.dldAmount());
                rr.createCell(c++).setCellValue(row.balanceLetterSent());
                rr.createCell(c++).setCellValue(row.balanceLetterIssued()!=null ? row.balanceLetterIssued().toString() : "");
                rr.createCell(c++).setCellValue(row.remarks());
            }

            for (int i=0; i<headers.length; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String nvl(String s) {
        return s==null?"":s;
    }
}
