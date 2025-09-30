package com.cdl.escrow.criteria;

import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.dto.RealEstateAssestDTO;
import com.cdl.escrow.filter.DoubleFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RealEstateAssestFinancialSummaryCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter reafsEstRevenue ;

    private DoubleFilter reafsEstConstructionCost;

    private DoubleFilter reafsEstProjectMgmtExpense ;

    private DoubleFilter reafsEstLandCost ;

    private DoubleFilter reafsEstMarketingExpense ;

    private ZonedDateTimeFilter reafsEstimatedDate;

    private StringFilter  reafsEstExceptionalCapVal ;

    private DoubleFilter reafsActualSoldValue ;

    private DoubleFilter reafsActualConstructionCost ;

    private DoubleFilter reafsActualInfraCost ;

    private DoubleFilter reafsActualLandCost ;

    private DoubleFilter reafsActualMarketingExp ;

    private DoubleFilter reafsActualProjectMgmtExpense ;

    private ZonedDateTimeFilter reafsActualDate ;

    private StringFilter  reafsActualexceptCapVal ;

    private DoubleFilter reafsCurrentCashReceived ;

    private DoubleFilter reafsCurCashRecvdOutEscrow ;

    private DoubleFilter reafsCurCashRecvdWithinEscrow ;

    private DoubleFilter reafsCurCashRecvdTotal ;

    private StringFilter reafsCurCashexceptCapVal ;

    private DoubleFilter reafsCurrentLandCost ;

    private DoubleFilter reafsCurLandCostOut ;

    private DoubleFilter reafsCurLandCostWithin ;

    private DoubleFilter reafsCurLandTotal ;

    private StringFilter reafsCurLandexceptCapVal ;

    private DoubleFilter reafsCurrentConstructionCost ;

    private DoubleFilter reafsCurConsCostWithin ;

    private DoubleFilter reafsCurConsCostOut ;

    private DoubleFilter reafsCurConsCostTotal ;

    private StringFilter reafsCurConsExcepCapVal ;

    private DoubleFilter reafsCurrentMarketingExp ;

    private DoubleFilter reafsCurrentMktgExpWithin ;

    private DoubleFilter reafsCurrentMktgExpOut ;

    private DoubleFilter reafsCurrentMktgExpTotal ;

    private StringFilter reafsCurrentmktgExcepCapVal ;

    private DoubleFilter reafsCurrentProjectMgmtExp ;

    private DoubleFilter reafsCurProjMgmtExpWithin ;

    private DoubleFilter reafsCurProjMgmtExpOut ;

    private DoubleFilter reafsCurProjMgmtExpTotal ;

    private StringFilter reafsCurProjExcepCapVal ;

    private DoubleFilter reafsCurrentMortgage ;

    private DoubleFilter reafsCurrentMortgageWithin ;

    private DoubleFilter currentMortgageOut ;

    private DoubleFilter reafsCurrentMortgageTotal ;

    private StringFilter reafsCurMortgageExceptCapVal ;

    private DoubleFilter reafsCurrentVatPayment ;

    private DoubleFilter reafsCurrentVatPaymentWithin ;

    private DoubleFilter reafsCurrentVatPaymentOut ;

    private DoubleFilter reafsCurrentVatPaymentTotal ;

    private StringFilter reafsCurVatExceptCapVal ;

    private DoubleFilter reafsCurrentOqood ;

    private DoubleFilter reafsCurrentOqoodWithin ;

    private DoubleFilter reafsCurrentOqoodOut ;

    private DoubleFilter reafsCurrentOqoodTotal ;

    private StringFilter reafsCurOqoodExceptCapVal ;

    private DoubleFilter reafsCurrentRefund ;

    private DoubleFilter reafsCurrentRefundWithin ;

    private DoubleFilter reafsCurrentRefundOut ;

    private DoubleFilter reafsCurrentRefundTotal ;

    private StringFilter reafsCurRefundExceptCapVal ;

    private DoubleFilter reafsCurrentBalInRetenAcc ;

    private DoubleFilter reafsCurBalInRetenAccWithin ;

    private DoubleFilter reafsCurBalInRetenAccOut ;

    private DoubleFilter reafsCurBalInRetenAccTotal ;

    private StringFilter reafsCurBalInRetenExceptCapVal ;

    private DoubleFilter reafsCurrentBalInTrustAcc ;

    private DoubleFilter reafsCurBalInTrustAccWithin ;

    private DoubleFilter reafsCurBalInTrustAccOut ;

    private DoubleFilter reafsCurBalInTrustAccTotal ;

    private StringFilter reafsCurBalInExceptCapVal ;

    private DoubleFilter reafsCurrentTechnicalFee ;

    private DoubleFilter reafsCurTechnFeeWithin ;

    private DoubleFilter reafsCurTechnFeeOut ;

    private DoubleFilter reafsCurTechnFeeTotal ;

    private StringFilter reafsCurTechFeeExceptCapVal ;

    private DoubleFilter reafsCurrentUnIdentifiedFund ;

    private DoubleFilter reafsCurUnIdeFundWithin ;

    private DoubleFilter reafsCurUnIdeFundOut ;

    private DoubleFilter reafsCurUnIdeFundTotal ;

    private StringFilter reafsCurUnIdeExceptCapVal ;

    private DoubleFilter reafsCurrentLoanInstal ;

    private DoubleFilter reafsCurLoanInstalWithin ;

    private DoubleFilter reafsCurLoanInstalOut ;

    private DoubleFilter reafsCurLoanInstalTotal ;

    private StringFilter reafsCurLoanExceptCapVal ;

    private DoubleFilter reafsCurrentInfraCost ;

    private DoubleFilter reafsCurInfraCostWithin ;

    private DoubleFilter reafsCurInfraCostOut ;

    private DoubleFilter reafsCurInfraCostTotal ;

    private StringFilter reafsCurInfraExceptCapVal ;

    private DoubleFilter reafsCurrentOthersCost ;

    private DoubleFilter reafsCurOthersCostWithin ;

    private DoubleFilter reafsCurOthersCostOut ;

    private DoubleFilter reafsCurOthersCostTotal ;

    private StringFilter reafsCurOthersExceptCapVal ;

    private DoubleFilter reafsCurrentTransferredCost ;

    private DoubleFilter reafsCurTransferCostWithin ;

    private DoubleFilter reafsCurTransferCostOut ;

    private DoubleFilter reafsCurTransferCostTotal ;

    private StringFilter reafsCurTransferExceptCapVal ;

    private DoubleFilter reafsCurrentForfeitedCost ;

    private DoubleFilter reafsCurForfeitCostWithin ;

    private DoubleFilter reafsCurForfeitCostOut ;

    private DoubleFilter reafsCurForfeitCostTotal ;

    private StringFilter reafsCurForfeitExceptCapVal ;

    private DoubleFilter reafsCurrentDeveloperEquitycost ;

    private DoubleFilter reafsCurDeveEqtycostWithin ;

    private DoubleFilter reafsCurDeveEqtycostOut ;

    private DoubleFilter reafsCurDeveEqtycostTotal ;

    private StringFilter reafsCurDeveExceptCapVal ;

    private DoubleFilter reafsCurrentAmantFund ;

    private DoubleFilter reafsCurAmntFundWithin ;

    private DoubleFilter reafsCurAmntFundOut ;

    private DoubleFilter reafsCurAmntFundTotal ;

    private StringFilter reafsCurAmntExceptCapVal ;

    private DoubleFilter reafsCurrentOtherWithdrawls ;

    private DoubleFilter reafsCurOtherWithdWithin ;

    private DoubleFilter reafsCurOtherWithdOut ;

    private DoubleFilter reafsCurOtherWithdTotal ;

    private StringFilter reafsCurOtherExceptCapVal ;

    private DoubleFilter reafsCurrentOqoodOtherFeePay ;

    private DoubleFilter reafsCurOqoodOthFeeWithin ;

    private DoubleFilter reafsCurOqoodOthFeeOut ;

    private DoubleFilter reafsCurOqoodOthFeeTotal ;

    private DoubleFilter reafsCurrentVatDeposit ;

    private DoubleFilter reafsCurVatDepositWithin ;

    private DoubleFilter reafsCurVatDepositOut ;

    private DoubleFilter reafsCurVatDepositTotal ;

    private StringFilter reafsCurVatDepositCapVal ;

    private DoubleFilter reafsCurBalConstructionTotal ;

    private DoubleFilter reafsCurBalConstructionWithin ;

    private DoubleFilter reafsCurBalConstructionOut ;

    private StringFilter reafsCurBalExcepCapVal ;

    private DoubleFilter reafsCreditInterest ;

    private DoubleFilter reafsPaymentForRetentionAcc ;

    private DoubleFilter reafsDeveloperReimburse ;

    private DoubleFilter reafsUnitRegFees ;

    private DoubleFilter reafsCreditInterestProfit ;

    private DoubleFilter reafsVatCappedCost ;

    private StringFilter reafsExceptionalCapVal ;

    private LongFilter buildPartnerId;

    private LongFilter realEstateAssestId;
}
