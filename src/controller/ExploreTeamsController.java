package controller;

import au.edu.uts.ap.javafx.ViewLoader;
import au.edu.uts.ap.javafx.Controller;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Teams;

public class ExploreTeamsController extends Controller<Teams> {

    @FXML
    private Button teamsButton;

    @FXML
    private Button viewPlayersButton;

    @FXML
    private Button closeButton;

    private static Teams teams;

    //public static Teams getTeamsInstance(){
    //    return teams;
    //}
    private static ObjectProperty<Teams> teamsProperty = new SimpleObjectProperty<>();

    public static ObjectProperty<Teams> teamsProperty() {
        return teamsProperty;
    }

    public static Teams getTeamsInstance(){
        return teamsProperty.get();
    }

    public void initialize() {
        teamsProperty.set(this.model);

    }
    private TeamsController teamsController;

    public void setTeamsController(TeamsController teamsController) {
        this.teamsController = teamsController;
    }


    @FXML
    public void viewPlayers(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayersView.fxml"));
            Parent root = loader.load();

            ViewPlayersController viewPlayersController = loader.getController();
            viewPlayersController.setTeams(teams);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ExploreTeamsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void viewTeams() {
        try {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TeamsTable.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            TeamsController teamsController = loader.getController();
            teamsController.setTeams(getTeamsInstance());

            // Pass the Teams to object because i cant read
            SeasonController seasonController = new SeasonController();
            seasonController.setTeams(getTeamsInstance());

            stage.setTitle("View Teams");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ExploreTeamsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}