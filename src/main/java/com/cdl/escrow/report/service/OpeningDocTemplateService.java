package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.BuildPartnerAssestReportDTO;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class OpeningDocTemplateService {

    private static final DateTimeFormatter DAY_MON_UPPER = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

    public byte[] generate(BuildPartnerAssestReportDTO project,
                           String developerName,
                           String developerAddress,
                           LocalDate letterDate,
                           String developerRegId,
                           String projectId,
                           String location) {
        try (InputStream in = new ClassPathResource("templates/account-opening-template.docx").getInputStream();
             XWPFDocument doc = new XWPFDocument(in)) {

            Map<String, String> vars = Map.ofEntries(
                    Map.entry("{{LETTER_DATE}}", DAY_MON_UPPER.format(letterDate)),
                    Map.entry("{{OUR_REF}}", developerName + " / " + project.name()),
                    Map.entry("{{DEVELOPER_NAME}}", developerName),
                    Map.entry("{{DEVELOPER_ADDRESS}}", nvl(developerAddress)),
                    Map.entry("{{PROJECT_NAME}}", project.name()),

                    Map.entry("{{TRUST_ACCOUNT_NAME}}", project.name() + " ESCROW ACCOUNT"),
                    Map.entry("{{TRUST_ACCOUNT_NO}}", nvl(project.escrowAccountNo())),
                    Map.entry("{{TRUST_IBAN}}", "AE00 0000 0000 0000 0000 000"), // mock

                    Map.entry("{{RETENTION_ACCOUNT_NAME}}", project.name() + " RETENTION ACCOUNT"),
                    Map.entry("{{RETENTION_ACCOUNT_NO}}", nvl(project.retentionAccountNo())),
                    Map.entry("{{RETENTION_IBAN}}", "AE00 0000 0000 0000 0000 001"), // mock

                    Map.entry("{{ACCOUNT_OPENED_DATE}}", DAY_MON_UPPER.format(letterDate)),

                    Map.entry("{{DEVELOPER_REG_ID}}", nvl(developerRegId)),
                    Map.entry("{{PROJECT_ID}}", nvl(projectId)),
                    Map.entry("{{LOCATION}}", nvl(location))
            );

            replaceAll(doc, vars);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                doc.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    private static String nvl(String s) { return s == null ? "" : s; }

    private static void replaceAll(XWPFDocument doc, Map<String, String> map) {
        for (XWPFParagraph p : doc.getParagraphs()) replaceInParagraph(p, map);
        for (XWPFTable table : doc.getTables())
            for (XWPFTableRow row : table.getRows())
                for (XWPFTableCell cell : row.getTableCells())
                    for (XWPFParagraph p : cell.getParagraphs()) replaceInParagraph(p, map);
        for (XWPFHeader h : doc.getHeaderList())
            for (XWPFParagraph p : h.getParagraphs()) replaceInParagraph(p, map);
        for (XWPFFooter f : doc.getFooterList())
            for (XWPFParagraph p : f.getParagraphs()) replaceInParagraph(p, map);
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
