package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.ApplicationSettingCriteria;
import com.cdl.escrow.dto.ApplicationSettingDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ApplicationSettingMapper;
import com.cdl.escrow.repository.ApplicationSettingRepository;
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
public class ApplicationSettingCriteriaService extends BaseSpecificationBuilder<ApplicationSetting> implements Serializable {
    private final transient ApplicationSettingRepository applicationSettingRepository;

    private final transient ApplicationSettingMapper applicationSettingMapper;

    public Page<ApplicationSettingDTO> findByCriteria(ApplicationSettingCriteria criteria, Pageable pageable) {
        Specification<ApplicationSetting> specification = createSpecification(criteria);
        return applicationSettingRepository.findAll(specification, pageable).map(applicationSettingMapper::toDto);
    }

    private Specification<ApplicationSetting> createSpecification(ApplicationSettingCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "settingKey", criteria.getSettingKey(), true);
                addStringFilter(cb, root, predicates, "settingValue", criteria.getSettingValue(), true);
                addStringFilter(cb, root, predicates, "languageTranslationId", criteria.getLanguageTranslationId(), true);
                addStringFilter(cb, root, predicates, "remarks", criteria.getRemarks(), true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
