package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.ApplicationFormDesignCriteria;
import com.cdl.escrow.dto.ApplicationFormDesignDTO;
import com.cdl.escrow.entity.ApplicationFormDesign;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ApplicationFormDesignMapper;
import com.cdl.escrow.repository.ApplicationFormDesignRepository;
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
public class ApplicationFormDesignCriteriaService extends BaseSpecificationBuilder<ApplicationFormDesign> implements Serializable {

    private final transient ApplicationFormDesignRepository applicationFormDesignRepository;

    private final transient ApplicationFormDesignMapper applicationFormDesignMapper;

    public Page<ApplicationFormDesignDTO> findByCriteria(ApplicationFormDesignCriteria criteria, Pageable pageable) {
        Specification<ApplicationFormDesign> specification = createSpecification(criteria);
        return applicationFormDesignRepository.findAll(specification, pageable).map(applicationFormDesignMapper::toDto);
    }

    private Specification<ApplicationFormDesign> createSpecification(ApplicationFormDesignCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "formName", criteria.getFormName(), true);
                addStringFilter(cb, root, predicates, "formDefinition", criteria.getFormDefinition(), true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
