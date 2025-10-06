package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.FinancialDataRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public final class FinancialDataMock {

    private FinancialDataMock(){}

    public static final List<FinancialDataRow> ROWS;

    static {
        Random rnd = new Random(23);
        String[] projects = {"EBI Heights","CDL Skytower","Green Meadows","Lakeside Villas"};
        String[] statuses = {"OPEN","CANCELLED","TRANSFERRED","ON HOLD"};

        List<FinancialDataRow> tmp = new ArrayList<>();
        AtomicInteger s = new AtomicInteger(1);

        LocalDate start = LocalDate.now().minusMonths(6);
        for (int i = 0; i < 250; i++) {
            String prj = projects[rnd.nextInt(projects.length)];
            String unit = "U-" + (10000 + rnd.nextInt(9000));
            String owner = "Owner " + (200 + rnd.nextInt(800));
            double opening = 100_000 + rnd.nextInt(400_000);
            double coll = 10_000 + rnd.nextInt(200_000);
            double refunds = rnd.nextInt(2)==0 ? 0 : rnd.nextInt(15_000);
            double transfers = rnd.nextInt(2)==0 ? 0 : rnd.nextInt(20_000);
            double outEsc = rnd.nextInt(2)==0 ? 0 : rnd.nextInt(30_000);
            double inEsc  = rnd.nextInt(2)==0 ? 0 : rnd.nextInt(30_000);
            double totalCash = outEsc + inEsc;
            double oqood = rnd.nextInt(2)==0 ? 0 : 2_000 + rnd.nextInt(4_000);
            double dld   = rnd.nextInt(2)==0 ? 0 : 3_000 + rnd.nextInt(7_000);
            LocalDate lastPay = start.plusDays(rnd.nextInt(180));
            String st = statuses[rnd.nextInt(statuses.length)];

            double ownerBal = opening + coll - refunds - transfers - totalCash;
            tmp.add(new FinancialDataRow(
                    s.getAndIncrement(), prj, unit, owner, opening, coll, refunds, transfers,
                    outEsc, inEsc, totalCash, ownerBal, oqood, dld, lastPay, st, "Mock row"
            ));
        }
        ROWS = Collections.unmodifiableList(tmp);
    }
}
