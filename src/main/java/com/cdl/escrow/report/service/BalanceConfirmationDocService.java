package com.cdl.escrow.report.service;

import com.cdl.escrow.report.repository.BalanceMockData;
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
public class BalanceConfirmationDocService {
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

    public byte[] generate(String developerName, String investorName, String projectName,
                           String unitNo, String escrowAccNo, LocalDate letterDate) {

        LocalDate safeDate = (letterDate != null) ? letterDate : LocalDate.now();

        try (InputStream in = new ClassPathResource("templates/balance-confirmation-template.docx").getInputStream();
             XWPFDocument doc = new XWPFDocument(in)) {

            Map<String, String> vars = Map.ofEntries(
                    Map.entry("{{LETTER_DATE}}", DATE_FMT.format(safeDate)),
                    Map.entry("{{INVESTOR_NAME}}", nvl(investorName)),
                    Map.entry("{{DEVELOPER_NAME}}", nvl(developerName)),
                    Map.entry("{{DEVELOPER_POBOX}}", "12345"),
                    Map.entry("{{PROJECT_NAME}}", nvl(projectName)),
                    Map.entry("{{UNIT_NO}}", nvl(unitNo)),
                    Map.entry("{{ESCROW_ACC_NO}}", nvl(escrowAccNo)),
                    Map.entry("{{PRIMARY_OWNER}}", nvl(investorName)),
                    Map.entry("{{SECONDARY_OWNER}}", "")
            );
            replaceAll(doc, vars);

            // --- Fill deposits table (first table) ---
            if (!doc.getTables().isEmpty()) {
                XWPFTable table = doc.getTables().get(0);

                // Detect how many columns exist (header). If header is merged, hardcode as needed.
                int headerCols = (table.getRow(0) != null) ? table.getRow(0).getTableCells().size() : 0;
                int colCount = headerCols >= 5 ? headerCols : 6; // default to 6 for v2 template

                // Remove all rows except header
                while (table.getNumberOfRows() > 1) {
                    table.removeRow(1);
                }

                // Append rows safely
                for (BalanceMockData.Deposit d : BalanceMockData.DEPOSITS) {
                    XWPFTableRow r = table.createRow(); // creates row with 1 cell
                    ensureCells(r, colCount);           // make sure all cells exist

                    // Common columns
                    r.getCell(0).setText(nvl(d.date()));
                    r.getCell(1).setText(nvl(d.currency()));
                    r.getCell(2).setText(String.valueOf(d.amount()));
                    r.getCell(3).setText(nvl(d.tasNo()));
                    r.getCell(4).setText(nvl(d.paymentRefNo()));

                    // 6th column only if template has it (Documents Submitted)
                    if (colCount >= 6) {
                        String docSub = (d.documentSubmitted() != null)
                                ? d.documentSubmitted().name().replace('_', ' ')
                                : "";
                        r.getCell(5).setText(docSub);
                    }
                }
            }

            long total = BalanceMockData.DEPOSITS.stream().mapToLong(BalanceMockData.Deposit::amount).sum();

            replaceAll(doc, Map.of(
                    "{{TOTAL_AMOUNT}}", String.valueOf(total),
                    "{{TOTAL_AMOUNT_WORDS}}", numberToWords(total) + " Rupees Only"
            ));

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                doc.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /* ---------- helpers ---------- */

    private static void ensureCells(XWPFTableRow row, int colCount) {
        for (int i = 0; i < colCount; i++) {
            if (row.getCell(i) == null) row.addNewTableCell();
        }
    }

    private static void replaceAll(XWPFDocument doc, Map<String, String> map) {
        for (XWPFParagraph p : doc.getParagraphs()) replaceInParagraph(p, map);
        for (XWPFTable t : doc.getTables())
            for (XWPFTableRow row : t.getRows())
                for (XWPFTableCell c : row.getTableCells())
                    for (XWPFParagraph p : c.getParagraphs()) replaceInParagraph(p, map);
        // (optional) headers/footers if you use placeholders there
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
            for (var e : map.entrySet()) {
                if (nt.contains(e.getKey())) nt = nt.replace(e.getKey(), e.getValue());
            }
            if (!nt.equals(t)) run.setText(nt, 0);
        }
    }

    private static String nvl(String s) { return s == null ? "" : s; }

    // Minimal number->words (English, short). Replace with a robust library if needed.
    private static String numberToWords(long n) {
        if (n == 0) return "Zero";
        final String[] small = {"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten",
                "Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
        final String[] tens = {"","","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};

        StringBuilder sb = new StringBuilder();
        long billions = n / 1_000_000_000; n %= 1_000_000_000;
        long millions = n / 1_000_000; n %= 1_000_000;
        long thousands = n / 1_000; n %= 1_000;

        if (billions > 0) sb.append(threeDigits((int)billions, small, tens)).append(" Billion ");
        if (millions > 0) sb.append(threeDigits((int)millions, small, tens)).append(" Million ");
        if (thousands > 0) sb.append(threeDigits((int)thousands, small, tens)).append(" Thousand ");
        if (n > 0) sb.append(threeDigits((int)n, small, tens));
        return sb.toString().trim();
    }

    private static String threeDigits(int n, String[] small, String[] tens) {
        StringBuilder sb = new StringBuilder();
        if (n >= 100) { sb.append(small[n/100]).append(" Hundred "); n %= 100; }
        if (n >= 20) { sb.append(tens[n/10]).append(" "); n %= 10; }
        if (n > 0) { sb.append(small[n]).append(" "); }
        return sb.toString();
    }
}
