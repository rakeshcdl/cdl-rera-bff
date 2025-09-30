package com.cdl.escrow.criteriaservice;

import com.cdl.escrow.criteria.FinancialInstitutionCriteria;
import com.cdl.escrow.dto.FinancialInstitutionDTO;
import com.cdl.escrow.entity.FinancialInstitution;
import com.cdl.escrow.filter.BaseSpecificationBuilder;
import com.cdl.escrow.mapper.FinancialInstitutionMapper;
import com.cdl.escrow.repository.FinancialInstitutionRepository;
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
public class FinancialInstitutionCriteriaService extends BaseSpecificationBuilder<FinancialInstitution> implements Serializable {

    private final transient FinancialInstitutionRepository financialInstitutionRepository;

    private final transient FinancialInstitutionMapper financialInstitutionMapper;

    public Page<FinancialInstitutionDTO> findByCriteria(FinancialInstitutionCriteria criteria, Pageable pageable) {
        Specification<FinancialInstitution> specification = createSpecification(criteria);
        return financialInstitutionRepository.findAll(specification, pageable).map(financialInstitutionMapper::toDto);
    }

    public Specification<FinancialInstitution> createSpecification(FinancialInstitutionCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                addLongFilter(cb, root, predicates, "id", criteria.getId());

                addStringFilter(cb,root,predicates,"fiName", criteria.getFiName(), true);
                addStringFilter(cb,root,predicates,"fiAddress", criteria.getFiAddress(), true);
                addStringFilter(cb,root,predicates,"fiContactNumber", criteria.getFiContactNumber(), true);
                addStringFilter(cb,root,predicates,"fiCode", criteria.getFiCode(), true);
                addStringFilter(cb,root,predicates,"fiAccountNumber", criteria.getFiAccountNumber(), true);
                addDoubleFilter(cb,root,predicates,"fiAccountBalance", criteria.getFiAccountBalance());
                addStringFilter(cb,root,predicates,"fiIbanNo", criteria.getFiIbanNo(), true);
                addStringFilter(cb,root,predicates,"fiAccountTitle", criteria.getFiAccountTitle(), true);
                addStringFilter(cb,root,predicates,"fiSwiftCode", criteria.getFiSwiftCode(), true);
                addStringFilter(cb,root,predicates,"fiRoutingCode", criteria.getFiRoutingCode(), true);
                addStringFilter(cb,root,predicates,"fiSchemeType", criteria.getFiSchemeType(), true);
                addZonedDateTimeFilter(cb,root,predicates, "fiOpeningDate", criteria.getFiOpeningDate());

            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
