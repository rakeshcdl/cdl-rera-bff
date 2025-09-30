/**
 * EscrowCriteria.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 21/07/25
 */


package com.cdl.escrow.filter;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public interface EscrowCriteria {

    default <T> Specification<T> buildSpecification(LongFilter filter, String fieldName) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filter != null) {
                if (filter.getEquals() != null)
                    predicate = cb.and(predicate, cb.equal(root.get(fieldName), filter.getEquals()));
                if (filter.getIn() != null && !filter.getIn().isEmpty())
                    predicate = cb.and(predicate, root.get(fieldName).in(filter.getIn()));
                if (filter.getNotIn() != null && !filter.getNotIn().isEmpty())
                    predicate = cb.and(predicate, cb.not(root.get(fieldName).in(filter.getNotIn())));
                if (filter.getGreaterThan() != null)
                    predicate = cb.and(predicate, cb.greaterThan(root.get(fieldName), filter.getGreaterThan()));
                if (filter.getLessThan() != null)
                    predicate = cb.and(predicate, cb.lessThan(root.get(fieldName), filter.getLessThan()));
                if (filter.getGreaterThanOrEqual() != null)
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(fieldName), filter.getGreaterThanOrEqual()));
                if (filter.getLessThanOrEqual() != null)
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(fieldName), filter.getLessThanOrEqual()));
            }
            return predicate;
        };
    }

    default <T> Specification<T> buildSpecification(StringFilter filter, String fieldName) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filter != null) {
                if (filter.getEquals() != null)
                    predicate = cb.and(predicate, cb.equal(root.get(fieldName), filter.getEquals()));
                if (filter.getIn() != null && !filter.getIn().isEmpty())
                    predicate = cb.and(predicate, root.get(fieldName).in(filter.getIn()));
                if (filter.getNotIn() != null && !filter.getNotIn().isEmpty())
                    predicate = cb.and(predicate, cb.not(root.get(fieldName).in(filter.getNotIn())));
                if (filter.getContains() != null)
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get(fieldName)), "%%" + filter.getContains().toLowerCase() + "%%"));
                if (filter.getDoesNotContain() != null)
                    predicate = cb.and(predicate, cb.notLike(cb.lower(root.get(fieldName)), "%%" + filter.getDoesNotContain().toLowerCase() + "%%"));
            }
            return predicate;
        };
    }

    default <T> Specification<T> buildSpecification(BooleanFilter filter, String fieldName) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filter != null) {
                if (filter.getEquals() != null)
                    predicate = cb.and(predicate, cb.equal(root.get(fieldName), filter.getEquals()));

                if (filter.getIn() != null && !filter.getIn().isEmpty())
                    predicate = cb.and(predicate, root.get(fieldName).in(filter.getIn()));

                if (filter.getNotIn() != null && !filter.getNotIn().isEmpty())
                    predicate = cb.and(predicate, cb.not(root.get(fieldName).in(filter.getNotIn())));
            }
            return predicate;
        };
    }

}
