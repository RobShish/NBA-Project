package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.util.LinkedList;

public class ErrorController {

    @FXML
    private RowConstraints errorMessageHeight;
    @FXML
    private Text errorMessage;

    @FXML
    private Button closeButton;

    private int errorMessageCount;

    private LinkedList<String> errorMessages;
    private Stage stage;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setText(errorMessage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void close(ActionEvent event) {


        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();


        clearErrorMessages();

    }

    public void setErrorMessages(LinkedList<String> errorMessages) {
        this.errorMessage.setText(""); //clear message from before
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (String errorMessage : errorMessages) {
            errorMessageCount++;
            errorMessageBuilder.append(errorMessage).append("\n");
        }
        this.errorMessage.setText(errorMessageBuilder.toString());
        setErrorMessageHeight(errorMessageCount);
    }

    public void setErrorMessageHeight(Integer errorMessageCount) {
        switch (errorMessageCount){
            case 1:
                errorMessageHeight.setPrefHeight(30);
                break;
            case 2:
                errorMessageHeight.setPrefHeight(70);
                break;
            case 3:
                errorMessageHeight.setPrefHeight(100);
                break;
            case 4:
                errorMessageHeight.setPrefHeight(120);
                break;
        }
    }

    public void clearErrorMessages(){
        errorMessage.setText("");
        errorMessageCount = 0;
    }


}
