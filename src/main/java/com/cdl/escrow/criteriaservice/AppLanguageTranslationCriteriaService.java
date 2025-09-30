/**
 * AppLanguageTranslationCriteriaService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 18/07/25
 */


package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.AppLanguageTranslationCriteria;
import com.cdl.escrow.dto.AppLanguageTranslationDTO;
import com.cdl.escrow.entity.AppLanguageTranslation;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.AppLanguageTranslationMapper;
import com.cdl.escrow.repository.AppConfigTranslationRepository;
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
public class AppLanguageTranslationCriteriaService extends BaseSpecificationBuilder<AppLanguageTranslation> implements Serializable {

    private final transient AppConfigTranslationRepository appConfigTranslationRepository;
    private final transient AppLanguageTranslationMapper appLanguageTranslationMapper;


    public Page<AppLanguageTranslationDTO> findByCriteria(AppLanguageTranslationCriteria criteria, Pageable pageable) {
        Specification<AppLanguageTranslation> specification = createSpecification(criteria);
        return appConfigTranslationRepository.findAll(specification, pageable).map(appLanguageTranslationMapper::toDto);
    }

    private Specification<AppLanguageTranslation> createSpecification(AppLanguageTranslationCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "configId", criteria.getConfigId(), true);
                addStringFilter(cb, root, predicates, "configValue", criteria.getConfigValue(), true);
                addStringFilter(cb, root, predicates, "content", criteria.getContent(), true);

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
