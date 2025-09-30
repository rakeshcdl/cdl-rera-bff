package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.ConfigurationStoreCriteria;
import com.cdl.escrow.dto.ConfigurationStoreDTO;
import com.cdl.escrow.entity.ConfigurationStore;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.ConfigurationStoreMapper;
import com.cdl.escrow.repository.ConfigurationStoreRepository;
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
public class ConfigurationStoreCriteriaService extends BaseSpecificationBuilder<ConfigurationStore>  implements Serializable {

    private final transient ConfigurationStoreRepository configurationStoreRepository;

    private final transient ConfigurationStoreMapper configurationStoreMapper;

    public Page<ConfigurationStoreDTO> findByCriteria(ConfigurationStoreCriteria criteria, Pageable pageable) {
        Specification<ConfigurationStore> specification = createSpecification(criteria);
        return configurationStoreRepository.findAll(specification, pageable).map(configurationStoreMapper::toDto);
    }


    public Specification<ConfigurationStore> createSpecification(ConfigurationStoreCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                // Long Filter
                addLongFilter(cb, root, predicates, "id", criteria.getId());

                // String Filters
                addStringFilter(cb, root, predicates, "key", criteria.getKey(), true);
                addStringFilter(cb, root, predicates, "value", criteria.getValue(), true);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
