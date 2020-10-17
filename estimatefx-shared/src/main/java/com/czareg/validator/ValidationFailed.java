package com.czareg.validator;

public class ValidationFailed extends ValidationResult {
    public ValidationFailed(String result) {
        super(false, result);
    }
}