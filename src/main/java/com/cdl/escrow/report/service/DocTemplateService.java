package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.BuildPartnerAssestReportDTO;
import com.cdl.escrow.report.reportenum.ReportStatus;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DocTemplateService {

    private static final DateTimeFormatter REF_YEAR = DateTimeFormatter.ofPattern("yyyy");
    private static final DateTimeFormatter DAY_MON_UPPER = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

    public byte[] generate(BuildPartnerAssestReportDTO project,
                           String developerName,
                           ReportStatus status,
                           LocalDate asOnDate) {

        try (InputStream in = new ClassPathResource("templates/account-closure-template.docx").getInputStream();
             XWPFDocument doc = new XWPFDocument(in)) {

            Map<String, String> vars = Map.ofEntries(
                    Map.entry("{{DATE_TODAY}}", DAY_MON_UPPER.format(asOnDate)),
                    Map.entry("{{OUR_REF}}", "CDL/Account/Closure/XXX/" + REF_YEAR.format(asOnDate)),
                    Map.entry("{{DEVELOPER_NAME}}", developerName),
                    Map.entry("{{PROJECT_NAME}}", project.name()),
                    Map.entry("{{STATUS_LINE}}", toStatusLine(status)),

                    Map.entry("{{ACC1_NAME}}", project.name() + " ESCROW ACCOUNT"),
                    Map.entry("{{ACC1_NO}}", nvl(project.escrowAccountNo())),
                    Map.entry("{{ACC2_NAME}}", project.name() + " RETENTION ACCOUNT"),
                    Map.entry("{{ACC2_NO}}", nvl(project.retentionAccountNo())),
                    Map.entry("{{ACC3_NAME}}", project.name() + " Sub construction A/c only if available"),
                    Map.entry("{{ACC3_NO}}", nvl(project.subConstructionAccountNo())),
                    Map.entry("{{ACC4_NAME}}", project.name() + " construction A/c only if available"),
                    Map.entry("{{ACC4_NO}}", nvl(project.constructionAccountNo()))
            );

            replaceAll(doc, vars);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                doc.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String toStatusLine(ReportStatus s) {
        return switch (s) {
            case CLOSE_ESCROW_AND_RETENTION -> "Closure of Escrow and Retention Accounts";
            case CLOSE_ESCROW -> "Closure of Escrow Account";
            case CLOSE_RETENTION -> "Closure of Retention Account";
            case TRANSFERRED -> "Transferred";
            case CANCELLED -> "Cancelled";
        };
    }

    private static String nvl(String s) { return s == null ? "" : s; }

    private static void replaceAll(XWPFDocument doc, Map<String, String> map) {
        // paragraphs
        for (XWPFParagraph p : doc.getParagraphs()) {
            replaceInParagraph(p, map);
        }
        // tables
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replaceInParagraph(p, map);
                    }
                }
            }
        }
        // headers/footers (optional, in case placeholders are there)
        for (XWPFHeader header : doc.getHeaderList()) {
            for (XWPFParagraph p : header.getParagraphs()) replaceInParagraph(p, map);
        }
        for (XWPFFooter footer : doc.getFooterList()) {
            for (XWPFParagraph p : footer.getParagraphs()) replaceInParagraph(p, map);
        }
    }

    private static void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> map) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text == null) continue;
            String newText = text;
            for (Map.Entry<String, String> e : map.entrySet()) {
                if (newText.contains(e.getKey())) {
                    newText = newText.replace(e.getKey(), e.getValue());
                }
            }
            if (!newText.equals(text)) {
                run.setText(newText, 0);
            }
        }
    }
}
