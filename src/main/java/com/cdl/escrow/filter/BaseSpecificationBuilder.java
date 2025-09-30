/**
 * BaseSpecificationBuilder.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 21/07/25
 */


package com.cdl.escrow.filter;

import com.cdl.escrow.entity.WorkflowRequest;
import jakarta.persistence.criteria.*;

import java.util.List;

public abstract class BaseSpecificationBuilder<T> {

    protected void addLongFilter(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, String field, LongFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) predicates.add(cb.equal(root.get(field), filter.getEquals()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            if (filter.getIn() != null && !filter.getIn().isEmpty()) predicates.add(root.get(field).in(filter.getIn()));
            if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) predicates.add(cb.not(root.get(field).in(filter.getNotIn())));
            if (filter.getGreaterThan() != null) predicates.add(cb.greaterThan(root.get(field), filter.getGreaterThan()));
            if (filter.getLessThan() != null) predicates.add(cb.lessThan(root.get(field), filter.getLessThan()));
            if (filter.getGreaterThanOrEqual() != null) predicates.add(cb.greaterThanOrEqualTo(root.get(field), filter.getGreaterThanOrEqual()));
            if (filter.getLessThanOrEqual() != null) predicates.add(cb.lessThanOrEqualTo(root.get(field), filter.getLessThanOrEqual()));
        }
    }

    protected void addIntegerFilter(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, String field, IntegerFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) predicates.add(cb.equal(root.get(field), filter.getEquals()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            if (filter.getIn() != null && !filter.getIn().isEmpty()) predicates.add(root.get(field).in(filter.getIn()));
            if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) predicates.add(cb.not(root.get(field).in(filter.getNotIn())));
            if (filter.getGreaterThan() != null) predicates.add(cb.greaterThan(root.get(field), filter.getGreaterThan()));
            if (filter.getLessThan() != null) predicates.add(cb.lessThan(root.get(field), filter.getLessThan()));
            if (filter.getGreaterThanOrEqual() != null) predicates.add(cb.greaterThanOrEqualTo(root.get(field), filter.getGreaterThanOrEqual()));
            if (filter.getLessThanOrEqual() != null) predicates.add(cb.lessThanOrEqualTo(root.get(field), filter.getLessThanOrEqual()));
        }
    }

    protected void addDoubleFilter(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, String field, DoubleFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) predicates.add(cb.equal(root.get(field), filter.getEquals()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            if (filter.getIn() != null && !filter.getIn().isEmpty()) predicates.add(root.get(field).in(filter.getIn()));
            if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) predicates.add(cb.not(root.get(field).in(filter.getNotIn())));
            if (filter.getGreaterThan() != null) predicates.add(cb.greaterThan(root.get(field), filter.getGreaterThan()));
            if (filter.getLessThan() != null) predicates.add(cb.lessThan(root.get(field), filter.getLessThan()));
            if (filter.getGreaterThanOrEqual() != null) predicates.add(cb.greaterThanOrEqualTo(root.get(field), filter.getGreaterThanOrEqual()));
            if (filter.getLessThanOrEqual() != null) predicates.add(cb.lessThanOrEqualTo(root.get(field), filter.getLessThanOrEqual()));
        }
    }

    protected void addZonedDateTimeFilter(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, String field, ZonedDateTimeFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) predicates.add(cb.equal(root.get(field), filter.getEquals()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            if (filter.getGreaterThan() != null) predicates.add(cb.greaterThan(root.get(field), filter.getGreaterThan()));
            if (filter.getLessThan() != null) predicates.add(cb.lessThan(root.get(field), filter.getLessThan()));
            if (filter.getGreaterThanOrEqual() != null) predicates.add(cb.greaterThanOrEqualTo(root.get(field), filter.getGreaterThanOrEqual()));
            if (filter.getLessThanOrEqual() != null) predicates.add(cb.lessThanOrEqualTo(root.get(field), filter.getLessThanOrEqual()));
        }
    }

    protected void addInstantFilter(CriteriaBuilder cb, Root<WorkflowRequest> root, List<Predicate> predicates, String field, LongFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) predicates.add(cb.equal(root.get(field), filter.getEquals()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            if (filter.getGreaterThan() != null) predicates.add(cb.greaterThan(root.get(field), filter.getGreaterThan()));
            if (filter.getLessThan() != null) predicates.add(cb.lessThan(root.get(field), filter.getLessThan()));
            if (filter.getGreaterThanOrEqual() != null) predicates.add(cb.greaterThanOrEqualTo(root.get(field), filter.getGreaterThanOrEqual()));
            if (filter.getLessThanOrEqual() != null) predicates.add(cb.lessThanOrEqualTo(root.get(field), filter.getLessThanOrEqual()));
        }
    }

    protected void addLocalDateTimeFilter(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, String field, LocalDateTimeFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) predicates.add(cb.equal(root.get(field), filter.getEquals()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            if (filter.getGreaterThan() != null) predicates.add(cb.greaterThan(root.get(field), filter.getGreaterThan()));
            if (filter.getLessThan() != null) predicates.add(cb.lessThan(root.get(field), filter.getLessThan()));
            if (filter.getGreaterThanOrEqual() != null) predicates.add(cb.greaterThanOrEqualTo(root.get(field), filter.getGreaterThanOrEqual()));
            if (filter.getLessThanOrEqual() != null) predicates.add(cb.lessThanOrEqualTo(root.get(field), filter.getLessThanOrEqual()));
        }
    }

    protected void addStringFilter(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, String field, StringFilter filter, boolean useContains) {
        if (filter != null) {
            if (useContains && filter.getContains() != null) {
                predicates.add(cb.like(cb.lower(root.get(field)), "%" + filter.getContains().toLowerCase() + "%"));
            }
            if (filter.getEquals() != null) predicates.add(cb.equal(cb.lower(root.get(field)), filter.getEquals().toLowerCase()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(cb.lower(root.get(field)), filter.getNotEquals().toLowerCase()));
            if (filter.getIn() != null && !filter.getIn().isEmpty()) predicates.add(root.get(field).in(filter.getIn()));
            if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) predicates.add(cb.not(root.get(field).in(filter.getNotIn())));
            if (filter.getDoesNotContain() != null) predicates.add(cb.notLike(cb.lower(root.get(field)), "%" + filter.getDoesNotContain().toLowerCase() + "%"));
            if (filter.getStartsWith() != null) {
                predicates.add(cb.like(cb.lower(root.get(field)), filter.getStartsWith().toLowerCase() + "%"));
            }
            if (filter.getEndsWith() != null) {
                predicates.add(cb.like(cb.lower(root.get(field)), "%" + filter.getEndsWith().toLowerCase()));
            }
            if (filter.getLike() != null) {
                // expects the caller to pass in a raw SQL LIKE pattern (e.g. "%tony%")
                predicates.add(cb.like(cb.lower(root.get(field)), filter.getLike().toLowerCase()));
            }
        }
    }

    protected void addBooleanFilter(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates, String field, BooleanFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) predicates.add(cb.equal(root.get(field), filter.getEquals()));
            if (filter.getNotEquals() != null) predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            if (filter.getIn() != null && !filter.getIn().isEmpty()) predicates.add(root.get(field).in(filter.getIn()));
            if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) predicates.add(cb.not(root.get(field).in(filter.getNotIn())));
        }
    }

    protected void addFloatFilter(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates, String field, FloatFilter filter) {
        if (filter != null) {
            if (filter.getEquals() != null) {
                predicates.add(cb.equal(root.get(field), filter.getEquals()));
            }
            if (filter.getNotEquals() != null) {
                predicates.add(cb.notEqual(root.get(field), filter.getNotEquals()));
            }
            if (filter.getIn() != null && !filter.getIn().isEmpty()) {
                predicates.add(root.get(field).in(filter.getIn()));
            }
            if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
                predicates.add(cb.not(root.get(field).in(filter.getNotIn())));
            }
            if (filter.getGreaterThan() != null) {
                predicates.add(cb.greaterThan(root.get(field), filter.getGreaterThan()));
            }
            if (filter.getLessThan() != null) {
                predicates.add(cb.lessThan(root.get(field), filter.getLessThan()));
            }
            if (filter.getGreaterThanOrEqual() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(field), filter.getGreaterThanOrEqual()));
            }
            if (filter.getLessThanOrEqual() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get(field), filter.getLessThanOrEqual()));
            }
            if (Boolean.TRUE.equals(filter.getSpecified())) {
                predicates.add(cb.isNotNull(root.get(field)));
            } else if (Boolean.FALSE.equals(filter.getSpecified())) {
                predicates.add(cb.isNull(root.get(field)));
            }
        }
    }

    protected static void addLongFilterForJoin(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates,
                                               String relation, String targetField, LongFilter filter) {
        if (filter != null) {
            Join<Object, Object> join = root.join(relation, JoinType.LEFT);
            if (filter.getEquals() != null) {
                predicates.add(cb.equal(join.get(targetField), filter.getEquals()));
            }
            if (filter.getIn() != null && !filter.getIn().isEmpty()) {
                predicates.add(join.get(targetField).in(filter.getIn()));
            }
            if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
                predicates.add(cb.not(join.get(targetField).in(filter.getNotIn())));
            }
            if (filter.getGreaterThan() != null) {
                predicates.add(cb.greaterThan(join.get(targetField), filter.getGreaterThan()));
            }
            if (filter.getLessThan() != null) {
                predicates.add(cb.lessThan(join.get(targetField), filter.getLessThan()));
            }
            if (filter.getGreaterThanOrEqual() != null) {
                predicates.add(cb.greaterThanOrEqualTo(join.get(targetField), filter.getGreaterThanOrEqual()));
            }
            if (filter.getLessThanOrEqual() != null) {
                predicates.add(cb.lessThanOrEqualTo(join.get(targetField), filter.getLessThanOrEqual()));
            }
        }
    }

    // Helper: apply LongFilter on a Join (child entity)
    protected static void addLongFilterOnJoin(CriteriaBuilder cb, Join<?, ?> join, List<Predicate> predicates,
                                              String targetField, LongFilter filter) {
        if (filter == null) return;
        Path<Long> path = join.get(targetField);
        if (filter.getEquals() != null) {
            predicates.add(cb.equal(path, filter.getEquals()));
        }
        if (filter.getIn() != null && !filter.getIn().isEmpty()) {
            predicates.add(path.in(filter.getIn()));
        }
        if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
            predicates.add(cb.not(path.in(filter.getNotIn())));
        }
        if (filter.getGreaterThan() != null) {
            predicates.add(cb.greaterThan(path, filter.getGreaterThan()));
        }
        if (filter.getLessThan() != null) {
            predicates.add(cb.lessThan(path, filter.getLessThan()));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            predicates.add(cb.greaterThanOrEqualTo(path, filter.getGreaterThanOrEqual()));
        }
        if (filter.getLessThanOrEqual() != null) {
            predicates.add(cb.lessThanOrEqualTo(path, filter.getLessThanOrEqual()));
        }
    }

    // 🔑 ManyToMany filter using EXISTS
    protected static <T> void addLongManyToManyFilterForJoin(
            CriteriaBuilder cb,
            Root<T> root,
            CriteriaQuery<?> query,
            List<Predicate> predicates,
            String relation,       // e.g. "buildPartners"
            String targetField,    // e.g. "id"
            LongFilter filter) {

        if (filter == null) return;

        // EQUALS
        if (filter.getEquals() != null) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<T> correlatedRoot = subquery.correlate(root);
            Join<?, ?> subJoin = correlatedRoot.join(relation, JoinType.INNER);

            subquery.select(subJoin.get(targetField))
                    .where(cb.equal(subJoin.get(targetField), filter.getEquals()));

            predicates.add(cb.exists(subquery));
        }

        // IN
        if (filter.getIn() != null && !filter.getIn().isEmpty()) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<T> correlatedRoot = subquery.correlate(root);
            Join<?, ?> subJoin = correlatedRoot.join(relation, JoinType.INNER);

            subquery.select(subJoin.get(targetField))
                    .where(subJoin.get(targetField).in(filter.getIn()));

            predicates.add(cb.exists(subquery));
        }

        // NOT IN
        if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<T> correlatedRoot = subquery.correlate(root);
            Join<?, ?> subJoin = correlatedRoot.join(relation, JoinType.INNER);

            subquery.select(subJoin.get(targetField))
                    .where(subJoin.get(targetField).in(filter.getNotIn()));

            predicates.add(cb.not(cb.exists(subquery)));
        }
    }



}

