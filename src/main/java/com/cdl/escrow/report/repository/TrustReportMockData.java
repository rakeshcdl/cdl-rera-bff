package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.TrustReportRow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TrustReportMockData {

    public static final List<TrustReportRow> ROWS;

    static {
        AtomicInteger serial = new AtomicInteger(1);
        List<TrustReportRow> tmp = new ArrayList<>();
        for (int i=1; i<=50; i++) {
            tmp.add(new TrustReportRow(
                    serial.getAndIncrement(),
                    "Building " + (i%5+1),
                    "U-" + (100+i),
                    "Owner " + i,
                    "Co-Owner " + i,
                    null,null,null,null,null,null,null,null,
                    500000 + (i*1000),
                    10000 + i*500,
                    20000 + i*300,
                    30000 + i*400,
                    60000 + i*700,
                    500000 - (60000 + i*700),
                    (i%2==0) ? "Active" : "Cancelled",
                    (i%3==0),
                    (i%4==0),
                    (i%5==0),
                    10000+i,
                    5000+i,
                    (i%2==0) ? "Yes":"No",
                    LocalDateTime.now().minusDays(i),
                    "Remark " + i
            ));
        }
        ROWS = Collections.unmodifiableList(tmp);
    }
}
