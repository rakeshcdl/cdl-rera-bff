package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.RealEstateAssestPaymentPlanCriteria;
import com.cdl.escrow.dto.RealEstateAssestPaymentPlanDTO;
import com.cdl.escrow.entity.RealEstateAssestPaymentPlan;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.RealEstateAssestPaymentPlanMapper;
import com.cdl.escrow.repository.RealEstateAssestPaymentPlanRepository;
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
public class RealEstateAssestPaymentPlanCriteriaService  extends BaseSpecificationBuilder<RealEstateAssestPaymentPlan> implements Serializable {

    private final transient RealEstateAssestPaymentPlanRepository realEstateAssestPaymentPlanRepository;

    private final transient RealEstateAssestPaymentPlanMapper realEstateAssestPaymentPlanMapper;

    public Page<RealEstateAssestPaymentPlanDTO> findByCriteria(RealEstateAssestPaymentPlanCriteria criteria, Pageable pageable) {
        Specification<RealEstateAssestPaymentPlan> specification = createSpecification(criteria);
        return realEstateAssestPaymentPlanRepository.findAll(specification, pageable).map(realEstateAssestPaymentPlanMapper::toDto);
    }

    private Specification<RealEstateAssestPaymentPlan> createSpecification(RealEstateAssestPaymentPlanCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());


                // Relation Join
                if (criteria.getRealEstateAssestId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "realEstateAssest", "id", criteria.getRealEstateAssestId());
                }


            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
