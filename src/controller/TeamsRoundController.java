package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Team;
import model.Teams;

public class TeamsRoundController  {
    private Teams teams;
    @FXML
    private ListView<Team> teamsList;
    private ObservableList<Team> teamList;

    public void setTeams(Teams teams) {
        this.teams = teams;
    }



}



