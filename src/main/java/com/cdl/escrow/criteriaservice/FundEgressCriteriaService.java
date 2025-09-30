package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.FundEgressCriteria;
import com.cdl.escrow.dto.FundEgressDTO;
import com.cdl.escrow.entity.FundEgress;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.FundEgressMapper;
import com.cdl.escrow.repository.FundEgressRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundEgressCriteriaService extends BaseSpecificationBuilder<FundEgress> implements Serializable {

    private final transient FundEgressRepository fundEgressRepository;

    private final transient FundEgressMapper fundEgressMapper;

    public Page<FundEgressDTO> findByCriteria(FundEgressCriteria criteria, Pageable pageable) {
        Specification<FundEgress> specification = createSpecification(criteria);
        return fundEgressRepository.findAll(specification, pageable).map(fundEgressMapper::toDto);
    }

    private Specification<FundEgress> createSpecification(FundEgressCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

           if(criteria!=null)
           {
               addLongFilter(cb, root, predicates, "id", criteria.getId());

               addStringFilter(cb, root, predicates, "feInvoiceNumber", criteria.getFeInvoiceNumber(), true);
               addZonedDateTimeFilter(cb, root, predicates, "fePaymentDate", criteria.getFePaymentDate());
               addDoubleFilter(cb, root, predicates, "fePaymentAmount", criteria.getFePaymentAmount());
               addStringFilter(cb, root, predicates, "feGlAccountNumber", criteria.getFeGlAccountNumber(), true);
               addStringFilter(cb, root, predicates, "feGlBranchCode", criteria.getFeGlBranchCode(), true);
               addStringFilter(cb, root, predicates, "feUnitRegistrationFee", criteria.getFeUnitRegistrationFee(), true);
               addStringFilter(cb, root, predicates, "feRemark", criteria.getFeRemark(), true);
               addStringFilter(cb, root, predicates, "fePaymentRefNumber", criteria.getFePaymentRefNumber(), true);
               addBooleanFilter(cb, root, predicates, "feSplitPayment", criteria.getFeSplitPayment());
               addZonedDateTimeFilter(cb, root, predicates, "feInvoiceDate", criteria.getFeInvoiceDate());
               addStringFilter(cb, root, predicates, "feRtZeroThree", criteria.getFeRtZeroThree(), true);
               addStringFilter(cb, root, predicates, "feEngineerRefNo", criteria.getFeEngineerRefNo(), true);
               addZonedDateTimeFilter(cb, root, predicates, "feEngineerApprovalDate", criteria.getFeEngineerApprovalDate());
               addStringFilter(cb, root, predicates, "feReraApprovedRefNo", criteria.getFeReraApprovedRefNo(), true);
               addZonedDateTimeFilter(cb, root, predicates, "feReraApprovedDate", criteria.getFeReraApprovedDate());
               addStringFilter(cb, root, predicates, "feInvoiceRefNo", criteria.getFeInvoiceRefNo(), true);
               addDoubleFilter(cb, root, predicates, "feEngineerApprovedAmt", criteria.getFeEngineerApprovedAmt());
               addDoubleFilter(cb, root, predicates, "feTotalEligibleAmtInv", criteria.getFeTotalEligibleAmtInv());
               addDoubleFilter(cb, root, predicates, "feAmtPaidAgainstInv", criteria.getFeAmtPaidAgainstInv());
               addStringFilter(cb, root, predicates, "feCapExcedded", criteria.getFeCapExcedded(), true);
               addDoubleFilter(cb, root, predicates, "feTotalAmountPaid", criteria.getFeTotalAmountPaid());
               addDoubleFilter(cb, root, predicates, "feDebitFromEscrow", criteria.getFeDebitFromEscrow());
               addDoubleFilter(cb, root, predicates, "feCurEligibleAmt", criteria.getFeCurEligibleAmt());
               addDoubleFilter(cb, root, predicates, "feDebitFromRetention", criteria.getFeDebitFromRetention());
               addDoubleFilter(cb, root, predicates, "feTotalPayoutAmt", criteria.getFeTotalPayoutAmt());
               addDoubleFilter(cb, root, predicates, "feAmountInTransit", criteria.getFeAmountInTransit());
               addStringFilter(cb, root, predicates, "feVarCapExcedded", criteria.getFeVarCapExcedded(), true);
               addDoubleFilter(cb, root, predicates, "feIndicativeRate", criteria.getFeIndicativeRate());
               addStringFilter(cb, root, predicates, "fePpcNumber", criteria.getFePpcNumber(), true);
               addBooleanFilter(cb, root, predicates, "feCorporatePayment", criteria.getFeCorporatePayment());
               addStringFilter(cb, root, predicates, "feNarration1", criteria.getFeNarration1(), true);
               addStringFilter(cb, root, predicates, "feNarration2", criteria.getFeNarration2(), true);
               addDoubleFilter(cb, root, predicates, "feAmtRecdFromUnitHolder", criteria.getFeAmtRecdFromUnitHolder());
               addBooleanFilter(cb, root, predicates, "feForFeit", criteria.getFeForFeit());
               addDoubleFilter(cb, root, predicates, "feForFeitAmt", criteria.getFeForFeitAmt());
               addBooleanFilter(cb, root, predicates, "feRefundToUnitHolder", criteria.getFeRefundToUnitHolder());
               addDoubleFilter(cb, root, predicates, "feRefundAmount", criteria.getFeRefundAmount());
               addBooleanFilter(cb, root, predicates, "feTransferToOtherUnit", criteria.getFeTransferToOtherUnit());
               addDoubleFilter(cb, root, predicates, "feTransferAmount", criteria.getFeTransferAmount());
               addStringFilter(cb, root, predicates, "feUnitReraApprovedRefNo", criteria.getFeUnitReraApprovedRefNo(), true);
               addZonedDateTimeFilter(cb, root, predicates, "feUnitTransferAppDate", criteria.getFeUnitTransferAppDate());
               addBooleanFilter(cb, root, predicates, "feBeneficiaryToMaster", criteria.getFeBeneficiaryToMaster());
               addDoubleFilter(cb, root, predicates, "feAmountToBeReleased", criteria.getFeAmountToBeReleased());
               addZonedDateTimeFilter(cb, root, predicates, "feBeneDateOfPayment", criteria.getFeBeneDateOfPayment());
               addDoubleFilter(cb, root, predicates, "feBeneVatPaymentAmt", criteria.getFeBeneVatPaymentAmt());
               addBooleanFilter(cb, root, predicates, "feIncludeInPayout", criteria.getFeIncludeInPayout());
               addDoubleFilter(cb, root, predicates, "fBbankCharges", criteria.getFBbankCharges());
               addBooleanFilter(cb, root, predicates, "feTasPaymentSuccess", criteria.getFeTasPaymentSuccess());
               addBooleanFilter(cb, root, predicates, "fetasPaymentRerun", criteria.getFetasPaymentRerun());
               addBooleanFilter(cb, root, predicates, "feDiscardPayment", criteria.getFeDiscardPayment());
               addBooleanFilter(cb, root, predicates, "feIsTasPayment", criteria.getFeIsTasPayment());
               addBooleanFilter(cb, root, predicates, "feIsManualPayment", criteria.getFeIsManualPayment());
               addStringFilter(cb, root, predicates, "feSpecialField1", criteria.getFeSpecialField1(), true);
               addStringFilter(cb, root, predicates, "feSpecialField2", criteria.getFeSpecialField2(), true);
               addStringFilter(cb, root, predicates, "feSpecialField3", criteria.getFeSpecialField3(), true);
               addStringFilter(cb, root, predicates, "feUniqueRefNo", criteria.getFeUniqueRefNo(), true);
               addStringFilter(cb, root, predicates, "fePaymentResponseObj", criteria.getFePaymentResponseObj(), true);
               addStringFilter(cb, root, predicates, "fePaymentStatus", criteria.getFePaymentStatus(), true);
               addStringFilter(cb, root, predicates, "feResPaymentRefNo", criteria.getFeResPaymentRefNo(), true);
               addStringFilter(cb, root, predicates, "feResUniqueRefNo", criteria.getFeResUniqueRefNo(), true);
               addStringFilter(cb, root, predicates, "feResHeader", criteria.getFeResHeader(), true);
               addDoubleFilter(cb, root, predicates, "feInvoiceValue", criteria.getFeInvoiceValue());
               addStringFilter(cb, root, predicates, "feSpecialField4", criteria.getFeSpecialField4(), true);
               addStringFilter(cb, root, predicates, "feSpecialField5", criteria.getFeSpecialField5(), true);
               addStringFilter(cb, root, predicates, "feSpecialField6", criteria.getFeSpecialField6(), true);
               addStringFilter(cb, root, predicates, "feTasPaymentStatus", criteria.getFeTasPaymentStatus(), true);
               addStringFilter(cb, root, predicates, "feCorpCertEngFee", criteria.getFeCorpCertEngFee(), true);
               addStringFilter(cb, root, predicates, "feSpecialField7", criteria.getFeSpecialField7(), true);
               addDoubleFilter(cb, root, predicates, "feCurBalInEscrowAcc", criteria.getFeCurBalInEscrowAcc());
               addDoubleFilter(cb, root, predicates, "feCurBalInRetentionAcc", criteria.getFeCurBalInRetentionAcc());
               addStringFilter(cb, root, predicates, "feEngineerFeePayment", criteria.getFeEngineerFeePayment(), true);
               addDoubleFilter(cb, root, predicates, "feCorporateAccBalance", criteria.getFeCorporateAccBalance());
               addDoubleFilter(cb, root, predicates, "feSubConsAccBalance", criteria.getFeSubConsAccBalance());
               addStringFilter(cb, root, predicates, "feErrorResponseObject", criteria.getFeErrorResponseObject(), true);
               addStringFilter(cb, root, predicates, "fePropertyRegistrationFee", criteria.getFePropertyRegistrationFee(), true);
               addDoubleFilter(cb, root, predicates, "feBalanceAmount", criteria.getFeBalanceAmount());
               addBooleanFilter(cb, root, predicates, "feDocVerified", criteria.getFeDocVerified());
               addStringFilter(cb, root, predicates, "fePaymentBodyObj", criteria.getFePaymentBodyObj(), true);
               addStringFilter(cb, root, predicates, "feDealRefNo", criteria.getFeDealRefNo(), true);
               addBooleanFilter(cb, root, predicates, "feSpecialRate", criteria.getFeSpecialRate());
               addDoubleFilter(cb, root, predicates, "feTreasuryRate", criteria.getFeTreasuryRate());
               addBooleanFilter(cb, root, predicates, "feBenefFromProject", criteria.getFeBenefFromProject());
               addDoubleFilter(cb, root, predicates, "feCorporatePaymentEngFee", criteria.getFeCorporatePaymentEngFee());
               addBooleanFilter(cb, root, predicates, "feIsEngineerFee", criteria.getFeIsEngineerFee());

               addLongFilter(cb, root, predicates, "paymentStatusOptionId", criteria.getPaymentStatusOptionId());
               addLongFilter(cb, root, predicates, "voucherPaymentTypeId", criteria.getVoucherPaymentTypeId());
               addLongFilter(cb, root, predicates, "voucherPaymentSubTypeId", criteria.getVoucherPaymentSubTypeId());
               addLongFilter(cb, root, predicates, "expenseTypeId", criteria.getExpenseTypeId());
               addLongFilter(cb, root, predicates, "expenseSubTypeId", criteria.getExpenseSubTypeId());
               addLongFilter(cb, root, predicates, "invoiceCurrencyId", criteria.getInvoiceCurrencyId());
               addLongFilter(cb, root, predicates, "paymentCurrencyId", criteria.getPaymentCurrencyId());
               addLongFilter(cb, root, predicates, "chargedCodeId", criteria.getChargedCodeId());
               addLongFilter(cb, root, predicates, "paymentModeId", criteria.getPaymentModeId());
               addLongFilter(cb, root, predicates, "transactionTypeId", criteria.getTransactionTypeId());
               addLongFilter(cb, root, predicates, "beneficiaryFeePaymentId", criteria.getBeneficiaryFeePaymentId());
               addLongFilter(cb, root, predicates, "payoutToBeMadeFromCbsId", criteria.getPayoutToBeMadeFromCbsId());
               addLongFilter(cb, root, predicates, "realEstateAssestId", criteria.getRealEstateAssestId());
               addLongFilter(cb, root, predicates, "capitalPartnerUnitId", criteria.getCapitalPartnerUnitId());
               addLongFilter(cb, root, predicates, "transferCapitalPartnerUnitId", criteria.getTransferCapitalPartnerUnitId());
               addLongFilter(cb, root, predicates, "buildPartnerId", criteria.getBuildPartnerId());
               addLongFilter(cb, root, predicates, "realEstateAssestBeneficiaryId", criteria.getRealEstateAssestBeneficiaryId());
               addLongFilter(cb, root, predicates, "suretyBondId", criteria.getSuretyBondId());

               addBooleanFilter(cb, root, predicates, "deleted", criteria.getDeleted());
               addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());

           }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
