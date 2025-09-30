package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.BinaryDataStoreCriteria;
import com.cdl.escrow.dto.BinaryDataStoreDTO;
import com.cdl.escrow.entity.BinaryDataStore;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BinaryDataStoreMapper;
import com.cdl.escrow.repository.BinaryDataStoreRepository;
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
public class BinaryDataStoreCriteriaService extends BaseSpecificationBuilder<BinaryDataStore> implements Serializable {

    private final transient BinaryDataStoreRepository binaryDataStoreRepository;

    private final transient BinaryDataStoreMapper binaryDataStoreMapper;

    public Page<BinaryDataStoreDTO> findByCriteria(BinaryDataStoreCriteria criteria, Pageable pageable) {
        Specification<BinaryDataStore> specification = createSpecification(criteria);
        return binaryDataStoreRepository.findAll(specification, pageable).map(binaryDataStoreMapper::toDto);
    }

    private Specification<BinaryDataStore> createSpecification(BinaryDataStoreCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "key", criteria.getKey(), true);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
