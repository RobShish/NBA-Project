package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Teams;
import model.Team;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeasonController extends Controller<Teams> {

    @FXML
    private Button roundButton;
    @FXML
    private Button currentButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button resultButton;
    @FXML
    private Button closeButton;

    private Teams teams;

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    @FXML
    public void openRoundWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SeasonRoundView.fxml"));
            Parent root = loader.load();

            TeamsRoundController teamsRoundController = loader.getController();
            teamsRoundController.setTeams(teams);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Round Window");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeasonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    public void openCurrentWindow() {

    }

    @FXML
    public void openGameWindow() {
        
    }

    @FXML
    public void openResultWindow() {
        
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
