package com.czareg.controller;

import com.czareg.Vote;
import com.czareg.context.Context;
import com.czareg.stage.ContextAware;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoteController implements ContextAware {
    private static final Logger LOG = LogManager.getLogger(VoteController.class);
    private Context context;

    @FXML
    private TableView<Vote> voteTableView;

    @FXML
    private TableColumn<Vote, String> nameColumn;

    @FXML
    private TableColumn<Vote, String> voteColumn;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        voteColumn.setCellValueFactory(new PropertyValueFactory<>("vote"));
    }

    @FXML
    private void handleVoteButtonClicked(ActionEvent event) {
        if (!(event.getSource() instanceof Button)) {
            throw new IllegalStateException("Handle was assigned to wrong UI element");
        }
        String name = context.getName();
        String voteValue = ((Button) event.getSource()).getText();
        Vote vote = new Vote(name, voteValue);

        voteTableView.getItems().add(vote);
        LOG.info("Voted {}", vote);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}