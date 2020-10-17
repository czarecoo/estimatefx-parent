package com.czareg.validator;

public interface Validator<T> {
    ValidationResult validate(T t);
}
