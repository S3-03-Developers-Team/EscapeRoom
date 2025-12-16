package org.s3team.common.valueobject;

public record Name(String value) {
    public Name {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
    }

    @Override
    public String toString(){
        return value;
    }
}