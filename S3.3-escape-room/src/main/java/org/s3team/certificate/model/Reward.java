package org.s3team.certificate.model;

public record Reward(String value) {

    public static final Reward NONE = new Reward("");

    public Reward {
        if (value == null) {
            throw new IllegalArgumentException("Reward value cannot be null");
        }

        value = value.strip();
        if(value.length() > 200) {
            throw new IllegalArgumentException("Reward cannot be superior to 200 characters");
        }
    }

    public boolean hasReward(){
        return !value.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }
}
