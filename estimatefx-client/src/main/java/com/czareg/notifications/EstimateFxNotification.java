package com.czareg.notifications;

import org.controlsfx.control.Notifications;

import static javafx.geometry.Pos.BOTTOM_CENTER;

public final class EstimateFxNotification {
    private EstimateFxNotification() {
    }

    public static void showErrorNotification(String failure) {
        String errorMessage = String.format("%s\n\nSee logs for more information.", failure);

        Notifications.create()
                .title("EstimateFx Error")
                .text(errorMessage)
                .position(BOTTOM_CENTER)
                .threshold(3, Notifications.create().title("Stop clicking so many times!"))
                .showError();
    }
}
