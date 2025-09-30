package com.cdl.escrow.report.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ChargesMockData {

    public record Charge(
            LocalDate transactionDate,
            String projectId,
            String developerName,
            String projectName,
            String chargeType,
            String frequency,        // Monthly / Real Time / End of day
            String perUnit,          // Per Visit / Per Certificate / Per Request / ""
            String transactionStatus,// Success / Under Process / Rejected
            String rejectReason      // "" if not rejected
    ) {}

    public static final List<Charge> CHARGES = List.of(
            new Charge(LocalDate.of(2025, 8, 1), "prj-1", "Code Decode Labs Pvt. Ltd.", "EBI Heights",
                    "Swift Fee", "Real Time", "", "Success", ""),
            new Charge(LocalDate.of(2025, 8, 2), "prj-1", "Code Decode Labs Pvt. Ltd.", "EBI Heights",
                    "Engineer Fee", "Monthly", "Per Visit", "Success", ""),
            new Charge(LocalDate.of(2025, 8, 3), "prj-2", "Code Decode Labs Pvt. Ltd.", "CDL Skytower",
                    "Account Maintenance", "End of day", "", "Under Process", ""),
            new Charge(LocalDate.of(2025, 8, 4), "prj-2", "Code Decode Labs Pvt. Ltd.", "CDL Skytower",
                    "Engineer Fee", "Monthly", "Per Certificate", "Rejected", "Invalid certificate id"),
            new Charge(LocalDate.of(2025, 8, 5), "prj-3", "GreenField Estates", "Green Meadows",
                    "Transfer Fee", "Real Time", "", "Success", "")
    );
}
