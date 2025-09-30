/**
 * AppConfigTranslationCriteriaService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 18/07/25
 */


package com.cdl.escrow.criteriaservice;


//@Service
//@RequiredArgsConstructor
public class AppConfigTranslationCriteriaService {

   /* private final AppConfigTranslationRepository appConfigTranslationRepository;

    private final AppLanguageCodeMapper appLanguageCodeMapper;

    public Page<AppLanguageTranslationDTO> findByCriteria(AppLanguageCodeCriteria criteria, Pageable pageable) {
        Specification<AppLanguageCode> specification = createSpecification(criteria);
        return appConfigTranslationRepository.findAll(specification, pageable).map(appLanguageCodeMapper::toDto);
    }*/

  /*  private Specification<AppLanguageCode> createSpecification(AppLanguageCodeCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getId() != null && criteria.getId().getEquals() != null && criteria.getId().getIn() !=null && criteria.getId().getNotIn() !=null ) {
                predicates.add(cb.equal(root.get("id").as(Long.class), criteria.getId().getEquals()));

            }

            if (criteria.getLanguageCode() != null && criteria.getLanguageCode().getContains() != null) {
                predicates.add(cb.like(cb.lower(root.get("languageCode").as(String.class)), "%" + criteria.getLanguageCode().getContains().toLowerCase() + "%"));
            }

            if (criteria.getNameKey() != null && criteria.getNameKey().getContains() != null) {
                predicates.add(cb.like(cb.lower(root.get("nameKey").as(String.class)), "%" + criteria.getNameKey().getContains().toLowerCase() + "%"));
            }

            if (criteria.getNameNativeValue() != null && criteria.getNameNativeValue() != null ) {
                predicates.add(cb.like(cb.lower(root.get("nameNativeValue").as(String.class)), "%" + criteria.getNameNativeValue().getContains().toLowerCase() + "%"));
            }


           *//* if (criteria.getLanguageTranslationId() != null && criteria.getLanguageTranslationId().getEquals() != null) {
                predicates.add(cb.equal(root.get("languageTranslationId").as(Long.class), criteria.getLanguageTranslationId().getEquals()));
            }*//*

            if (criteria.getEnabled() != null && criteria.getEnabled().getEquals() != null && criteria.getEnabled().getIn()!=null && criteria.getEnabled().getNotIn() !=null) {
                predicates.add(cb.equal(root.get("enabled").as(Boolean.class), criteria.getEnabled().getEquals()));
            }

            if (criteria.getIsRtl() != null && criteria.getIsRtl().getEquals() != null && criteria.getIsRtl().getNotIn()!=null && criteria.getIsRtl().getIn()!=null  ) {
                predicates.add(cb.equal(root.get("isRtl").as(Boolean.class), criteria.getIsRtl().getEquals()));
            }


            if (criteria instanceof DistinctSupport && ((DistinctSupport) criteria).getDistinct() != null) {
                query.distinct(((DistinctSupport) criteria).getDistinct());
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }*/
}
