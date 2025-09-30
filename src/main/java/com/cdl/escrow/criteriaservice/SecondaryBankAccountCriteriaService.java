package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.SecondaryBankAccountCriteria;
import com.cdl.escrow.dto.SecondaryBankAccountDTO;
import com.cdl.escrow.entity.SecondaryBankAccount;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.SecondaryBankAccountMapper;
import com.cdl.escrow.repository.SecondaryBankAccountRepository;
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
public class SecondaryBankAccountCriteriaService extends BaseSpecificationBuilder<SecondaryBankAccount> implements Serializable {

    private final transient SecondaryBankAccountRepository secondaryBankAccountRepository;

    private final transient SecondaryBankAccountMapper secondaryBankAccountMapper;

    public Page<SecondaryBankAccountDTO> findByCriteria(SecondaryBankAccountCriteria criteria, Pageable pageable) {
        Specification<SecondaryBankAccount> specification = createSpecification(criteria);
        return secondaryBankAccountRepository.findAll(specification, pageable).map(secondaryBankAccountMapper::toDto);
    }

    private Specification<SecondaryBankAccount> createSpecification(SecondaryBankAccountCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "sbAccountNumber", criteria.getSbAccountNumber(), true);
                addDoubleFilter(cb, root, predicates, "sbBalance", criteria.getSbBalance());
                addStringFilter(cb, root, predicates, "sbLabelId", criteria.getSbLabelId(), true);
                addStringFilter(cb, root, predicates, "sbTypeId", criteria.getSbTypeId(), true);
                addLongFilter(cb, root, predicates, "primaryBankAccountId", criteria.getPrimaryBankAccountId());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
