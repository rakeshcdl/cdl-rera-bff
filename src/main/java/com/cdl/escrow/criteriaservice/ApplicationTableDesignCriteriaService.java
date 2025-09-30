package com.cdl.escrow.criteriaservice;



import com.cdl.escrow.criteria.ApplicationTableDesignCriteria;
import com.cdl.escrow.dto.ApplicationTableDesignDTO;
import com.cdl.escrow.entity.ApplicationTableDesign;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ApplicationTableDesignMapper;
import com.cdl.escrow.repository.ApplicationTableDesignRepository;
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
public class ApplicationTableDesignCriteriaService extends BaseSpecificationBuilder<ApplicationTableDesign> implements Serializable {

    private final transient ApplicationTableDesignRepository applicationTableDesignRepository;

    private final transient ApplicationTableDesignMapper applicationTableDesignMapper;

    public Page<ApplicationTableDesignDTO> findByCriteria(ApplicationTableDesignCriteria criteria, Pageable pageable) {
        Specification<ApplicationTableDesign> specification = createSpecification(criteria);
        return applicationTableDesignRepository.findAll(specification, pageable).map(applicationTableDesignMapper::toDto);
    }

    private Specification<ApplicationTableDesign> createSpecification(ApplicationTableDesignCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "tableName", criteria.getTableName(), true);
                addStringFilter(cb, root, predicates, "tableDefinition", criteria.getTableDefinition(), true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
