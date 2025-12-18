package org.s3team.common.valueobject;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public Price {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    @Override
    public String toString(){
        return value.toPlainString();
    }
}
