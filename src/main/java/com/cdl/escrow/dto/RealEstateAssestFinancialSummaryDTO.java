package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateAssestFinancialSummaryDTO implements Serializable {
    private Long id ;

    private String reafsEstRevenue ;

    private Double reafsEstConstructionCost;

    private Double reafsEstProjectMgmtExpense ;

    private Double reafsEstLandCost ;

    private Double reafsEstMarketingExpense ;

    private ZonedDateTime reafsEstimatedDate;

    private String  reafsEstExceptionalCapVal ;

    private Double reafsActualSoldValue ;

    private Double reafsActualConstructionCost ;

    private Double reafsActualInfraCost ;

    private Double reafsActualLandCost ;

    private Double reafsActualMarketingExp ;

    private Double reafsActualProjectMgmtExpense ;

    private ZonedDateTime reafsActualDate ;

    private String  reafsActualexceptCapVal ;

    private Double reafsCurrentCashReceived ;

    private Double reafsCurCashRecvdOutEscrow ;

    private Double reafsCurCashRecvdWithinEscrow ;

    private Double reafsCurCashRecvdTotal ;

    private String reafsCurCashexceptCapVal ;

    private Double reafsCurrentLandCost ;

    private Double reafsCurLandCostOut ;

    private Double reafsCurLandCostWithin ;

    private Double reafsCurLandTotal ;

    private String reafsCurLandexceptCapVal ;

    private Double reafsCurrentConstructionCost ;

    private Double reafsCurConsCostWithin ;

    private Double reafsCurConsCostOut ;

    private Double reafsCurConsCostTotal ;

    private String reafsCurConsExcepCapVal ;

    private Double reafsCurrentMarketingExp ;

    private Double reafsCurrentMktgExpWithin ;

    private Double reafsCurrentMktgExpOut ;

    private Double reafsCurrentMktgExpTotal ;

    private String reafsCurrentmktgExcepCapVal ;

    private Double reafsCurrentProjectMgmtExp ;

    private Double reafsCurProjMgmtExpWithin ;

    private Double reafsCurProjMgmtExpOut ;

    private Double reafsCurProjMgmtExpTotal ;

    private String reafsCurProjExcepCapVal ;

    private Double reafsCurrentMortgage ;

    private Double reafsCurrentMortgageWithin ;

    private Double currentMortgageOut ;

    private Double reafsCurrentMortgageTotal ;

    private String reafsCurMortgageExceptCapVal ;

    private Double reafsCurrentVatPayment ;

    private Double reafsCurrentVatPaymentWithin ;

    private Double reafsCurrentVatPaymentOut ;

    private Double reafsCurrentVatPaymentTotal ;

    private String reafsCurVatExceptCapVal ;

    private Double reafsCurrentOqood ;

    private Double reafsCurrentOqoodWithin ;

    private Double reafsCurrentOqoodOut ;

    private Double reafsCurrentOqoodTotal ;

    private String reafsCurOqoodExceptCapVal ;

    private Double reafsCurrentRefund ;

    private Double reafsCurrentRefundWithin ;

    private Double reafsCurrentRefundOut ;

    private Double reafsCurrentRefundTotal ;

    private String reafsCurRefundExceptCapVal ;

    private Double reafsCurrentBalInRetenAcc ;

    private Double reafsCurBalInRetenAccWithin ;

    private Double reafsCurBalInRetenAccOut ;

    private Double reafsCurBalInRetenAccTotal ;

    private String reafsCurBalInRetenExceptCapVal ;

    private Double reafsCurrentBalInTrustAcc ;

    private Double reafsCurBalInTrustAccWithin ;

    private Double reafsCurBalInTrustAccOut ;

    private Double reafsCurBalInTrustAccTotal ;

    private String reafsCurBalInExceptCapVal ;

    private Double reafsCurrentTechnicalFee ;

    private Double reafsCurTechnFeeWithin ;

    private Double reafsCurTechnFeeOut ;

    private Double reafsCurTechnFeeTotal ;

    private String reafsCurTechFeeExceptCapVal ;

    private Double reafsCurrentUnIdentifiedFund ;

    private Double reafsCurUnIdeFundWithin ;

    private Double reafsCurUnIdeFundOut ;

    private Double reafsCurUnIdeFundTotal ;

    private String reafsCurUnIdeExceptCapVal ;

    private Double reafsCurrentLoanInstal ;

    private Double reafsCurLoanInstalWithin ;

    private Double reafsCurLoanInstalOut ;

    private Double reafsCurLoanInstalTotal ;

    private String reafsCurLoanExceptCapVal ;

    private Double reafsCurrentInfraCost ;

    private Double reafsCurInfraCostWithin ;

    private Double reafsCurInfraCostOut ;

    private Double reafsCurInfraCostTotal ;

    private String reafsCurInfraExceptCapVal ;

    private Double reafsCurrentOthersCost ;

    private Double reafsCurOthersCostWithin ;

    private Double reafsCurOthersCostOut ;

    private Double reafsCurOthersCostTotal ;

    private String reafsCurOthersExceptCapVal ;

    private Double reafsCurrentTransferredCost ;

    private Double reafsCurTransferCostWithin ;

    private Double reafsCurTransferCostOut ;

    private Double reafsCurTransferCostTotal ;

    private String reafsCurTransferExceptCapVal ;

    private Double reafsCurrentForfeitedCost ;

    private Double reafsCurForfeitCostWithin ;

    private Double reafsCurForfeitCostOut ;

    private Double reafsCurForfeitCostTotal ;

    private String reafsCurForfeitExceptCapVal ;

    private Double reafsCurrentDeveloperEquitycost ;

    private Double reafsCurDeveEqtycostWithin ;

    private Double reafsCurDeveEqtycostOut ;

    private Double reafsCurDeveEqtycostTotal ;

    private String reafsCurDeveExceptCapVal ;

    private Double reafsCurrentAmantFund ;

    private Double reafsCurAmntFundWithin ;

    private Double reafsCurAmntFundOut ;

    private Double reafsCurAmntFundTotal ;

    private String reafsCurAmntExceptCapVal ;

    private Double reafsCurrentOtherWithdrawls ;

    private Double reafsCurOtherWithdWithin ;

    private Double reafsCurOtherWithdOut ;

    private Double reafsCurOtherWithdTotal ;

    private String reafsCurOtherExceptCapVal ;

    private Double reafsCurrentOqoodOtherFeePay ;

    private Double reafsCurOqoodOthFeeWithin ;

    private Double reafsCurOqoodOthFeeOut ;

    private Double reafsCurOqoodOthFeeTotal ;

    private Double reafsCurrentVatDeposit ;

    private Double reafsCurVatDepositWithin ;

    private Double reafsCurVatDepositOut ;

    private Double reafsCurVatDepositTotal ;

    private String reafsCurVatDepositCapVal ;

    private Double reafsCurBalConstructionTotal ;

    private Double reafsCurBalConstructionWithin ;

    private Double reafsCurBalConstructionOut ;

    private String reafsCurBalExcepCapVal ;

    private Double reafsCreditInterest ;

    private Double reafsPaymentForRetentionAcc ;

    private Double reafsDeveloperReimburse ;

    private Double reafsUnitRegFees ;

    private Double reafsCreditInterestProfit ;

    private Double reafsVatCappedCost ;

    private String reafsExceptionalCapVal ;

    private Double reafsCurrentBalInSubsConsAcc ;

    private Double reafsCurBalInRSubsConsWithin ;

    private Double reafsCurBalInSubsConsOut ;

    private Double reafsCurBalInSubsConsTotal ;

    private String reafsCurBalInSubsConsCapVal ;

    private String reafsOtherFeesAnPaymentExcepVal;

   // private BuildPartnerDTO buildPartnerDTO;

    private RealEstateAssestDTO realEstateAssestDTO;
    private Boolean deleted ;

    private boolean enabled ;
}
