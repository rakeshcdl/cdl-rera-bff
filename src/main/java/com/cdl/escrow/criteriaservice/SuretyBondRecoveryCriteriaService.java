package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.SuretyBondRecoveryCriteria;
import com.cdl.escrow.dto.SuretyBondRecoveryDTO;
import com.cdl.escrow.entity.SuretyBondRecovery;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.SuretyBondRecoveryMapper;
import com.cdl.escrow.repository.SuretyBondRecoveryRepository;
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
public class SuretyBondRecoveryCriteriaService extends BaseSpecificationBuilder<SuretyBondRecovery> implements Serializable {

    private final transient SuretyBondRecoveryRepository suretyBondRecoveryRepository;

    private final transient SuretyBondRecoveryMapper suretyBondRecoveryMapper;

    public Page<SuretyBondRecoveryDTO> findByCriteria(SuretyBondRecoveryCriteria criteria, Pageable pageable) {
        Specification<SuretyBondRecovery> specification = createSpecification(criteria);
        return suretyBondRecoveryRepository.findAll(specification, pageable).map(suretyBondRecoveryMapper::toDto);
    }

    private Specification<SuretyBondRecovery> createSpecification(SuretyBondRecoveryCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addDoubleFilter(cb, root, predicates, "suretyBondRecoveryReductionAmount", criteria.getSuretyBondRecoveryReductionAmount());
                addDoubleFilter(cb, root, predicates, "suretyBondRecoveryBalanceAmount", criteria.getSuretyBondRecoveryBalanceAmount());
                addLongFilter(cb, root, predicates, "suretyBondId", criteria.getSuretyBondId());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
