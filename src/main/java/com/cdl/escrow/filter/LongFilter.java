/**
 * LongFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LongFilter extends Filter<Long> {

    private Long equals;
    private List<Long> in;
    private List<Long> notIn;
    private Long greaterThan;
    private Long lessThan;
    private Long greaterThanOrEqual;
    private Long lessThanOrEqual;
}
