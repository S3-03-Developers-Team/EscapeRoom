package org.s3team.common.valueobject;

public record Id<T>(int value) {
    public Id {
        if (value <= 0) {
            throw new IllegalArgumentException("Id must be positive");
        }
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}

