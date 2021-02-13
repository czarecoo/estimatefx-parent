package com.czareg.notifications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.controlsfx.tools.Utils;

import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.BOTTOM_CENTER;

public final class EstimateFxNotification {
    private static final Logger LOG = LogManager.getLogger(EstimateFxNotification.class);

    private EstimateFxNotification() {
    }

    public static void showErrorNotificationFromUiThread(String failure) {
        doShowErrorNotification(failure);
    }

    public static void showErrorNotificationFromCustomThread(String failure) {
        runLater(() -> doShowErrorNotification(failure));
    }

    private static void doShowErrorNotification(String failure) {
        if (Utils.getWindow(null) == null) {
            LOG.info("Skipping notification because window could not be found");
            return;
        }
        String errorMessage = String.format("%s\n\nSee logs for more information.", failure);

        Notifications.create()
                .title("EstimateFx Error")
                .text(errorMessage)
                .position(BOTTOM_CENTER)
                .threshold(3, Notifications.create().title("Stop clicking so many times!"))
                .showError();
    }
}
