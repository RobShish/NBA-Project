package controller;


import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;
import model.Players;
import model.Team;
import model.Teams;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageTeamController {
    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> playerNameColumn;

    @FXML
    private TableColumn<Player, Double> playerCreditColumn;

    @FXML
    private TableColumn<Player, Integer> playerAgeColumn;

    @FXML
    private TableColumn<Player, Integer> playerNoColumn;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextField teamName;
    private Team team;
    private Teams teams;
    @FXML
    private TextField teamNameTextField;

    public void initialize() {
        teams = ExploreTeamsController.getTeamsInstance();

        teamNameTextField.setEditable(true);

        playerTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = mouseEvent.getPickResult().getIntersectedNode();

                // clear selection when click off
                if (node instanceof Text || (node instanceof TableCell && ((TableCell) node).getText() != null)) {
                    return;
                }

                playerTable.getSelectionModel().clearSelection();
            }
        });

        playerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null && oldSelection != null) {
                // deslect
                updateButton.setOpacity(0.5);
                deleteButton.setOpacity(0.5);
                addButton.setOpacity(1.0);
                addButton.setDisable(false);
            } else if (newSelection != null && oldSelection == null) {
                //select
                updateButton.setOpacity(1.0);
                deleteButton.setOpacity(1.0);
                addButton.setOpacity(0.5);
                addButton.setDisable(true);
            }
        });
    }




    public void setTeam(Team team) {
        this.team = team;
        teamNameTextField.setText(team.getName());
        populateTable();
    }
    private void populateTable() {
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        playerCreditColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerCreditProperty().asObject());
        playerAgeColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerAgeProperty().asObject());
        playerNoColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerNoProperty().asObject());

        playerTable.setItems(team.getCurrentPlayers());
    }

    @FXML
    private void handleMouseClick() {
        if (playerTable.getSelectionModel().getSelectedItem() != null) {
            updateButton.setOpacity(1.0);
            deleteButton.setOpacity(1.0);
        }
    }


    @FXML
    public void deletePlayer() {
        Player selectedPlayer = playerTable.getSelectionModel().getSelectedItem();

        if (selectedPlayer != null) {
            team.getPlayers().removePlayer(selectedPlayer);
            playerTable.getItems().remove(selectedPlayer);
        }
    }

    @FXML
    public void addPlayer(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayerUpdateView.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            PlayerUpdateController controller = loader.getController();
            controller.setTeam(team);

            stage.setTitle("Add New Player");
            stage.getIcons().add(new Image("/view/nba.png"));

            stage.show();
        } catch (IOException e) {
            Logger.getLogger(ManageTeamController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void updatePlayer(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayerUpdateView.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            PlayerUpdateController controller = loader.getController();
            Player selectedPlayer = playerTable.getSelectionModel().getSelectedItem();
            controller.setPlayer(selectedPlayer);

            stage.setTitle("Updating Player: " + selectedPlayer.getName());
            stage.getIcons().add(new Image("/view/nba.png"));

            stage.show();
        } catch (IOException e) {
            Logger.getLogger(ManageTeamController.class.getName()).log(Level.SEVERE, null, e);
        }
    }




    @FXML
    public void close(ActionEvent event) {

        team.setName(teamNameTextField.getText());
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
