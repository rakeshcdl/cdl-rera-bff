/**
 * Filter.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.filter;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient T equals;
    private transient T notEquals;
    private transient List<T> in;
    private  transient List<T> notIn;
    private  transient T greaterThan;
    private  transient T lessThan;
    private transient T greaterThanOrEqual;
    private  transient T lessThanOrEqual;

    public Filter(Filter<T> filter) {
        this.equals = filter.equals;
        this.notEquals = filter.notEquals;
        this.in = filter.in != null ? new ArrayList<>(filter.in) : null;
        this.notIn = filter.notIn != null ? new ArrayList<>(filter.notIn) : null;
        this.greaterThan = filter.greaterThan;
        this.lessThan = filter.lessThan;
        this.greaterThanOrEqual = filter.greaterThanOrEqual;
        this.lessThanOrEqual = filter.lessThanOrEqual;
    }

    public Filter<T> copy() {
        return new Filter<>(this);
    }
}
