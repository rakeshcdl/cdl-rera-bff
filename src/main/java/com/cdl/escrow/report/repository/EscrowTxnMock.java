package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.EscrowTxnRow;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class EscrowTxnMock {
    private EscrowTxnMock(){}

    // simple FX table (Ref -> USD) just for refAmount demo
    public static final Map<String, Double> FX = Map.of(
            "USD", 1.0, "AED", 0.27225, "EUR", 1.10 // 1 AED ≈ 0.27225 USD, sample only
    );

    public static final List<EscrowTxnRow> ROWS;

    static {
        Random rnd = new Random(19);
        String[] developers = {"Code Decode Labs Pvt Ltd","GreenField Estates"};
        String[] projects   = {"EBI Heights","CDL Skytower","Green Meadows","Lakeside Villas"};
        String[] projectRera= {"P-RERA-2113","P-RERA-4550","P-RERA-5120","P-RERA-6330"};
        String[] entity     = {"ENBD","EI"};
        String[] seg        = {"Escrow","Escrow – Premium"};
        String[] rm         = {"Rohan Mehta","Sara George","Naveen Iyer"};
        String[] rm2        = {"Anita Das","—","—"};
        String[] tl         = {"TL-A","TL-B"};
        String[] tType      = {"Fees","Collection","Transfer In","Transfer Out","Refund"};
        String[] pType      = {"Debit","Credit"};
        String[] curr       = {"AED","USD"};
        String[] status     = {"POSTED","PENDING",""}; // posted status (payments)

        List<EscrowTxnRow> tmp = new ArrayList<>();
        AtomicInteger s = new AtomicInteger(1);

        LocalDate start = LocalDate.now().minusMonths(2);
        for (int i=0; i<300; i++) {
            String dev = developers[rnd.nextInt(developers.length)];
            int pj = rnd.nextInt(projects.length);
            String prj = projects[pj];
            String prera = projectRera[pj];

            String acctNo = (rnd.nextBoolean() ? "120" : "121") + (100000 + rnd.nextInt(899999)); // 120/121 -> schemeCode
            String scheme = acctNo.substring(0,3);

            LocalDate dt = start.plusDays(rnd.nextInt(60));
            String ptt = pType[rnd.nextInt(pType.length)];
            double amt = 500 + rnd.nextInt(150000);
            Double debit = "Debit".equals(ptt) ? amt : 0.0;
            Double credit= "Credit".equals(ptt) ? amt : 0.0;
            String tcurr = curr[rnd.nextInt(curr.length)];
            String refCcy = "USD"; // demo reference currency
            double fx = FX.getOrDefault(tcurr, 1.0) / FX.getOrDefault(refCcy, 1.0);
            double refAmt = Math.round(amt * fx * 100.0) / 100.0;

            tmp.add(new EscrowTxnRow(
                    s.getAndIncrement(),
                    dev,
                    prj,
                    prera,
                    "TRN-" + (100000 + rnd.nextInt(900000)),
                    tType[rnd.nextInt(tType.length)],
                    amt,
                    dt,
                    "Narration " + (1000 + i),
                    entity[rnd.nextInt(entity.length)],
                    scheme,
                    seg[rnd.nextInt(seg.length)],
                    "CIF-" + (1000 + rnd.nextInt(9000)),
                    acctNo,
                    "Main Escrow for " + prj,
                    tcurr,
                    rm[rnd.nextInt(rm.length)],
                    rm2[rnd.nextInt(rm2.length)],
                    tl[rnd.nextInt(tl.length)],
                    ptt,
                    refAmt,
                    refCcy,
                    status[rnd.nextInt(status.length)],
                    "MK" + (10 + rnd.nextInt(90)),
                    "CK" + (10 + rnd.nextInt(90)),
                    "SOL-" + (100 + rnd.nextInt(900)),
                    "BR-" + (100 + rnd.nextInt(900)),
                    tcurr,
                    debit, credit,
                    "Type-" + (1 + rnd.nextInt(4)),
                    "SubType-" + (1 + rnd.nextInt(6))
            ));
        }

        ROWS = Collections.unmodifiableList(tmp);
    }
}
