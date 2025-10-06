package com.cdl.escrow.report.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class BuildPartnerTemplateService {

    public byte[] buildExcelTemplate() {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Build Partner Report");

            // Title
            Row r1 = sheet.createRow(0);
            r1.createCell(0).setCellValue("Build Partner – Master Report");

            // Selection criteria placeholders
            Row r2 = sheet.createRow(1);
            r2.createCell(0).setCellValue("Selection Criteria : Period : {{FROM_DATE}} to {{TO_DATE}} ; Status : {{STATUS}}");

            // spacer
            sheet.createRow(2);
            Row r4 = sheet.createRow(3);
            r4.createCell(0).setCellValue("{{RECORDS_FOUND}} Record(s) Found");

            // spacer
            sheet.createRow(4);

            // Headers (row index 5 = Excel row 6)
            String[] headers = {
                    "S. No.","ID","bpDeveloperId","bpCifrera","bpDeveloperRegNo","bpName",
                    "bpMasterName","bpNameLocal","bpOnboardingDate","bpContactAddress","bpContactTel","bpPoBox",
                    "bpMobile","bpFax","bpEmail","bpLicenseNo","bpLicenseExpDate","bpWorldCheckFlag",
                    "bpWorldCheckRemarks","bpMigratedData","enabled","deleted","bpremark",
                    "bpRegulator","bpActiveStatus","taskStatus"
            };
            Row headerRow = sheet.createRow(5);
            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);

            double[] widths = {8,8,16,16,16,24,20,20,20,30,18,12,14,14,28,16,20,14,28,14,10,10,30,18,18,18};

            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(bold);
                sheet.setColumnWidth(i, (int)(256 * widths[i]));
            }

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] buildLetterTemplate() {
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFParagraph title = doc.createParagraph();
            XWPFRun tr = title.createRun();
            tr.setText("Build Partner Registration / Profile Summary");
            tr.setBold(true);
            tr.setFontSize(14);

            doc.createParagraph().createRun().setText("Date: {{LETTER_DATE}}");

            doc.createParagraph().createRun().setText("To,");
            doc.createParagraph().createRun().setText("{{bpName}} ({{bpDeveloperId}})");
            doc.createParagraph().createRun().setText("P.O. Box {{bpPoBox}}");
            doc.createParagraph().createRun().setText("{{bpContactAddress}}");
            doc.createParagraph().createRun().setText("Tel: {{bpContactTel}}  |  Mobile: {{bpMobile}}  |  Fax: {{bpFax}}");
            doc.createParagraph().createRun().setText("Email: {{bpEmail}}");

            doc.createParagraph().createRun().setText("");
            XWPFParagraph subj = doc.createParagraph();
            XWPFRun sr = subj.createRun();
            sr.setText("Subject: Confirmation of Registration / Profile Details");
            sr.setBold(true);

            doc.createParagraph().createRun().setText(
                    "We confirm the registration/profile details for the Build Partner as below. " +
                            "Please review and inform us of any discrepancy within 7 working days."
            );

            XWPFTable table = doc.createTable();
            addRow(table, "Build Partner ID", "{{id}}");
            addRow(table, "Developer ID", "{{bpDeveloperId}}");
            addRow(table, "CIFRERA", "{{bpCifrera}}");
            addRow(table, "Developer Reg. No.", "{{bpDeveloperRegNo}}");
            addRow(table, "Build Partner Name", "{{bpName}}");
            addRow(table, "Master Name", "{{bpMasterName}}");
            addRow(table, "Local Name", "{{bpNameLocal}}");
            addRow(table, "Onboarding Date", "{{bpOnboardingDate}}");
            addRow(table, "Contact Address", "{{bpContactAddress}}");
            addRow(table, "Contact Telephone", "{{bpContactTel}}");
            addRow(table, "P.O. Box", "{{bpPoBox}}");
            addRow(table, "Mobile", "{{bpMobile}}");
            addRow(table, "Fax", "{{bpFax}}");
            addRow(table, "Email", "{{bpEmail}}");
            addRow(table, "License No.", "{{bpLicenseNo}}");
            addRow(table, "License Expiry", "{{bpLicenseExpDate}}");
            addRow(table, "World Check Flag", "{{bpWorldCheckFlag}}");
            addRow(table, "World Check Remarks", "{{bpWorldCheckRemarks}}");
            addRow(table, "Migrated Data", "{{bpMigratedData}}");
            addRow(table, "Enabled", "{{enabled}}");
            addRow(table, "Deleted", "{{deleted}}");
            addRow(table, "Remark", "{{bpremark}}");
            addRow(table, "Regulator", "{{bpRegulator}}");
            addRow(table, "Active Status", "{{bpActiveStatus}}");
            addRow(table, "Task Status", "{{taskStatus}}");

            doc.createParagraph().createRun().setText("");
            doc.createParagraph().createRun().setText("This is a system generated letter and does not require a signature.");

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                doc.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addRow(XWPFTable table, String k, String v) {
        XWPFTableRow row;
        if (table.getNumberOfRows() == 1
                && table.getRow(0).getCell(0) != null
                && table.getRow(0).getCell(0).getText() == null || table.getRow(0).getCell(0).getText().isEmpty()) {
            row = table.getRow(0);
        } else {
            row = table.createRow();
        }
        if (row.getCell(0) == null) row.addNewTableCell();
        if (row.getCell(1) == null) row.addNewTableCell();
        row.getCell(0).setText(k);
        row.getCell(1).setText(v);
    }
}
