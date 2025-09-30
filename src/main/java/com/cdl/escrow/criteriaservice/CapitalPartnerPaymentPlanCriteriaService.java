package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.CapitalPartnerPaymentPlanCriteria;
import com.cdl.escrow.dto.CapitalPartnerPaymentPlanDTO;
import com.cdl.escrow.entity.CapitalPartnerPaymentPlan;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.CapitalPartnerPaymentPlanMapper;
import com.cdl.escrow.repository.CapitalPartnerPaymentPlanRepository;
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
public class CapitalPartnerPaymentPlanCriteriaService extends BaseSpecificationBuilder<CapitalPartnerPaymentPlan> implements Serializable {

    private final CapitalPartnerPaymentPlanRepository capitalPartnerPaymentPlanRepository;

    private final CapitalPartnerPaymentPlanMapper capitalPartnerPaymentPlanMapper;

    public Page<CapitalPartnerPaymentPlanDTO> findByCriteria(CapitalPartnerPaymentPlanCriteria criteria, Pageable pageable) {
        Specification<CapitalPartnerPaymentPlan> specification = createSpecification(criteria);
        return capitalPartnerPaymentPlanRepository.findAll(specification, pageable).map(capitalPartnerPaymentPlanMapper::toDto);
    }

    private Specification<CapitalPartnerPaymentPlan> createSpecification(CapitalPartnerPaymentPlanCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria != null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());

                if (criteria.getCapitalPartnerId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "capitalPartner", "id", criteria.getCapitalPartnerId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
