package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.TasBatchStatusRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class TasBatchStatusMock {
    private TasBatchStatusMock(){}

    public static final List<TasBatchStatusRow> ROWS;

    static {
        AtomicInteger s = new AtomicInteger(1);
        List<TasBatchStatusRow> tmp = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            tmp.add(new TasBatchStatusRow(
                    s.getAndIncrement(),
                    LocalDate.now().minusDays(i),
                    "BATCH" + (1000+i),
                    "PAYMENT",
                    "TYPE" + (i%5),
                    "BLDG-" + (i%10),
                    LocalDate.now().minusDays(i-2),
                    1000 + (i*50),
                    (i%2==0) ? "CASH":"TRANSFER",
                    "Payee " + i,
                    "DEV-" + (i%4),
                    "PRJ-" + (i%6),
                    "TASREF-" + (i*99),
                    "PROP" + (i%7),
                    "U-" + (1000+i),
                    (i%3==0) ? "SUCCESS":"FAILED",
                    (i%3==0) ? "" : "Error " + i,
                    "FIN" + (i*5),
                    "MORT" + (i%20),
                    "SYSTEM",
                    "SYSTEM"
            ));
        }
        ROWS = Collections.unmodifiableList(tmp);
    }
}
