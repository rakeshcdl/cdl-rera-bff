package com.cdl.escrow.criteriaservice;



import com.cdl.escrow.criteria.BuildPartnerFeesCriteria;
import com.cdl.escrow.dto.BuildPartnerFeesDTO;
import com.cdl.escrow.entity.BuildPartnerFees;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BuildPartnerFeesMapper;
import com.cdl.escrow.repository.BuildPartnerFeesRepository;
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
public class BuildPartnerFeesCriteriaService extends BaseSpecificationBuilder<BuildPartnerFees> implements Serializable {

    private final transient BuildPartnerFeesRepository buildPartnerFeesRepository;

    private final transient BuildPartnerFeesMapper buildPartnerFeesMapper;

    public Page<BuildPartnerFeesDTO> findByCriteria(BuildPartnerFeesCriteria criteria, Pageable pageable) {
        Specification<BuildPartnerFees> specification = createSpecification(criteria);
        return buildPartnerFeesRepository.findAll(specification, pageable).map(buildPartnerFeesMapper::toDto);
    }

    private Specification<BuildPartnerFees> createSpecification(BuildPartnerFeesCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria != null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addDoubleFilter(cb, root, predicates, "debitAmount", criteria.getDebitAmount());
                addDoubleFilter(cb, root, predicates, "totalAmount", criteria.getTotalAmount());
                addZonedDateTimeFilter(cb, root, predicates, "feeCollectionDate", criteria.getFeeCollectionDate());
                addZonedDateTimeFilter(cb, root, predicates, "feeNextRecoveryDate", criteria.getFeeNextRecoveryDate());
                addDoubleFilter(cb, root, predicates, "feePercentage", criteria.getFeePercentage());
                addDoubleFilter(cb, root, predicates, "vatPercentage", criteria.getVatPercentage());
                addBooleanFilter(cb, root, predicates, "feeCollected", criteria.getFeeCollected());

                // WorkflowStatus enum
                if (criteria.getStatus() != null) {
                    predicates.add(cb.equal(root.get("workflowStatus"), criteria.getStatus()));
                }

                // Boolean enabled
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());

                // Enum or direct equals


                // Relation Join
                if (criteria.getBpFeeCategoryId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "bpFeeCategory", "id", criteria.getBpFeeCategoryId());
                }
                if (criteria.getBpFeeFrequencyId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "bpFeeFrequency", "id", criteria.getBpFeeFrequencyId());
                }
                if (criteria.getBpFeeCurrencyId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "bpFeeCurrency", "id", criteria.getBpFeeCurrencyId());
                }
                if (criteria.getBpAccountTypeId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "bpAccountType", "id", criteria.getBpAccountTypeId());
                }

                if (criteria.getBuildPartnerId() != null) {
                    addLongFilterForJoin(cb, root, predicates, "buildPartner", "id", criteria.getBuildPartnerId());
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
