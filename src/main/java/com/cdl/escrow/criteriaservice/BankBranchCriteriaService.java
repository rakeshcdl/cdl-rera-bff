package com.cdl.escrow.criteriaservice;



import com.cdl.escrow.criteria.BankBranchCriteria;
import com.cdl.escrow.dto.BankBranchDTO;
import com.cdl.escrow.entity.BankBranch;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.BankBranchMapper;
import com.cdl.escrow.repository.BankBranchRepository;
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
public class BankBranchCriteriaService extends BaseSpecificationBuilder<BankBranch> implements Serializable {

    private final transient BankBranchRepository bankBranchRepository;

    private final transient BankBranchMapper bankBranchMapper;

    public Page<BankBranchDTO> findByCriteria(BankBranchCriteria criteria, Pageable pageable) {
        Specification<BankBranch> specification = createSpecification(criteria);
        return bankBranchRepository.findAll(specification, pageable).map(bankBranchMapper::toDto);
    }

    private Specification<BankBranch> createSpecification(BankBranchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria!=null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());
                addStringFilter(cb, root, predicates, "bankBranchName", criteria.getBankBranchName(), true);
                addStringFilter(cb, root, predicates, "bankBranchAddress", criteria.getBankBranchAddress(), true);
                addStringFilter(cb, root, predicates, "bankBranchCode", criteria.getBankBranchCode(), true);
                addStringFilter(cb, root, predicates, "bankBranchIfscCode", criteria.getBankBranchIfscCode(), true);
                addStringFilter(cb, root, predicates, "bankBranchSwiftCode", criteria.getBankBranchSwiftCode(), true);
                addStringFilter(cb, root, predicates, "bankBranchRoutingCode", criteria.getBankBranchRoutingCode(), true);
                addStringFilter(cb, root, predicates, "bankBranchTtcCode", criteria.getBankBranchTtcCode(), true);
                addLongFilter(cb, root, predicates, "financialInstitutionId", criteria.getFinancialInstitutionId());
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
