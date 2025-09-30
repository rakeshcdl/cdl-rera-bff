/**
 * LocalDateFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocalDateFilter extends Filter<LocalDate> {
    private LocalDate greaterThan;
    private LocalDate lessThan;
    private LocalDate greaterThanOrEqual;
    private LocalDate lessThanOrEqual;

    private List<LocalDate> in;
    private List<LocalDate> notIn;
}
