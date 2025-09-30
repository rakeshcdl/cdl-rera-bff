package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.CapitalPartnerUnitPurchaseCriteria;
import com.cdl.escrow.dto.CapitalPartnerUnitPurchaseDTO;
import com.cdl.escrow.entity.CapitalPartnerUnitPurchase;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.CapitalPartnerUnitPurchaseMapper;
import com.cdl.escrow.repository.CapitalPartnerUnitPurchaseRepository;
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
public class CapitalPartnerUnitPurchaseCriteriaService extends BaseSpecificationBuilder<CapitalPartnerUnitPurchase> implements Serializable {

    private final transient CapitalPartnerUnitPurchaseRepository capitalPartnerUnitPurchaseRepository;

    private final transient CapitalPartnerUnitPurchaseMapper capitalPartnerUnitPurchaseMapper;

    public Page<CapitalPartnerUnitPurchaseDTO> findByCriteria(CapitalPartnerUnitPurchaseCriteria criteria, Pageable pageable) {
        Specification<CapitalPartnerUnitPurchase> specification = createSpecification(criteria);
        return capitalPartnerUnitPurchaseRepository.findAll(specification, pageable).map(capitalPartnerUnitPurchaseMapper::toDto);
    }


    public Specification<CapitalPartnerUnitPurchase> createSpecification(CapitalPartnerUnitPurchaseCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                // Long Filters
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addLongFilter(cb, root, predicates, "cpupCreditCurrencyId", criteria.getCpupCreditCurrencyId());
                addLongFilter(cb, root, predicates, "cpuPurchasePriceCurrencyId", criteria.getCpuPurchasePriceCurrencyId());
                //addLongFilter(cb, root, predicates, "capitalPartnerUnitId", criteria.getCapitalPartnerUnitId());

                // ZonedDateTime Filters
                addZonedDateTimeFilter(cb, root, predicates, "cpuPurchaseDate", criteria.getCpuPurchaseDate());
                addZonedDateTimeFilter(cb, root, predicates, "cpupAgreementDate", criteria.getCpupAgreementDate());

                // Double Filters
                addDoubleFilter(cb, root, predicates, "cpupSaleRate", criteria.getCpupSaleRate());
                addDoubleFilter(cb, root, predicates, "cpuPurchasePrice", criteria.getCpuPurchasePrice());
                addDoubleFilter(cb, root, predicates, "cpupUnitRegistrationFee", criteria.getCpupUnitRegistrationFee());
                addDoubleFilter(cb, root, predicates, "cpupGrossSaleprice", criteria.getCpupGrossSaleprice());
                addDoubleFilter(cb, root, predicates, "cpupAmtPaidToDevInEscorw", criteria.getCpupAmtPaidToDevInEscorw());
                addDoubleFilter(cb, root, predicates, "cpupAmtPaidToDevOutEscorw", criteria.getCpupAmtPaidToDevOutEscorw());
                addDoubleFilter(cb, root, predicates, "cpupTotalAmountPaid", criteria.getCpupTotalAmountPaid());
                addDoubleFilter(cb, root, predicates, "cpupSalePrice", criteria.getCpupSalePrice());

                // String Filters
                addStringFilter(cb, root, predicates, "cpupAgentName", criteria.getCpupAgentName(), true);
                addStringFilter(cb, root, predicates, "cpupAgentId", criteria.getCpupAgentId(), true);
                addStringFilter(cb, root, predicates, "cpupDeedNo", criteria.getCpupDeedNo(), true);
                addStringFilter(cb, root, predicates, "cpupAgreementNo", criteria.getCpupAgreementNo(), true);
                addStringFilter(cb, root, predicates, "cpupUnitIban", criteria.getCpupUnitIban(), true);
                addStringFilter(cb, root, predicates, "cpupOqoodAmountPaid", criteria.getCpupOqoodAmountPaid(), true);
                addStringFilter(cb, root, predicates, "cpupUnitAreaSize", criteria.getCpupUnitAreaSize(), true);
                addStringFilter(cb, root, predicates, "cpupForfeitAmount", criteria.getCpupForfeitAmount(), true);
                addStringFilter(cb, root, predicates, "cpupDldAmount", criteria.getCpupDldAmount(), true);
                addStringFilter(cb, root, predicates, "cpupRefundAmount", criteria.getCpupRefundAmount(), true);
                addStringFilter(cb, root, predicates, "cpupRemarks", criteria.getCpupRemarks(), true);
                addStringFilter(cb, root, predicates, "cpupTransferredAmount", criteria.getCpupTransferredAmount(), true);
                addStringFilter(cb, root, predicates, "cpupUnitNoOtherFormat", criteria.getCpupUnitNoOtherFormat(), true);

                // Boolean Filters
                addBooleanFilter(cb, root, predicates, "cpupVatApplicable", criteria.getCpupVatApplicable());
                addBooleanFilter(cb, root, predicates, "cpupSalePurchaseAgreement", criteria.getCpupSalePurchaseAgreement());
                addBooleanFilter(cb, root, predicates, "cpupWorldCheck", criteria.getCpupWorldCheck());
                addBooleanFilter(cb, root, predicates, "cpupOqood", criteria.getCpupOqood());
                addBooleanFilter(cb, root, predicates, "cpupOqoodPaid", criteria.getCpupOqoodPaid());
                addBooleanFilter(cb, root, predicates, "cpupProjectPaymentPlan", criteria.getCpupProjectPaymentPlan());
                addBooleanFilter(cb, root, predicates, "cpupReservationBookingForm", criteria.getCpupReservationBookingForm());

                // Relation Join
                if (criteria.getCapitalPartnerUnitId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "capitalPartnerUnit", "id", criteria.getCapitalPartnerUnitId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
