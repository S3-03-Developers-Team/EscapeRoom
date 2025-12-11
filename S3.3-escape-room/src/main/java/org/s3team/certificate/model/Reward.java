package org.s3team.certificate.model;

public record Reward(String value) {

    public Reward {
        if (value != null) {
            value = value.strip();
        }

        if(value != null && value.length() > 200) {
            throw new IllegalArgumentException("Reward cannot be superior to 200 characters");
        }
    }

    public boolean hasReward(){
        return value != null && !value.isEmpty();
    }

    @Override
    public String toString() {
        return value != null ? value : "";
    }
}
