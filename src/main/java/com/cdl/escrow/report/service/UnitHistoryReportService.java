package com.cdl.escrow.report.service;

import com.cdl.escrow.report.dto.UnitHistoryDetailRow;
import com.cdl.escrow.report.dto.UnitHistoryListRow;
import com.cdl.escrow.report.dto.UnitHistoryMock;
import com.cdl.escrow.report.dto.UnitHistoryRequest;
import org.apache.poi.common.usermodel.HyperlinkType;
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
public class UnitHistoryReportService {

    public Page<UnitHistoryListRow> page(UnitHistoryRequest req) {
        int page = req.page() == null || req.page() < 0 ? 0 : req.page();
        int size = req.size() == null || req.size() < 1 ? 50 : req.size();

        List<UnitHistoryListRow> all = UnitHistoryMock.LISTING.stream()
                .sorted((a, b) -> a.unitNo().compareToIgnoreCase(b.unitNo()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int) pageable.getOffset(), all.size());
        int end = Math.min(start + pageable.getPageSize(), all.size());

        return new PageImpl<>(all.subList(start, end), pageable, all.size());
    }

    /** Build Excel with listing + details and internal hyperlinks from listing -> details row. */
    public byte[] exportExcel(UnitHistoryRequest req, List<UnitHistoryListRow> listing) {
        try (Workbook wb = new XSSFWorkbook()) {
            CreationHelper helper = wb.getCreationHelper();
            CellStyle bold = wb.createCellStyle();
            Font f = wb.createFont(); f.setBold(true); bold.setFont(f);

            // ---------- SHEET 1: LISTING ----------
            Sheet list = wb.createSheet("Unit History - Listing");
            Row title = list.createRow(0);
            title.createCell(0).setCellValue("Unit History Report - Listing");

            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            String asOn = (req.toDate() != null) ? df.format(req.toDate()) : df.format(java.time.LocalDate.now());
            String crit = "Selection Criteria : Project : " + nvl(req.project()) +
                    ", As on (Date) : " + asOn + " , Unit Holder Name : All";
            Row criteria = list.createRow(1);
            criteria.createCell(0).setCellValue(crit);

            Row found = list.createRow(3);
            found.createCell(0).setCellValue(UnitHistoryMock.LISTING.size() + " Record(s) Found");

            String[] headers = {
                    "S.No","Unit No","Name of Owner1","Name of Owner2","Unit History Flag","Status"
            };
            Row h = list.createRow(5);
            for (int i = 0; i < headers.length; i++) {
                Cell c = h.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(bold);
            }

            // Sort by Unit No ascending
            listing = listing.stream()
                    .sorted((a, b) -> a.unitNo().compareToIgnoreCase(b.unitNo()))
                    .toList();

            // Fill rows with hyperlink on unit number
            int r = 6;
            for (UnitHistoryListRow row : listing) {
                Row rr = list.createRow(r++);
                int c = 0;
                rr.createCell(c++).setCellValue(row.serialNo());
                Cell unitCell = rr.createCell(c++);
                unitCell.setCellValue(row.unitNo());

                // hyperlink to Details sheet row for this unit
                int targetRow = findDetailsRowIndex(UnitHistoryMock.DETAILS, row.unitNo()); // 0-based row index on details sheet
                if (targetRow >= 0) {
                    Hyperlink link = helper.createHyperlink(HyperlinkType.DOCUMENT);
                    link.setAddress("'Unit Details'!A" + (targetRow + 1)); // Excel rows are 1-based
                    unitCell.setHyperlink(link);
                    CellStyle linkStyle = wb.createCellStyle();
                    Font linkFont = wb.createFont();
                    linkFont.setUnderline(Font.U_SINGLE);
                    linkFont.setColor(IndexedColors.BLUE.getIndex());
                    linkStyle.setFont(linkFont);
                    unitCell.setCellStyle(linkStyle);
                }

                rr.createCell(c++).setCellValue(row.owner1());
                rr.createCell(c++).setCellValue(nvl(row.owner2()));
                rr.createCell(c++).setCellValue(row.unitHistoryFlag());
                rr.createCell(c++).setCellValue(row.status());
            }
            for (int i = 0; i < headers.length; i++) list.autoSizeColumn(i);

            // ---------- SHEET 2: DETAILS ----------
            Sheet det = wb.createSheet("Unit Details");
            String[] dHeaders = {
                    "S.No","Unit Number","Building Name","Modified Date","Unit Status",
                    "Oqood Paid","Oqood Amount","Gross Sales Value","Amount paid out of Escrow",
                    "Amount paid within escrow","Total Cash received from owner","Owner Balance",
                    "Forfeited Amount","Refund Amount","Transferred Amount","DLD Amount",
                    "Plot Area","Unit Floor","No of Bedrooms",
                    "Owner Name 1","Owner Name 2","Owner Name 3","Owner Name 4","Owner Name 5",
                    "Owner Name 6","Owner Name 7","Owner Name 8","Owner Name 9","Owner Name 10",
                    "Remarks"
            };
            Row dh = det.createRow(0);
            for (int i = 0; i < dHeaders.length; i++) {
                Cell c = dh.createCell(i);
                c.setCellValue(dHeaders[i]);
                c.setCellStyle(bold);
            }

            int dr = 1;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm");
            for (var d : UnitHistoryMock.DETAILS) {
                Row rr = det.createRow(dr++);
                int c = 0;
                rr.createCell(c++).setCellValue(d.serialNo());
                rr.createCell(c++).setCellValue(d.unitNumber());
                rr.createCell(c++).setCellValue(d.buildingName());
                rr.createCell(c++).setCellValue(d.modifiedDate() != null ? dtf.format(d.modifiedDate()) : "");
                rr.createCell(c++).setCellValue(d.unitStatus());
                rr.createCell(c++).setCellValue(d.oqoodPaid() ? "Y" : "N");
                rr.createCell(c++).setCellValue(d.oqoodAmount());
                rr.createCell(c++).setCellValue(d.grossSalesValue());
                rr.createCell(c++).setCellValue(d.amountPaidOutEscrow());
                rr.createCell(c++).setCellValue(d.amountPaidWithinEscrow());
                rr.createCell(c++).setCellValue(d.totalCashReceivedFromOwner());
                rr.createCell(c++).setCellValue(d.ownerBalance());
                rr.createCell(c++).setCellValue(d.forfeitedAmount());
                rr.createCell(c++).setCellValue(d.refundAmount());
                rr.createCell(c++).setCellValue(d.transferredAmount());
                rr.createCell(c++).setCellValue(d.dldAmount());
                rr.createCell(c++).setCellValue(d.plotArea());
                rr.createCell(c++).setCellValue(d.unitFloor());
                rr.createCell(c++).setCellValue(d.noOfBedrooms());
                rr.createCell(c++).setCellValue(nvl(d.ownerName1()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName2()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName3()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName4()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName5()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName6()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName7()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName8()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName9()));
                rr.createCell(c++).setCellValue(nvl(d.ownerName10()));
                rr.createCell(c++).setCellValue(nvl(d.remarks()));
            }
            for (int i = 0; i < dHeaders.length; i++) det.autoSizeColumn(i);

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                return bos.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to build Unit History Excel", e);
        }
    }

    private static String nvl(String s) { return s == null ? "" : s; }

    /** find 0-based details row index for a given unit (sheet includes header row at 0). */
    private static int findDetailsRowIndex(List<UnitHistoryDetailRow> details, String unitNo) {
        for (int i = 0; i < details.size(); i++) {
            if (details.get(i).unitNumber().equals(unitNo)) {
                return i + 1; // +1 because details sheet has header at row 0; return Excel row, but caller adds 1 again => keep 0 here
            }
        }
        return -1;
    }
}
