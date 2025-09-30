package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.ApplicationUserCriteria;
import com.cdl.escrow.dto.ApplicationUserDTO;
import com.cdl.escrow.entity.ApplicationUser;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ApplicationUserMapper;
import com.cdl.escrow.repository.ApplicationUserRepository;
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
public class ApplicationUserCriteriaService extends BaseSpecificationBuilder<ApplicationUser> implements Serializable {

    private final transient ApplicationUserRepository applicationUserRepository;

    private final transient ApplicationUserMapper applicationUserMapper;


    public Page<ApplicationUserDTO> findByCriteria(ApplicationUserCriteria criteria, Pageable pageable) {
        Specification<ApplicationUser> specification = createSpecification(criteria);
        return applicationUserRepository.findAll(specification, pageable).map(applicationUserMapper::toDto);
    }

    private Specification<ApplicationUser> createSpecification(ApplicationUserCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "username", criteria.getUsername(), true);
                addStringFilter(cb, root, predicates, "password", criteria.getPassword(), true);
                addStringFilter(cb, root, predicates, "email", criteria.getEmail(), true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

