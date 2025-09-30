/**
 * AppLanguageCodeCriteria.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AppLanguageCodeCriteria implements Serializable {
    private LongFilter id;

    private StringFilter languageCode;

    private StringFilter nameKey;

    private StringFilter nameNativeValue;

    private BooleanFilter enabled ;

    private BooleanFilter isRtl ;

   // private LongFilter languageTranslationId;

    private Boolean distinct;
}
