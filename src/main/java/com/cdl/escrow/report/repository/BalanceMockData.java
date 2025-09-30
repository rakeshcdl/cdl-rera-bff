package com.cdl.escrow.report.repository;

import lombok.Data;

import java.util.List;

@Data
public class BalanceMockData {

    public enum DocumentType {
        PSA, BOOKING_FORM, OQOOD
    }

    // Full deposit row used to populate the table
    public static final class Deposit {
        private final String date;            // e.g., 03/Mar/2021
        private final String currency;        // e.g., INR
        private final long amount;            // numeric for totals
        private final String tasNo;           // TAS#
        private final String paymentRefNo;    // may be "NA"
        private final DocumentType documentSubmitted;

        public Deposit(String date, String currency, long amount,
                       String tasNo, String paymentRefNo, DocumentType documentSubmitted) {
            this.date = date;
            this.currency = currency;
            this.amount = amount;
            this.tasNo = tasNo;
            this.paymentRefNo = paymentRefNo;
            this.documentSubmitted = documentSubmitted;
        }

        public String date() { return date; }
        public String currency() { return currency; }
        public long amount() { return amount; }
        public String tasNo() { return tasNo; }
        public String paymentRefNo() { return paymentRefNo; }
        public DocumentType documentSubmitted() { return documentSubmitted; }
    }

    // ✔ Complete mock list with document types
    public static final List<Deposit> DEPOSITS = List.of(
            new Deposit("03/Mar/2025", "INR", 10000, "TAS123", "PAYREF001", DocumentType.PSA),
            new Deposit("05/Apr/2025", "INR", 15000, "TAS456", "PAYREF002", DocumentType.BOOKING_FORM),
            new Deposit("09/May/2025", "INR", 20000, "TAS789", "NA",       DocumentType.OQOOD),
            new Deposit("18/Jun/2025", "INR", 12000, "TAS810", "PAYREF003", DocumentType.PSA),
            new Deposit("22/Aug/2025", "INR", 18000, "TAS955", "PAYREF004", DocumentType.OQOOD)
    );
}
