package org.s3team.Player.Model;

import org.s3team.Exceptions.ValidationException;

import java.util.Objects;
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
        String emailToValidate = email.trim();
        Matcher matcher = pattern.matcher(emailToValidate);
        if (!matcher.matches()) {
            throw new ValidationException("Invalid email format");
        }
        this.email = email.toLowerCase();
    }
    public String value() {
        return email;
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}


