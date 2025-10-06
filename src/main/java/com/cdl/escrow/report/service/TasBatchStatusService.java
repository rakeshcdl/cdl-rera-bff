package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.TasBatchStatusRequest;
import com.cdl.escrow.report.dto.TasBatchStatusRow;
import com.cdl.escrow.report.repository.TasBatchStatusMock;
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
public class TasBatchStatusService {

    public Page<TasBatchStatusRow> page(TasBatchStatusRequest req) {
        int page = req.page() == null ? 0 : req.page();
        int size = req.size() == null ? 20 : req.size();

        List<TasBatchStatusRow> all = TasBatchStatusMock.ROWS;
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int)pageable.getOffset(), all.size());
        int end = Math.min(start + pageable.getPageSize(), all.size());
        return new PageImpl<>(all.subList(start, end), pageable, all.size());
    }

    public byte[] exportExcel(List<TasBatchStatusRow> rows, TasBatchStatusRequest req) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("TAS Batch Status Report");
            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);

            // Title
            Row title = sheet.createRow(0);
            title.createCell(0).setCellValue("TAS Batch Status Report");

            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String crit = "Selection Criteria : TAS Batch Date From : " + df.format(req.fromDate())
                    + " , TAS Batch Date To : " + df.format(req.toDate());
            Row criteria = sheet.createRow(1);
            criteria.createCell(0).setCellValue(crit);

            // Count row
            Row count = sheet.createRow(3);
            count.createCell(0).setCellValue(rows.size() + " Record(s) Found");

            // Headers
            String[] headers = {
                    "S.No","Create Date","Batch No.","Payment Type","Sub-type","Building Number","Date of Payment",
                    "Payment Amount","Payment Mode","Payee Name","Developer Number","Project Number",
                    "TAS Reference No","Property Type Id","Unit Number","Service","Error Descp.","Finacle Ref No.",
                    "Mortgage Number","MAKER","CHECKER"
            };
            Row h = sheet.createRow(5);
            for (int i=0; i<headers.length; i++) {
                Cell c = h.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(bold);
            }

            // Fill rows
            int r = 6;
            for (TasBatchStatusRow row : rows) {
                Row rr = sheet.createRow(r++);
                int c=0;
                rr.createCell(c++).setCellValue(row.serialNo());
                rr.createCell(c++).setCellValue(df.format(row.createDate()));
                rr.createCell(c++).setCellValue(row.batchNo());
                rr.createCell(c++).setCellValue(row.paymentType());
                rr.createCell(c++).setCellValue(row.subType());
                rr.createCell(c++).setCellValue(row.buildingNumber());
                rr.createCell(c++).setCellValue(df.format(row.dateOfPayment()));
                rr.createCell(c++).setCellValue(row.paymentAmount());
                rr.createCell(c++).setCellValue(row.paymentMode());
                rr.createCell(c++).setCellValue(row.payeeName());
                rr.createCell(c++).setCellValue(row.developerNumber());
                rr.createCell(c++).setCellValue(row.projectNumber());
                rr.createCell(c++).setCellValue(row.tasReferenceNo());
                rr.createCell(c++).setCellValue(row.propertyTypeId());
                rr.createCell(c++).setCellValue(row.unitNumber());
                rr.createCell(c++).setCellValue(row.service());
                rr.createCell(c++).setCellValue(row.errorDescription());
                rr.createCell(c++).setCellValue(row.finacleRefNo());
                rr.createCell(c++).setCellValue(row.mortgageNumber());
                rr.createCell(c++).setCellValue(row.maker());
                rr.createCell(c++).setCellValue(row.checker());
            }

            for (int i=0; i<headers.length; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
