package com.czareg.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String userName) {
        if (userName == null) {
            return new ValidationFailed("User name cannot be null");
        }
        if (userName.isEmpty()) {
            return new ValidationFailed("User name cannot be empty");
        }
        String trimmedUserName = userName.trim();
        if (!userName.equals(trimmedUserName)) {
            return new ValidationFailed("User name cannot have leading and trailing whitespace");
        }
        if (userName.length() > 20) {
            return new ValidationFailed("User name too long. Only 20 characters are allowed.");
        }
        Pattern p = Pattern.compile("[^AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtQqUuWwVvXxYyZzŹźŻż0-9 ]");
        Matcher m = p.matcher(trimmedUserName);
        boolean containsSpecialCharacters = m.find();
        if (containsSpecialCharacters) {
            return new ValidationFailed("User name cannot contain special characters. Only letters, numbers and space is allowed");
        }
        return new ValidationSuccessful("User name is ok");
    }
}
