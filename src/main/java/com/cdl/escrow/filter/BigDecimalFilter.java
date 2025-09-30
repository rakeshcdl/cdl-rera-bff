/**
 * BigDecimalFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BigDecimalFilter extends Filter<BigDecimal> {
    private BigDecimal greaterThan;
    private BigDecimal lessThan;
    private BigDecimal greaterThanOrEqual;
    private BigDecimal lessThanOrEqual;

    private List<BigDecimal> in;
    private List<BigDecimal> notIn;
}
