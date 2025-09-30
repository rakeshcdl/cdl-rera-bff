package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.FeatureFlagCriteria;
import com.cdl.escrow.dto.FeatureFlagDTO;
import com.cdl.escrow.entity.FeatureFlag;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.FeatureFlagMapper;
import com.cdl.escrow.repository.FeatureFlagRepository;
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
public class FeatureFlagCriteriaService extends BaseSpecificationBuilder<FeatureFlag> implements Serializable {

    private final transient FeatureFlagRepository featureFlagRepository;

    private final transient FeatureFlagMapper featureFlagMapper;

    public Page<FeatureFlagDTO> findByCriteria(FeatureFlagCriteria criteria, Pageable pageable) {
        Specification<FeatureFlag> specification = createSpecification(criteria);
        return featureFlagRepository.findAll(specification, pageable).map(featureFlagMapper::toDto);
    }

    public Specification<FeatureFlag> createSpecification(FeatureFlagCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                // Long Filter
                addLongFilter(cb, root, predicates, "id", criteria.getId());

                // String Filters
                addStringFilter(cb, root, predicates, "featureName", criteria.getFeatureName(), true);
                addStringFilter(cb, root, predicates, "featureDescription", criteria.getFeatureDescription(), true);
                addBooleanFilter(cb, root, predicates, "", criteria.getIsEnabled());
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
