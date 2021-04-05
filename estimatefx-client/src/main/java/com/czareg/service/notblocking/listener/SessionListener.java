package com.czareg.service.notblocking.listener;

import com.czareg.context.Context;
import com.czareg.dto.SessionDTO;
import com.czareg.notifications.NotificationMessageBuilder;
import com.google.gson.Gson;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.notifications.EstimateFxNotification.showErrorNotificationFromCustomThread;

public class SessionListener extends Listener {
    private static final Logger LOG = LogManager.getLogger(SessionListener.class);

    private Gson gson;
    private Context context;
    private SessionDTO sessionDTO;

    public SessionListener(Context context) {
        super(LOG);
        gson = new Gson();
        this.context = context;
    }

    @Override
    protected void onEvent(String jsonObject) {
        String userName = context.getUserName();
        if (userName == null) {
            LOG.error("Username stored in context is null");
            return;
        }
        sessionDTO = gson.fromJson(jsonObject, SessionDTO.class);
        boolean hasUser = hasUser(userName, sessionDTO);
        if (!hasUser) {
            String developerMessage = "You are no longer in session";
            LOG.info(developerMessage);
            NotificationMessageBuilder notificationMessageBuilder = new NotificationMessageBuilder();
            notificationMessageBuilder.developerMessage(developerMessage);
            notificationMessageBuilder.remediation("Try leaving and rejoining session.");
            showErrorNotificationFromCustomThread(notificationMessageBuilder.build());

            Platform.runLater(() -> {
                InactiveVoteViewUpdater inactiveVoteViewUpdater = new InactiveVoteViewUpdater(context, sessionDTO);
                inactiveVoteViewUpdater.update();
            });
            return;
        }

        Platform.runLater(() -> {
            ActiveVoteViewUpdater activeVoteViewUpdater = new ActiveVoteViewUpdater(context, sessionDTO);
            activeVoteViewUpdater.update();
        });
    }

    @Override
    protected void onFailure(Throwable throwable) {
        String developerMessage = "Failed to get current session information from backend.";
        LOG.error(developerMessage, throwable);
        NotificationMessageBuilder notificationMessageBuilder = new NotificationMessageBuilder();
        notificationMessageBuilder.developerMessage(developerMessage);
        notificationMessageBuilder.exceptionMessage(throwable);
        notificationMessageBuilder.remediation("There is a high probability that session polling mechanism is broken now. \nIn that case leave the session and try to rejoin.");
        showErrorNotificationFromCustomThread(notificationMessageBuilder.build());
    }

    private boolean hasUser(String userName, SessionDTO sessionDTO) {
        return sessionDTO.getUsers().stream()
                .anyMatch((userDTO -> userDTO.getUserName().equals(userName)));
    }
}