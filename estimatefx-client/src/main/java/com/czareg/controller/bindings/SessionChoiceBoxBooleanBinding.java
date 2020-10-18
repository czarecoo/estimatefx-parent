package com.czareg.controller.bindings;

import com.czareg.model.SessionIdentifier;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.ChoiceBox;

public class SessionChoiceBoxBooleanBinding extends BooleanBinding {
    private ChoiceBox<SessionIdentifier> sessionIdentifierChoiceBox;

    public SessionChoiceBoxBooleanBinding(ChoiceBox<SessionIdentifier> sessionIdentifierChoiceBox) {
        this.sessionIdentifierChoiceBox = sessionIdentifierChoiceBox;
        bind(sessionIdentifierChoiceBox.getSelectionModel().selectedItemProperty());
    }

    @Override
    protected boolean computeValue() {
        SessionIdentifier selectedItem = sessionIdentifierChoiceBox.getSelectionModel().getSelectedItem();
        return selectedItem == null;
    }
}