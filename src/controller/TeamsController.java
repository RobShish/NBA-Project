package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Teams;
import model.Team;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamsController extends Controller<Teams> {
    @FXML
    private TableView<Team> teamsTable;

    @FXML
    private TableColumn<Team, String> teamNameColumn;

    @FXML
    private TableColumn<Team, Integer> numberOfPlayersColumn;

    @FXML
    private TableColumn<Team, Double> avgPlayerCreditColumn;

    @FXML
    private TableColumn<Team, Double> avgAgeColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button manageButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button closeButton;

    private Teams teams;
    private final BooleanProperty addButtonDisabled = new SimpleBooleanProperty();
    private final BooleanProperty manageButtonDisabled = new SimpleBooleanProperty();
    private final BooleanProperty deleteButtonDisabled = new SimpleBooleanProperty();


    public void initialize() {
        this.teams = new Teams();
        populateTeamList();
        populateTable();
        
        teamsTable.setPlaceholder(new Text("Players list is not loaded"));
        // bind disable
        addButton.disableProperty().bind(addButtonDisabled);
        manageButton.disableProperty().bind(manageButtonDisabled);
        deleteButton.disableProperty().bind(deleteButtonDisabled);

        // Initialize if row selected
        addButtonDisabled.set(teamsTable.getSelectionModel().getSelectedItem() != null);
        manageButtonDisabled.set(teamsTable.getSelectionModel().getSelectedItem() == null);
        deleteButtonDisabled.set(teamsTable.getSelectionModel().getSelectedItem() == null);

        teamsTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = mouseEvent.getPickResult().getIntersectedNode();
                // clear selection on clicking anywhere but on a filled row
                if (node instanceof Text || (node instanceof TableCell && ((TableCell) node).getText() != null)) {
                    return;
                }

                teamsTable.getSelectionModel().clearSelection();
            }
        });

        teamsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null && oldSelection != null) {
                // Item deselected
                manageButton.setOpacity(0.5);
                deleteButton.setOpacity(0.5);
                addButton.setOpacity(1.0);

                // Update button states
                addButtonDisabled.set(false);
                manageButtonDisabled.set(true);
                deleteButtonDisabled.set(true);
            } else if (newSelection != null && oldSelection == null) {
                // Item Selected
                manageButton.setOpacity(1.0);
                deleteButton.setOpacity(1.0);
                addButton.setOpacity(0.5);

                // Update button states
                addButtonDisabled.set(true);
                manageButtonDisabled.set(false);
                deleteButtonDisabled.set(false);
            }
        });
        
    }

    public Teams getTeams(){
        return this.model;
    }

    public void refreshTable() {
        populateTable();
    }

    private ObservableList<Team> teamList = FXCollections.observableArrayList();

    private void populateTeamList(){
        teamList.addAll(teams.currentTeams());
    }
    public void addTeam(){
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTeam.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            AddTeamController addTeamController = loader.getController();
            addTeamController.setStage(stage);
            addTeamController.setTeams(teams);
            addTeamController.setTeamsController(this);
            addTeamController.setTeamList(teamList);

            stage.setTitle("Add Team");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void setTeams(Teams teams) {
        this.teams = teams;
        populateTable();
    }

    private void populateTable() {
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("name"));
        numberOfPlayersColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayers().playerNumberProperty().asObject());
        avgPlayerCreditColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayers().avgCreditProperty().asObject());
        avgAgeColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayers().avgAgeProperty().asObject());

        teamsTable.setItems(teams.currentTeams());
    }


    @FXML
    private void handleMouseClick() {
        if (teamsTable.getSelectionModel().getSelectedItem() != null) {
            manageButton.setOpacity(1.0);
            deleteButton.setOpacity(1.0);
            addButton.setOpacity(0.5);
        }
    }

    public void manageTeam() {
        if (teamsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/view/nba.png"));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManageTeamView.fxml"));
                Parent root = loader.load();
                stage.setScene(new Scene(root));

                ManageTeamController manageTeamController = loader.getController();
                manageTeamController.setTeam(teamsTable.getSelectionModel().getSelectedItem());

                stage.setTitle("Managing Team: " + teamsTable.getSelectionModel().getSelectedItem().getName());
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void deleteTeam() {
        Team selectedTeam = teamsTable.getSelectionModel().getSelectedItem();

        if (selectedTeam != null) {
            teams.currentTeams().remove(selectedTeam);
            refreshTable();
        }
    }



    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}