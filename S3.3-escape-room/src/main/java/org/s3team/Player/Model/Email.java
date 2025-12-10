package org.s3team.Player.Model;

import org.s3team.Exceptions.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Email(String email) {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public Email(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email can't be empty");
        }
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ValidationException("Invalid email format");
        }
        this.email = email.trim().toLowerCase();
    }


    @Override
    public String email() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int hashCode() {
        return 0;
    }
}


