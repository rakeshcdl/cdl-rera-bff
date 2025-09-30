package com.cdl.escrow.criteriaservice;



import com.cdl.escrow.criteria.ApplicationModuleCriteria;
import com.cdl.escrow.dto.ApplicationModuleDTO;
import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ApplicationModuleMapper;
import com.cdl.escrow.repository.ApplicationModuleRepository;
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
public class ApplicationModuleCriteriaService extends BaseSpecificationBuilder<ApplicationModule> implements Serializable {

    private final transient ApplicationModuleRepository applicationModuleRepository;

    private final transient ApplicationModuleMapper applicationModuleMapper;

    public Page<ApplicationModuleDTO> findByCriteria(ApplicationModuleCriteria criteria, Pageable pageable) {
        Specification<ApplicationModule> specification = createSpecification(criteria);
        return applicationModuleRepository.findAll(specification, pageable).map(applicationModuleMapper::toDto);
    }

    private Specification<ApplicationModule> createSpecification(ApplicationModuleCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "moduleName", criteria.getModuleName(), true);
                addStringFilter(cb, root, predicates, "moduleDescription", criteria.getModuleDescription(), true);
                addBooleanFilter(cb, root, predicates, "isActive", criteria.getIsActive());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
