package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.EscrowTasRequest;
import com.cdl.escrow.report.dto.EscrowTasRow;
import com.cdl.escrow.report.repository.EscrowTasMock;
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
public class EscrowTasService {

    private static final String[] HEADERS = {
            "S.No",
            "Build Partner Number/Name",
            "Build Partner Assets Number/Name",
            "Deposit Request Type",
            "Owner Number/Name",
            "Unit No 1 (Tower)",
            "Unit No 2 (Unit)",
            "Payment Reference number",
            "Offline Payment Methods",
            "Transfer Ref Number",
            "Project RERA Number",
            "Build Partner RERA Number",
            "Beneficiary Type",
            "Beneficiary Name",
            "Beneficiary Bank",
            "Beneficiary Mobile",
            "Beneficiary Email",
            "Beneficiary Routing Code",
            "Depositor or Payee Name"
    };

    public Page<EscrowTasRow> page(EscrowTasRequest req) {
        // For mock: ignore filters, but keep deterministic order
        var all = EscrowTasMock.ROWS.stream()
                .sorted((a,b) -> a.projectConcat().compareToIgnoreCase(b.projectConcat()))
                .toList();

        int page = (req.page()==null || req.page()<0) ? 0 : req.page();
        int size = (req.size()==null || req.size()<1) ? 50 : req.size();
        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), all.size());
        int end   = Math.min(start + pageable.getPageSize(), all.size());
        return new PageImpl<>(all.subList(start, end), pageable, all.size());
    }

    public byte[] exportExcel(EscrowTasRequest req, List<EscrowTasRow> rows) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sh = wb.createSheet("Escrow Regulatory - TAS");
            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);

            // Title
            Row r0 = sh.createRow(0);
            Cell c0 = r0.createCell(0);
            c0.setCellValue("Escrow Regulatory - TAS Report");
            c0.setCellStyle(bold);

            // Criteria
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String criteria = "Selection Criteria : Developer : " + nv(req.developer()) +
                    " , Project : " + nv(req.project()) +
                    " , Deposit Request Type : " + nv(req.depositRequestType()) +
                    " , Owner : " + nv(req.owner()) +
                    " , From : " + (req.fromDate()!=null ? df.format(req.fromDate()) : "N/A") +
                    " , To : " + (req.toDate()!=null ? df.format(req.toDate()) : "N/A") +
                    " , Checker Approval Date : " + (req.checkerApprovalDate()!=null ? df.format(req.checkerApprovalDate()) : "N/A");
            Row r1 = sh.createRow(1);
            r1.createCell(0).setCellValue(criteria);

            // Records Found
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
            for (EscrowTasRow row : rows) {
                Row rr = sh.createRow(r++);
                int c = 0;
                rr.createCell(c++).setCellValue(row.serialNo());
                rr.createCell(c++).setCellValue(row.developerConcat());
                rr.createCell(c++).setCellValue(row.projectConcat());
                rr.createCell(c++).setCellValue(row.depositRequestType());
                rr.createCell(c++).setCellValue(row.ownerConcat());
                rr.createCell(c++).setCellValue(row.unitNo1());
                rr.createCell(c++).setCellValue(row.unitNo2());
                rr.createCell(c++).setCellValue(row.paymentReferenceNo());
                rr.createCell(c++).setCellValue(row.offlinePaymentMethods());
                rr.createCell(c++).setCellValue(row.transferRefNumber());
                rr.createCell(c++).setCellValue(row.projectReraNumber());
                rr.createCell(c++).setCellValue(row.developerReraNumber());
                rr.createCell(c++).setCellValue(row.beneficiaryType());        // "Bank"
                rr.createCell(c++).setCellValue(row.beneficiaryName());
                rr.createCell(c++).setCellValue(row.beneficiaryBank());
                rr.createCell(c++).setCellValue(row.beneficiaryMobile());      // "No Values"
                rr.createCell(c++).setCellValue(row.beneficiaryEmail());       // "No Values"
                rr.createCell(c++).setCellValue(row.beneficiaryRoutingCode()); // "No Values"
                rr.createCell(c++).setCellValue(row.depositorOrPayeeName());
            }

            for (int i=0;i<HEADERS.length;i++) sh.autoSizeColumn(i);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to build Escrow TAS Excel", e);
        }
    }

    /** blank template (no data, placeholders in criteria) */
    public byte[] buildBlankTemplate() {
        EscrowTasRequest dummy = new EscrowTasRequest(
                "{{DEV_NO - DEV_NAME}}",
                "{{PRJ_NO - PRJ_NAME}}",
                "{{DEPOSIT_REQUEST_TYPE}}",
                "{{OWNER_NO - OWNER_NAME}}",
                null, null, null,
                0, 1
        );
        return exportExcel(dummy, List.of());
    }

    private static String nv(String s){ return (s==null || s.isBlank()) ? "ALL" : s; }
}
