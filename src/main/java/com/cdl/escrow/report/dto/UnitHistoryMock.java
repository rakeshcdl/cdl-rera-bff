package com.cdl.escrow.report.dto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class UnitHistoryMock {

    private UnitHistoryMock(){}

    public static final List<UnitHistoryListRow> LISTING;
    public static final List<UnitHistoryDetailRow> DETAILS;

    static {
        var listing = new ArrayList<UnitHistoryListRow>();
        var details = new ArrayList<UnitHistoryDetailRow>();
        AtomicInteger s = new AtomicInteger(1);

        Random rnd = new Random(42);

        for (int i = 1; i <= 200; i++) {
            String unit = "U-" + (10000 + i);
            String own1 = "Owner " + i;
            String own2 = (i % 5 == 0) ? ("CoOwner " + i) : "";

            String status = switch (i % 5) {
                case 0 -> "CANCELLED";
                case 1 -> "OPEN";
                case 2 -> "DELETED";
                case 3 -> "TRANSFERRED";
                default -> "OPEN";
            };
            String historyFlag = (i % 7 == 0) ? "Y" : "N";

            listing.add(new UnitHistoryListRow(
                    s.getAndIncrement(),
                    unit,
                    own1,
                    own2,
                    historyFlag,
                    status
            ));

            double gross = 300000 + (i * 1500);
            double outEsc = 5000 + (i * 200);
            double inEsc = 12000 + (i * 150);
            double totalCash = outEsc + inEsc + (i * 50);
            double ownerBal = gross - totalCash;

            details.add(new UnitHistoryDetailRow(
                    i,
                    unit,
                    "Building " + ((i % 6) + 1),
                    LocalDateTime.now().minusDays(i),
                    status,
                    (i % 3 == 0),
                    10000 + (i * 30.0),
                    gross,
                    outEsc,
                    inEsc,
                    totalCash,
                    ownerBal,
                    (i % 10 == 0) ? 1000 : 0,
                    (i % 8 == 0) ? 500 : 0,
                    (i % 6 == 0) ? 2000 : 0,
                    2500 + (i * 10.0),
                    950.0 + (i % 50),
                    "F" + ((i % 40) + 1),
                    (i % 4) + 1,
                    own1,
                    own2,
                    "", "", "", "", "", "", "", "",
                    "Auto-generated mock row " + i
            ));
        }

        LISTING = Collections.unmodifiableList(listing);
        DETAILS  = Collections.unmodifiableList(details);
    }

    public static Optional<UnitHistoryDetailRow> findDetailByUnit(String unitNo) {
        return DETAILS.stream().filter(d -> d.unitNumber().equals(unitNo)).findFirst();
    }
}
