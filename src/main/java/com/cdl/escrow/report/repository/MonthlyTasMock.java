package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.MonthlyTasRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public final class MonthlyTasMock {
    private MonthlyTasMock(){}

    public static final List<MonthlyTasRow> ROWS;

    static {
        Random rnd = new Random(17);
        String[] developers = {"Code Decode Labs Pvt Ltd", "GreenField Estates"};
        String[][] projects = {
                {"EBI Heights", "CDL Skytower"},
                {"Green Meadows", "Lakeside Villas"}
        };
        String[] ptypes = {"Deposit","Transfer","Reversal"};
        String[] pmodes = {"Cash","Transfer","Cheque"};
        String[] banks  = {"ADCB","FAB","ENBD","Mashreq"};
        String[] statuses = {"SUCCESS","FAILED"};

        List<MonthlyTasRow> tmp = new ArrayList<>();
        AtomicInteger s = new AtomicInteger(1);

        LocalDate start = LocalDate.now().minusMonths(3);
        for (int i = 0; i < 250; i++) {
            int di = rnd.nextInt(developers.length);
            String dev = developers[di];
            String prj = projects[di][rnd.nextInt(projects[di].length)];
            LocalDate d = start.plusDays(rnd.nextInt(90));
            String unit = "U-" + (10000 + rnd.nextInt(9000));
            String owner = "Owner " + (100 + rnd.nextInt(800));
            String ptype = ptypes[rnd.nextInt(ptypes.length)];
            String pmode = pmodes[rnd.nextInt(pmodes.length)];
            double amt = 2000 + rnd.nextInt(150000);
            String bank = banks[rnd.nextInt(banks.length)];
            String tas  = "TAS-" + (100000 + rnd.nextInt(900000));
            String batch= "BATCH-" + (1000 + rnd.nextInt(9000));
            String status = statuses[rnd.nextInt(statuses.length)];
            String finRef = "FIN-" + (100000 + rnd.nextInt(900000));
            String err = "SUCCESS".equals(status) ? "" : "Validation error";

            tmp.add(new MonthlyTasRow(
                    s.getAndIncrement(), d, batch, tas, dev, prj, unit, owner,
                    ptype, pmode, amt, bank, finRef, status, err
            ));
        }
        ROWS = Collections.unmodifiableList(tmp);
    }
}
