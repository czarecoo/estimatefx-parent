package com.czareg;

import com.czareg.context.Context;
import com.czareg.scene.SceneManager;
import com.czareg.stage.StagePreparer;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.czareg.scene.fxml.FxmlScene.JOIN;

public class Client extends Application {
    private static final Logger LOG = LogManager.getLogger(Client.class);
    private SceneManager sceneManager;
    private StagePreparer stagePreparer;
    private Context context;

    public static void main(String[] args) {
        launch(Client.class);
    }

    @Override
    public void start(Stage stage) throws ConfigurationException {
        context = new Context();
        context.setStage(stage);

        sceneManager = new SceneManager(context);
        sceneManager.setScene(JOIN);

        stagePreparer = new StagePreparer(stage);
        stagePreparer.prepare();

        LOG.info("Started UI");
        stage.show();
    }

    @Override
    public void stop() {
        context.getTaskFactory().createLeaveSessionTask().run();
    }
}