package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.ApplicationConfigurationCriteria;
import com.cdl.escrow.dto.ApplicationConfigurationDTO;
import com.cdl.escrow.entity.ApplicationConfiguration;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ApplicationConfigurationMapper;
import com.cdl.escrow.repository.ApplicationConfigurationRepository;
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
public class ApplicationConfigurationCriteriaService extends BaseSpecificationBuilder<ApplicationConfiguration> implements Serializable {

    private final transient ApplicationConfigurationRepository applicationConfigurationRepository;

    private final transient ApplicationConfigurationMapper applicationConfigurationMapper;

    public Page<ApplicationConfigurationDTO> findByCriteria(ApplicationConfigurationCriteria criteria, Pageable pageable) {
        Specification<ApplicationConfiguration> specification = createSpecification(criteria);
        return applicationConfigurationRepository.findAll(specification, pageable).map(applicationConfigurationMapper::toDto);
    }

    private Specification<ApplicationConfiguration> createSpecification(ApplicationConfigurationCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "configKey", criteria.getConfigKey(), true);
                addStringFilter(cb, root, predicates, "configValue", criteria.getConfigValue(), true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
