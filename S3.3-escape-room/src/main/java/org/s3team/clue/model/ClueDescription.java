package org.s3team.clue.model;

public record ClueDescription(String value) {

    public ClueDescription{
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Clue description cannot be null or blank");
        }

        if(value.length() > 200) {
            throw new IllegalArgumentException("Clue Description cannot be superior to 200 characters");
        }
    }

    @Override
    public String toString(){
        return value;
    }
}

