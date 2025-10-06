package com.cdl.escrow.report.repository;

import com.cdl.escrow.report.dto.EscrowTasRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public final class EscrowTasMock {
    private EscrowTasMock(){}

    public static final List<EscrowTasRow> ROWS;

    static {
        Random rnd = new Random(31);
        String[] devs = {"1001 - Code Decode Labs", "1002 - GreenField Estates"};
        String[] devRera = {"D-RERA-1256", "D-RERA-3399"};
        String[] prjs = {"2001 - EBI Heights", "2002 - CDL Skytower", "3001 - Green Meadows", "3002 - Lakeside Villas"};
        String[] prjRera = {"P-RERA-2113", "P-RERA-4550", "P-RERA-5120", "P-RERA-6330"};
        String[] drTypes = {"Deposit","Transfer","Reversal"};
        String[] owners = {"5001 - Ahmed Ali", "5002 - Sarah Khan", "5003 - John Smith", "5004 - Maria Lopez"};
        String[] offline = {"Cash","Cheque","Cash/Cheque","Transfer"};
        String[] banks = {"ADCB","FAB","ENBD","Mashreq"};
        String[] beneNames = {"Escrow Ops Team", "Trust Services", "Escrow Processing"};

        List<EscrowTasRow> tmp = new ArrayList<>();
        AtomicInteger s = new AtomicInteger(1);

        for (int i = 0; i < 120; i++) {
            int di = rnd.nextInt(devs.length);
            int pi = rnd.nextInt(prjs.length);
            String dev = devs[di];
            String prj = prjs[pi];

            tmp.add(new EscrowTasRow(
                    s.getAndIncrement(),
                    dev,
                    prj,
                    drTypes[rnd.nextInt(drTypes.length)],
                    owners[rnd.nextInt(owners.length)],
                    "T" + (1 + rnd.nextInt(8)),                 // unitNo1 tower
                    "U-" + (10000 + rnd.nextInt(9000)),         // unitNo2
                    "PAY-" + (100000 + rnd.nextInt(900000)),    // payment ref
                    offline[rnd.nextInt(offline.length)],
                    "TRF-" + (10000 + rnd.nextInt(90000)),      // transfer ref
                    prjRera[pi % prjRera.length],               // project RERA
                    devRera[di % devRera.length],               // developer RERA
                    "Bank",                                     // beneficiaryType (constant)
                    beneNames[rnd.nextInt(beneNames.length)],   // beneficiaryName
                    banks[rnd.nextInt(banks.length)],           // beneficiaryBank
                    "No Values",                                 // mobile const
                    "No Values",                                 // email const
                    "No Values",                                 // routing code const
                    "Depositor " + (100 + rnd.nextInt(900))     // depositor/payee
            ));
        }

        ROWS = Collections.unmodifiableList(tmp);
    }
}
