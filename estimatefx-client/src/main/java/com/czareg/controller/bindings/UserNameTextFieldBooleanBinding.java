package com.czareg.controller.bindings;

import com.czareg.validator.UserNameValidator;
import com.czareg.validator.ValidationResult;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.TextField;

public class UserNameTextFieldBooleanBinding extends BooleanBinding {
    private TextField textField;

    public UserNameTextFieldBooleanBinding(TextField textField) {
        this.textField = textField;
        super.bind(textField.textProperty());
    }

    @Override
    protected boolean computeValue() {
        ValidationResult validationResult = new UserNameValidator().validate(textField.getText());
        return !validationResult.isSuccessful();
    }
}
