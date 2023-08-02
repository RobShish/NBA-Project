package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Player;
import model.Teams;
import model.Team;

public class ViewPlayersController extends Controller<Player> {

    @FXML
    private TableView<Player> playerTable;
    @FXML
    private TableColumn<Player, String> teamNameColumn;
    @FXML
    private TableColumn<Player, String> playerNameColumn;
    @FXML
    private TableColumn<Player, Double> playerCreditColumn;
    @FXML
    private TableColumn<Player, Integer> playerAgeColumn;
    @FXML
    private TableColumn<Player, Integer> playerNoColumn;
    @FXML
    private TableColumn<Player, String> playerLevelColumn;
    @FXML
    private TextField levelFilterField;
    @FXML
    private TextField nameFilterField;
    @FXML
    private TextField ageFromFilterField;
    @FXML
    private TextField ageToFilterField;
    @FXML
    private Button closeButton;
    private Teams teams;
    private ObservableList<Player> playerData = FXCollections.observableArrayList();
    private FilteredList<Player> filteredData;



    @FXML
    public void initialize() {

        teams = ExploreTeamsController.getTeamsInstance();
        populatePlayerData();

        ExploreTeamsController.teamsProperty().addListener((obs, oldTeams, newTeams) -> {
            teams = newTeams;
            populatePlayerData();
        });

        filteredData = new FilteredList<>(playerData, p -> true);
        
        playerTable.setPlaceholder(new Text("Players list is not loaded."));
        
        //Table data
        teamNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeamName()));
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        playerCreditColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerCreditProperty().asObject());
        playerAgeColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerAgeProperty().asObject());
        playerNoColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayerNoProperty().asObject());
        playerLevelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLevel()));


        playerTable.setItems(filteredData);

        // filters
        levelFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(player -> player.getLevel().toLowerCase().contains(newValue.toLowerCase().trim())));

        nameFilterField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(player -> player.getName().toLowerCase().contains(newValue.toLowerCase().trim())));

        ageFromFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().matches("\\d+")) {
                filteredData.setPredicate(player -> player.getAge() >= Integer.parseInt(newValue.trim()));
            }
        });

        ageToFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().matches("\\d+")) {
                filteredData.setPredicate(player -> player.getAge() <= Integer.parseInt(newValue.trim()));
            }
        });


    }


    public void setTeams(Teams teams) {
        this.teams = teams;
        populatePlayerData();
    }
    private void populatePlayerData() {
        if (teams != null && teams.currentTeams() != null) {
            for(Team team : teams.currentTeams()){
                playerData.addAll(team.getCurrentPlayers());
            }
        }
    }
    @FXML
    public void close(ActionEvent event) {
           //close
        closeButton.getScene().getWindow().hide();
    }
}