package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Team;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import model.Player;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerUpdateController {
    private Team team;
    private Validator validator;

    @FXML
    private TextField playerNameTextField;
    @FXML
    private TextField playerCreditTextField;
    @FXML
    private TextField playerAgeTextField;
    @FXML
    private TextField playerNoTextField;

    @FXML
    private Button updateButton;
    @FXML
    private Button addButton;
    @FXML
    private Button closeButton;
    private Player player;

    public PlayerUpdateController() {
        validator = new Validator();
    }

    public void setTeam(Team team) {
        this.team = team;
        updateButton.setDisable(true);
        addButton.setDisable(false);
    }

    public void setPlayer(Player player) {
        this.player = player;
        playerNameTextField.setText(player.getName());
        playerCreditTextField.setText(String.valueOf(player.getCredit()));
        playerAgeTextField.setText(String.valueOf(player.getAge()));
        playerNoTextField.setText(String.valueOf(player.getNo()));

        addButton.setDisable(true);
        updateButton.setDisable(false);
    }

    @FXML
    public void addPlayer() {
        String name = playerNameTextField.getText();
        String credit = playerCreditTextField.getText();
        String age = playerAgeTextField.getText();
        String no = playerNoTextField.getText();

        System.out.println("Name: " + name + ", Credit: " + credit + ", Age: " + age + ", No: " + no);
        // check input
        validator.generateErrors(name, credit, age, no);
        if (validator.errors().isEmpty()) {
            System.out.println("Validation passed, trying to add player...");
            Player player = new Player(name, Double.parseDouble(credit), Integer.parseInt(age), Integer.parseInt(no));
            System.out.println("Player added successfully!");
            player.setTeam(team);
            player.setNo(Integer.parseInt(no));
            player.setName(name);
            player.setAge(Integer.parseInt(age));
            player.setCredit(Double.parseDouble(credit));
            player.setLevel();

            team.getCurrentPlayers().add(player);
            clearFields();

            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Validation failed with errors: " + validator.errors());
            openErrorWindow(validator.errors());
        }
    }

    @FXML
    public void updatePlayer() {
        String name = playerNameTextField.getText();
        String credit = playerCreditTextField.getText();
        String age = playerAgeTextField.getText();
        String no = playerNoTextField.getText();


        validator.generateErrors(name, credit, age, no);
        if (validator.errors().isEmpty()) {
            player.setName(name);
            player.setCredit(Double.parseDouble(credit));
            player.setAge(Integer.parseInt(age));
            player.setNo(Integer.parseInt(no));
            close();
        } else {
            // open errow window with errors
            openErrorWindow(validator.errors());
        }
    }

    private void clearFields() {
        playerNameTextField.setText("");
        playerCreditTextField.setText("");
        playerAgeTextField.setText("");
        playerNoTextField.setText("");
    }

    private void openErrorWindow(LinkedList<String> errors) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
            Parent root = loader.load();

            ErrorController errorController = loader.getController();
            errorController.setErrorMessages(errors);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Error!");
            stage.getIcons().add(new Image("/view/error.png"));
            stage.show();

            errorController.setStage(stage);

        } catch (IOException ex) {
            Logger.getLogger(PlayerUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
