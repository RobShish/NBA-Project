package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Team;
import model.Teams;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTeamController extends Controller<Teams> {
    @FXML
    private TextField teamNameField;
    private Teams teams;

    public void initialize() {
        teams = new Teams();
    }

    private TeamsController teamsController;

    public void setTeamsController(TeamsController teamsController) {
        this.teamsController = teamsController;
    }

    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private ObservableList<Team> teamList;

    public void setTeamList(ObservableList<Team> teamList) {
        this.teamList = teamList;
    }


    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    @FXML
    private void addButton(ActionEvent event) {
        String teamName = teamNameField.getText();

        Validator validator = new Validator();
        validator.generateErrors(teamName);

        if (!validator.errors().isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
                Parent root = loader.load();

                ErrorController errorController = loader.getController();
                errorController.setErrorMessages(validator.errors());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Error!");
                stage.getIcons().add(new Image("/view/error.png"));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AddTeamController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (teamList.stream().anyMatch(team -> team.getName().equalsIgnoreCase(teamName))) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
                    Parent root = loader.load();

                    ErrorController errorController = loader.getController();
                    LinkedList<String> errorMessages = new LinkedList<>();
                    errorMessages.add(teamName + " Team already exists");
                    errorController.setErrorMessages(errorMessages);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Error!");
                    stage.getIcons().add(new Image("/view/error.png"));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AddTeamController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                teams.addTeam(new Team(teamName));
                teamsController.refreshTable();
                System.out.println(teams.currentTeams());
                if (stage != null) {
                    stage.close();
                }
            }
        }
    }


    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
