package com.cdl.escrow.dashboard.dto;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class FakeDashboardResponseProvider {

    private final List<DashboardSummaryDto> samples;

    public FakeDashboardResponseProvider() {
        samples = new ArrayList<>();
        samples.add(sample1());
        samples.add(sample2());
        samples.add(sample3());
        samples.add(sample4());
        samples.add(sample5());
        samples.add(sample6());
        samples.add(sample7());
        samples.add(sample8());
        samples.add(sample9());
        samples.add(sample10());
    }

    public DashboardSummaryDto getRandomSample() {
        Random random = new Random();
        return samples.get(random.nextInt(samples.size()));
    }

    // -------------------------------
    // SAMPLE RESPONSES
    // -------------------------------

    private DashboardSummaryDto sample1() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(150000000.0);
        main.setTotalDeposits(200000000.0);
        main.setTotalPayments(50000000.0);
        main.setTotalFeesCollected(10000000.0);
        main.setDepositVsLastPeriodPercent(12.5);
        main.setPaymentVsLastPeriodPercent(-5.0);
        main.setFeesVsLastPeriodPercent(8.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 80000000.0),
                makeFund(2L, "Investor Fund", 60000000.0),
                makeFund(3L, "DLD", 20000000.0),
                makeFund(4L, "Unit Installment", 30000000.0),
                makeFund(5L, "VAT", 10000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(101L, "Construction", 25000000.0),
                makeExpense(102L, "Marketing", 10000000.0),
                makeExpense(103L, "Legal Fees", 5000000.0),
                makeExpense(104L, "Admin", 10000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(

        ));
        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 3000000.0),
                makeFees(12L, "Service Charges", 4000000.0),
                makeFees(13L, "Legal Fees (collected)", 2000000.0)
        ));



        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 120000000.0, 15.0),
                makeAccount("RETENTION", 20000000.0, 10.0),
                makeAccount("WAKALA", 5000000.0, -3.0),
                makeAccount("CORPORATE", 5000000.0, 5.0)
        ));

        // ---------- Unit Status ----------
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(20678L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 60),
                new StatusPercentDto("Unsold", 60),
                new StatusPercentDto("Freeze", 76),
                new StatusPercentDto("Resold", 44),
                new StatusPercentDto("Cancelled", 56)
        ));
        dto.setUnitStatus(unit);

        // ---------- Guarantee Status ----------
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(3L);
        g.setTotalGuaranteedAmount(14267900.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 3500000.0),
                new GuaranteeTypeDto("Retention Guarantee", 2700000.0),
                new GuaranteeTypeDto("Performance Guarantee", 8067900.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }

    private DashboardSummaryDto sample2() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(95000000.0);
        main.setTotalDeposits(120000000.0);
        main.setTotalPayments(25000000.0);
        main.setTotalFeesCollected(7000000.0);
        main.setDepositVsLastPeriodPercent(-10.0);
        main.setPaymentVsLastPeriodPercent(5.0);
        main.setFeesVsLastPeriodPercent(-2.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 50000000.0),
                makeFund(2L, "Investor Fund", 30000000.0),
                makeFund(3L, "Unit Installment", 25000000.0),
                makeFund(4L, "VAT", 10000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(101L, "Construction", 12000000.0),
                makeExpense(102L, "Marketing", 8000000.0),
                makeExpense(103L, "Admin", 5000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 1500000.0),
                makeFees(12L, "Service Charges", 3500000.0),
                makeFees(13L, "Processing Fees", 200000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 70000000.0, -8.0),
                makeAccount("RETENTION", 15000000.0, 12.0),
                makeAccount("WAKALA", 5000000.0, -5.0),
                makeAccount("CORPORATE", 5000000.0, 0.0)
        ));

        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(18750L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 55),
                new StatusPercentDto("Unsold", 45),
                new StatusPercentDto("Freeze", 30),
                new StatusPercentDto("Resold", 20),
                new StatusPercentDto("Cancelled", 10)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(3L);
        g.setTotalGuaranteedAmount(48000000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 25000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 18000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 5000000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }

    private DashboardSummaryDto sample3() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(210000000.0);
        main.setTotalDeposits(250000000.0);
        main.setTotalPayments(40000000.0);
        main.setTotalFeesCollected(15000000.0);
        main.setDepositVsLastPeriodPercent(18.0);
        main.setPaymentVsLastPeriodPercent(-2.0);
        main.setFeesVsLastPeriodPercent(20.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 100000000.0),
                makeFund(2L, "Investor Fund", 70000000.0),
                makeFund(3L, "DLD", 30000000.0),
                makeFund(4L, "VAT", 20000000.0),
                makeFund(5L, "Unit Installment", 30000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(101L, "Construction", 20000000.0),
                makeExpense(102L, "Legal Fees", 5000000.0),
                makeExpense(103L, "Admin", 15000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 7000000.0),
                makeFees(12L, "Service Charges", 5000000.0),
                makeFees(13L, "Late Fees", 3000000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 150000000.0, 20.0),
                makeAccount("RETENTION", 30000000.0, 15.0),
                makeAccount("WAKALA", 20000000.0, 8.0),
                makeAccount("CORPORATE", 10000000.0, -4.0)
        ));

        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(24000L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 70),
                new StatusPercentDto("Unsold", 30),
                new StatusPercentDto("Freeze", 5),
                new StatusPercentDto("Resold", 10),
                new StatusPercentDto("Cancelled", 15)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(3L);
        g.setTotalGuaranteedAmount(90000000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 50000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 30000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 10000000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }

    private DashboardSummaryDto sample4() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(50000000.0);
        main.setTotalDeposits(70000000.0);
        main.setTotalPayments(20000000.0);
        main.setTotalFeesCollected(4000000.0);
        main.setDepositVsLastPeriodPercent(-15.0);
        main.setPaymentVsLastPeriodPercent(10.0);
        main.setFeesVsLastPeriodPercent(-5.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 30000000.0),
                makeFund(2L, "Investor Fund", 15000000.0),
                makeFund(3L, "Unit Installment", 20000000.0),
                makeFund(4L, "VAT", 5000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(101L, "Construction", 10000000.0),
                makeExpense(102L, "Marketing", 5000000.0),
                makeExpense(103L, "Admin", 5000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 1000000.0),
                makeFees(12L, "Service Charges", 2000000.0),
                makeFees(13L, "Misc Fees", 1000000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 40000000.0, -12.0),
                makeAccount("RETENTION", 6000000.0, -8.0),
                makeAccount("WAKALA", 2000000.0, -10.0),
                makeAccount("CORPORATE", 2000000.0, 0.0)
        ));
        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(10234L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 40),
                new StatusPercentDto("Unsold", 60),
                new StatusPercentDto("Freeze", 12),
                new StatusPercentDto("Resold", 8),
                new StatusPercentDto("Cancelled", 20)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(3L);
        g.setTotalGuaranteedAmount(27500000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 12000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 10000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 5500000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }

    private DashboardSummaryDto sample5() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(300000000.0);
        main.setTotalDeposits(350000000.0);
        main.setTotalPayments(50000000.0);
        main.setTotalFeesCollected(25000000.0);
        main.setDepositVsLastPeriodPercent(22.0);
        main.setPaymentVsLastPeriodPercent(-6.0);
        main.setFeesVsLastPeriodPercent(15.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 150000000.0),
                makeFund(2L, "Investor Fund", 100000000.0),
                makeFund(3L, "DLD", 50000000.0),
                makeFund(4L, "Unit Installment", 30000000.0),
                makeFund(5L, "VAT", 20000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(101L, "Construction", 20000000.0),
                makeExpense(102L, "Marketing", 15000000.0),
                makeExpense(103L, "Legal Fees", 10000000.0),
                makeExpense(104L, "Admin", 5000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 8000000.0),
                makeFees(12L, "Service Charges", 10000000.0),
                makeFees(13L, "Legal Fees (collected)", 7000000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 220000000.0, 18.0),
                makeAccount("RETENTION", 40000000.0, 12.0),
                makeAccount("WAKALA", 25000000.0, 10.0),
                makeAccount("CORPORATE", 15000000.0, -3.0)
        ));
        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(31450L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 80),
                new StatusPercentDto("Unsold", 20),
                new StatusPercentDto("Freeze", 2),
                new StatusPercentDto("Resold", 5),
                new StatusPercentDto("Cancelled", 3)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(3L);
        g.setTotalGuaranteedAmount(125000000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 70000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 40000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 15000000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }

    private DashboardSummaryDto sample6() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(125000000.0);
        main.setTotalDeposits(160000000.0);
        main.setTotalPayments(35000000.0);
        main.setTotalFeesCollected(6000000.0);
        main.setDepositVsLastPeriodPercent(9.0);
        main.setPaymentVsLastPeriodPercent(-4.0);
        main.setFeesVsLastPeriodPercent(3.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 60000000.0),
                makeFund(2L, "Investor Fund", 50000000.0),
                makeFund(3L, "DLD", 20000000.0),
                makeFund(4L, "Unit Installment", 20000000.0),
                makeFund(5L, "VAT", 10000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(201L, "Construction", 20000000.0),
                makeExpense(202L, "Marketing", 8000000.0),
                makeExpense(203L, "Legal Fees", 4000000.0),
                makeExpense(204L, "Admin", 3000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 2500000.0),
                makeFees(12L, "Service Charges", 2000000.0),
                makeFees(13L, "Admin Fees", 1500000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 90000000.0, 12.0),
                makeAccount("RETENTION", 20000000.0, 8.0),
                makeAccount("WAKALA", 10000000.0, 5.0),
                makeAccount("CORPORATE", 5000000.0, -2.0)
        ));
        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(20678L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 60),
                new StatusPercentDto("Unsold", 60),
                new StatusPercentDto("Freeze", 76),
                new StatusPercentDto("Resold", 44),
                new StatusPercentDto("Cancelled", 56)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(3L);
        g.setTotalGuaranteedAmount(12500000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 6000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 4000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 2500000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }

    private DashboardSummaryDto sample7() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(80000000.0);
        main.setTotalDeposits(100000000.0);
        main.setTotalPayments(20000000.0);
        main.setTotalFeesCollected(5000000.0);
        main.setDepositVsLastPeriodPercent(-7.0);
        main.setPaymentVsLastPeriodPercent(3.0);
        main.setFeesVsLastPeriodPercent(-1.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 40000000.0),
                makeFund(2L, "Investor Fund", 30000000.0),
                makeFund(3L, "Unit Installment", 20000000.0),
                makeFund(4L, "VAT", 10000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(201L, "Construction", 12000000.0),
                makeExpense(202L, "Marketing", 5000000.0),
                makeExpense(203L, "Admin", 3000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 1200000.0),
                makeFees(12L, "Service Charges", 1800000.0),
                makeFees(13L, "Processing Fees", 200000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 60000000.0, -6.0),
                makeAccount("RETENTION", 10000000.0, 4.0),
                makeAccount("WAKALA", 6000000.0, 2.0),
                makeAccount("CORPORATE", 4000000.0, -3.0)
        ));
        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(18750L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 55),
                new StatusPercentDto("Unsold", 45),
                new StatusPercentDto("Freeze", 30),
                new StatusPercentDto("Resold", 20),
                new StatusPercentDto("Cancelled", 10)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(2L);
        g.setTotalGuaranteedAmount(20000000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 12000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 6000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 2000000.0)
        ));
        dto.setGuaranteeStatus(g);

        return dto;
    }

    private DashboardSummaryDto sample8() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(500000000.0);
        main.setTotalDeposits(600000000.0);
        main.setTotalPayments(100000000.0);
        main.setTotalFeesCollected(30000000.0);
        main.setDepositVsLastPeriodPercent(25.0);
        main.setPaymentVsLastPeriodPercent(-5.0);
        main.setFeesVsLastPeriodPercent(10.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 200000000.0),
                makeFund(2L, "Investor Fund", 150000000.0),
                makeFund(3L, "DLD", 100000000.0),
                makeFund(4L, "Unit Installment", 100000000.0),
                makeFund(5L, "VAT", 50000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(201L, "Construction", 70000000.0),
                makeExpense(202L, "Legal Fees", 15000000.0),
                makeExpense(203L, "Marketing", 10000000.0),
                makeExpense(204L, "Admin", 5000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 12000000.0),
                makeFees(12L, "Service Charges", 10000000.0),
                makeFees(13L, "Legal Fees", 8000000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 350000000.0, 20.0),
                makeAccount("RETENTION", 80000000.0, 15.0),
                makeAccount("WAKALA", 40000000.0, 10.0),
                makeAccount("CORPORATE", 30000000.0, -2.0)
        ));
        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(24000L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 70),
                new StatusPercentDto("Unsold", 30),
                new StatusPercentDto("Freeze", 5),
                new StatusPercentDto("Resold", 10),
                new StatusPercentDto("Cancelled", 15)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(4L);
        g.setTotalGuaranteedAmount(90000000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 50000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 30000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 10000000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }
    private DashboardSummaryDto sample9() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(40000000.0);
        main.setTotalDeposits(60000000.0);
        main.setTotalPayments(20000000.0);
        main.setTotalFeesCollected(3000000.0);
        main.setDepositVsLastPeriodPercent(-8.0);
        main.setPaymentVsLastPeriodPercent(6.0);
        main.setFeesVsLastPeriodPercent(2.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 20000000.0),
                makeFund(2L, "Investor Fund", 15000000.0),
                makeFund(3L, "Unit Installment", 15000000.0),
                makeFund(4L, "VAT", 10000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(201L, "Construction", 10000000.0),
                makeExpense(202L, "Marketing", 6000000.0),
                makeExpense(203L, "Admin", 4000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 800000.0),
                makeFees(12L, "Service Charges", 1200000.0),
                makeFees(13L, "Misc Fees", 1000000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 25000000.0, -5.0),
                makeAccount("RETENTION", 7000000.0, 3.0),
                makeAccount("WAKALA", 5000000.0, 0.0),
                makeAccount("CORPORATE", 3000000.0, -2.0)
        ));
        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(20678L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 60),
                new StatusPercentDto("Unsold", 60),
                new StatusPercentDto("Freeze", 76),
                new StatusPercentDto("Resold", 44),
                new StatusPercentDto("Cancelled", 56)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(3L);
        g.setTotalGuaranteedAmount(40000000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 20000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 12000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 8000000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }
    private DashboardSummaryDto sample10() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        MainTrustSummaryDto main = new MainTrustSummaryDto();

        main.setAvailableBalance(750000000.0);
        main.setTotalDeposits(900000000.0);
        main.setTotalPayments(150000000.0);
        main.setTotalFeesCollected(50000000.0);
        main.setDepositVsLastPeriodPercent(30.0);
        main.setPaymentVsLastPeriodPercent(-7.0);
        main.setFeesVsLastPeriodPercent(12.0);

        main.setDepositBreakdown(Arrays.asList(
                makeFund(1L, "Equity Fund", 300000000.0),
                makeFund(2L, "Investor Fund", 250000000.0),
                makeFund(3L, "DLD", 150000000.0),
                makeFund(4L, "Unit Installment", 150000000.0),
                makeFund(5L, "VAT", 50000000.0)
        ));

        main.setExpenseBreakdown(Arrays.asList(
                makeExpense(201L, "Construction", 100000000.0),
                makeExpense(202L, "Marketing", 20000000.0),
                makeExpense(203L, "Legal Fees", 15000000.0),
                makeExpense(204L, "Admin", 15000000.0)
        ));

        main.setFeesBreakdown(Arrays.asList(
                makeFees(11L, "Brokerage Fees", 20000000.0),
                makeFees(12L, "Service Charges", 15000000.0),
                makeFees(13L, "Legal Fees (collected)", 15000000.0)
        ));


        dto.setMainTrustSummary(main);
        dto.setOtherAccounts(Arrays.asList(
                makeAccount("TRUST", 500000000.0, 25.0),
                makeAccount("RETENTION", 100000000.0, 18.0),
                makeAccount("WAKALA", 80000000.0, 12.0),
                makeAccount("CORPORATE", 70000000.0, 8.0)
        ));
        // Unit Status
        UnitStatusDto unit = new UnitStatusDto();
        unit.setTotalCount(31450L);
        unit.setItems(Arrays.asList(
                new StatusPercentDto("Sold", 80),
                new StatusPercentDto("Unsold", 20),
                new StatusPercentDto("Freeze", 2),
                new StatusPercentDto("Resold", 5),
                new StatusPercentDto("Cancelled", 3)
        ));
        dto.setUnitStatus(unit);

        // Guarantee Status
        GuaranteeStatusDto g = new GuaranteeStatusDto();
        g.setTotalGuaranteesCount(5L);
        g.setTotalGuaranteedAmount(750000000.0);
        g.setItems(Arrays.asList(
                new GuaranteeTypeDto("Advanced Guarantee", 500000000.0),
                new GuaranteeTypeDto("Retention Guarantee", 150000000.0),
                new GuaranteeTypeDto("Performance Guarantee", 100000000.0)
        ));
        dto.setGuaranteeStatus(g);
        return dto;
    }




    // -------------------------------
    // HELPERS
    // -------------------------------

    private FundBreakdownDto makeFund(Long id, String name, Double amt) {
        FundBreakdownDto dto = new FundBreakdownDto();
        dto.setBucketTypeId(id);
        dto.setBucketTypeName(name);
        dto.setAmount(amt);
        return dto;
    }

    private ExpenseBreakdownDto makeExpense(Long id, String name, Double amt) {
        ExpenseBreakdownDto dto = new ExpenseBreakdownDto();
        dto.setExpenseTypeId(id);
        dto.setExpenseTypeName(name);
        dto.setAmount(amt);
        return dto;
    }

    private FeesBreakDownDto makeFees(Long id, String name, Double amt) {
        FeesBreakDownDto dto = new FeesBreakDownDto();
        dto.setExpenseTypeId(id);
        dto.setExpenseTypeName(name);
        dto.setAmount(amt);
        return dto;
    }

    private AccountBalanceDto makeAccount(String type, Double balance, Double change) {
        AccountBalanceDto dto = new AccountBalanceDto();
        dto.setAccountType(type);
        dto.setBalance(balance);
        dto.setChangeVsLastPeriodPercent(change);
        return dto;
    }
}
