package com.czareg.validator;

public class ValidationSuccessful extends ValidationResult {
    public ValidationSuccessful(String result) {
        super(true, result);
    }
}