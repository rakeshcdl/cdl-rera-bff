/**
 * BankAccountCriteriaService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 04/08/25
 */


package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.BankAccountCriteria;
import com.cdl.escrow.dto.BankAccountDTO;
import com.cdl.escrow.entity.BankAccount;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BankAccountMapper;
import com.cdl.escrow.repository.BankAccountRepository;
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
public class BankAccountCriteriaService extends BaseSpecificationBuilder<BankAccount> implements Serializable {

    private final transient BankAccountRepository bankAccountRepository;

    private final transient BankAccountMapper bankAccountMapper;

    public Page<BankAccountDTO> findByCriteria(BankAccountCriteria criteria, Pageable pageable) {
        Specification<BankAccount> specification = createSpecification(criteria);
        return bankAccountRepository.findAll(specification, pageable).map(bankAccountMapper::toDto);
    }

    public Specification<BankAccount> createSpecification(BankAccountCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());



            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
