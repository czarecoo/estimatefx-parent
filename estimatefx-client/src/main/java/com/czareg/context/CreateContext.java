package com.czareg.context;

import javafx.scene.control.CheckBox;

public class CreateContext {
    private final CheckBox allowPassingCreatorCheckBox;
    private final CheckBox allowStealingCreatorCheckBox;
    private final CheckBox passCreatorWhenLeavingCheckBox;

    public CreateContext(CheckBox allowPassingCreatorCheckBox, CheckBox allowStealingCreatorCheckBox, CheckBox passCreatorWhenLeavingCheckBox) {

        this.allowPassingCreatorCheckBox = allowPassingCreatorCheckBox;
        this.allowStealingCreatorCheckBox = allowStealingCreatorCheckBox;
        this.passCreatorWhenLeavingCheckBox = passCreatorWhenLeavingCheckBox;
    }

    public boolean shouldAllowPassingCreator() {
        return allowPassingCreatorCheckBox.isSelected();
    }

    public boolean shouldAllowStealingCreator() {
        return allowPassingCreatorCheckBox.isSelected();
    }

    public boolean shouldPassCreatorWhenLeaving() {
        return allowPassingCreatorCheckBox.isSelected();
    }
}