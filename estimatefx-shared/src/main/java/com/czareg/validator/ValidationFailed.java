package com.czareg.validator;

class ValidationFailed extends ValidationResult {
    ValidationFailed(String result) {
        super(false, result);
    }
}