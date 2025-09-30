/**
 * InstantFilter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class InstantFilter extends Filter<Instant> {
    private Instant greaterThan;
    private Instant lessThan;
    private Instant greaterThanOrEqual;
    private Instant lessThanOrEqual;

    private List<Instant> in;
    private List<Instant> notIn;
}
