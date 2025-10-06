package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.MonthlyReraRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public final class MonthlyReraMock {

    private MonthlyReraMock(){}

    public static final List<MonthlyReraRow> ROWS;

    static {
        Random rnd = new Random(7);
        String[] developers = {"Code Decode Labs Pvt Ltd", "GreenField Estates"};
        String[][] projects = {
                {"EBI Heights", "CDL Skytower"},
                {"Green Meadows", "Lakeside Villas"}
        };
        String[] activities = {"Booking","SPA","Oqood","Transfer","Cancel","Collection"};
        String[] modes = {"Cash","Transfer","Cheque","-"};
        String[] banks = {"ADCB","FAB","ENBD","Mashreq","-"};
        String[] status = {"SUCCESS","FAILED","OPEN","CANCELLED"};

        List<MonthlyReraRow> tmp = new ArrayList<>();
        AtomicInteger s = new AtomicInteger(1);

        // 300 rows across a few months
        LocalDate start = LocalDate.now().minusMonths(6);
        for (int i = 0; i < 300; i++) {
            int dIdx = rnd.nextInt(developers.length);
            String dev = developers[dIdx];
            String prj = projects[dIdx][rnd.nextInt(projects[dIdx].length)];
            LocalDate date = start.plusDays(rnd.nextInt(180));
            String unit = "U-" + (10000 + rnd.nextInt(9000));
            String owner = "Owner " + (100 + rnd.nextInt(800));
            String act = activities[rnd.nextInt(activities.length)];
            String mode = "Collection".equals(act) ? modes[rnd.nextInt(modes.length)] : "-";
            double amt = switch (act) {
                case "Booking" -> 25000 + rnd.nextInt(25000);
                case "SPA"     -> 0;
                case "Oqood"   -> 4000 + rnd.nextInt(4000);
                case "Transfer"-> 1500 + rnd.nextInt(1500);
                case "Cancel"  -> 0;
                default        -> 5000 + rnd.nextInt(50000); // Collection
            };
            String bank = "Collection".equals(act) ? banks[rnd.nextInt(banks.length)] : "-";
            String tas   = "Collection".equals(act) ? "TAS-" + (100000 + rnd.nextInt(900000)) : "-";
            String st    = status[rnd.nextInt(status.length)];

            tmp.add(new MonthlyReraRow(
                    s.getAndIncrement(),
                    date, dev, prj, unit, owner, act, mode, amt, bank, tas, st
            ));
        }
        ROWS = Collections.unmodifiableList(tmp);
    }
}
