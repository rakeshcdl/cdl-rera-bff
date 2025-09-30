/**
 * IntegerFilter.java
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
public class IntegerFilter extends Filter<Integer> {
    private Integer greaterThan;
    private Integer lessThan;
    private Integer greaterThanOrEqual;
    private Integer lessThanOrEqual;

    private List<Integer> in;
    private List<Integer> notIn;
}
