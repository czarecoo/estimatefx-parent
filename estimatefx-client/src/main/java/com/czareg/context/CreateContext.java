package com.czareg.context;

import javafx.scene.control.CheckBox;

public class CreateContext {
    private final CheckBox allowStealingCreatorCheckBox;
    private final CheckBox passCreatorWhenLeavingCheckBox;

    public CreateContext(CheckBox allowStealingCreatorCheckBox, CheckBox passCreatorWhenLeavingCheckBox) {
        this.allowStealingCreatorCheckBox = allowStealingCreatorCheckBox;
        this.passCreatorWhenLeavingCheckBox = passCreatorWhenLeavingCheckBox;
    }

    public boolean shouldAllowStealingCreator() {
        return allowStealingCreatorCheckBox.isSelected();
    }

    public boolean shouldPassCreatorWhenLeaving() {
        return passCreatorWhenLeavingCheckBox.isSelected();
    }
}