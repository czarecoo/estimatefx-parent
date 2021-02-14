package com.czareg.notifications;

import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.controlsfx.tools.Utils;

import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.TOP_CENTER;

public final class EstimateFxNotification {
    private static final Logger LOG = LogManager.getLogger(EstimateFxNotification.class);

    private EstimateFxNotification() {
    }

    public static void showErrorNotificationFromUiThread(String message) {
        doShowErrorNotification(message);
    }

    public static void showErrorNotificationFromCustomThread(String message) {
        runLater(() -> doShowErrorNotification(message));
    }

    private static void doShowErrorNotification(String message) {
        if (Utils.getWindow(null) == null) {
            LOG.info("Skipping notification because window could not be found");
            return;
        }

        Notifications.create()
                .title("EstimateFx Error")
                .text(message)
                .position(TOP_CENTER)
                .hideAfter(Duration.seconds(15))
                .threshold(3, Notifications.create().title("Too many errors in short time."))
                .showError();
    }
}