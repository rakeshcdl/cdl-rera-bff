/**
 * FloatFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FloatFilter extends Filter<Float> {
    private Float equals;
    private Float notEquals;
    private List<Float> in;
    private List<Float> notIn;
    private Float greaterThan;
    private Float lessThan;
    private Float greaterThanOrEqual;
    private Float lessThanOrEqual;
    private Boolean specified;
}
