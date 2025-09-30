/**
 * BooleanFilter.java
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
public class BooleanFilter extends Filter<Boolean> {

    private Boolean equals;
    private List<Boolean> in;
    private List<Boolean> notIn;
}
