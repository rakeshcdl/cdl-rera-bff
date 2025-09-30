/**
 * AppLanguageCodeCriteriaService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.AppLanguageCodeCriteria;
import com.cdl.escrow.dto.AppLanguageCodeDTO;
import com.cdl.escrow.entity.AppLanguageCode;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.AppLanguageCodeMapper;
import com.cdl.escrow.repository.AppLanguageCodeRepository;
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
public class AppLanguageCodeCriteriaService extends BaseSpecificationBuilder<AppLanguageCode> implements Serializable {

    private final transient AppLanguageCodeRepository appLanguageCodeRepository;

    private final transient AppLanguageCodeMapper appLanguageCodeMapper;

    public Page<AppLanguageCodeDTO> findByCriteria(AppLanguageCodeCriteria criteria, Pageable pageable) {
        Specification<AppLanguageCode> specification = createSpecification(criteria);
        return appLanguageCodeRepository.findAll(specification, pageable).map(appLanguageCodeMapper::toDto);
    }

    private Specification<AppLanguageCode> createSpecification(AppLanguageCodeCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "languageCode", criteria.getLanguageCode(), true);
                addStringFilter(cb, root, predicates, "nameKey", criteria.getNameKey(), true);
                addStringFilter(cb, root, predicates, "nameNativeValue", criteria.getNameNativeValue(), true);
                addBooleanFilter(cb, root, predicates, "enabled", criteria.getEnabled());
                addBooleanFilter(cb, root, predicates, "isRtl", criteria.getIsRtl());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
