package com.czareg.validator;

public abstract class ValidationResult {
    private boolean wasSuccessful;
    private String result;

    public ValidationResult(boolean wasSuccessful, String result) {
        this.wasSuccessful = wasSuccessful;
        this.result = result;
    }

    public boolean isSuccessful() {
        return wasSuccessful;
    }

    public String getResult() {
        return result;
    }
}
