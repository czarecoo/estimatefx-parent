package com.czareg.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserNameValidatorTest {
    private UserNameValidator userNameValidator;

    @BeforeEach
    void setUp() {
        userNameValidator = new UserNameValidator();
    }

    @Test
    void shouldReturnTrueForNameWithPolishLetter() {
        ValidationResult validationResult = userNameValidator.validate("łukasz");

        assertTrue(validationResult.isSuccessful());
    }

    @Test
    void shouldReturnTrueForNameWithSpaceBetweenWords() {
        ValidationResult validationResult = userNameValidator.validate("Adam Dom");

        assertTrue(validationResult.isSuccessful());
    }
}