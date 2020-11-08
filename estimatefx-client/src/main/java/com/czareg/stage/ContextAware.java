package com.czareg.stage;

import com.czareg.context.Context;

public interface ContextAware {
    /**
     * This method is called AFTER FXML stuff is already initialized.
     */
    void initialize(Context context);
}