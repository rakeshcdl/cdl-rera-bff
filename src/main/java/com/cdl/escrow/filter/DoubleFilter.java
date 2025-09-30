/**
 * DoubleFilter.java
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
public class DoubleFilter extends Filter<Double> {
    private Double greaterThan;
    private Double lessThan;
    private Double greaterThanOrEqual;
    private Double lessThanOrEqual;

    private List<Double> in;
    private List<Double> notIn;
}
