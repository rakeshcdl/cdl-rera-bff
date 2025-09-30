package com.cdl.escrow.criteriaservice;


import com.cdl.escrow.criteria.PrimaryBankAccountCriteria;
import com.cdl.escrow.dto.PrimaryBankAccountDTO;
import com.cdl.escrow.entity.PrimaryBankAccount;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.PrimaryBankAccountMapper;
import com.cdl.escrow.repository.PrimaryBankAccountRepository;
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
public class PrimaryBankAccountCriteriaService   extends BaseSpecificationBuilder<PrimaryBankAccount>  implements Serializable {

    private final transient PrimaryBankAccountRepository primaryBankAccountRepository;

    private final transient PrimaryBankAccountMapper primaryBankAccountMapper;

    public Page<PrimaryBankAccountDTO> findByCriteria(PrimaryBankAccountCriteria criteria, Pageable pageable) {
        Specification<PrimaryBankAccount> specification = createSpecification(criteria);
        return primaryBankAccountRepository.findAll(specification, pageable).map(primaryBankAccountMapper::toDto);
    }

    private Specification<PrimaryBankAccount> createSpecification(PrimaryBankAccountCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "pcAccountNumber", criteria.getPcAccountNumber(), true);
                addDoubleFilter(cb, root, predicates, "pbBalance", criteria.getPbBalance());
                addStringFilter(cb, root, predicates, "pbLabelId", criteria.getPbLabelId(), true);
                addStringFilter(cb, root, predicates, "pbTypeId", criteria.getPbTypeId(), true);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
