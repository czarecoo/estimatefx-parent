package com.czareg.validator;

class ValidationSuccessful extends ValidationResult {
    ValidationSuccessful(String result) {
        super(true, result);
    }
}