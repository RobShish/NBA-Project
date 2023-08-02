package au.edu.uts.ap.javafx;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import java.io.*;
import java.lang.Runnable;

public class ViewLoader {

    public final static int X = 5;
    public final static int Y = 5;

    public static <T> void showStage(T model, String fxml, String title, Stage stage, Runnable onStageClosed) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource(fxml), null, null,
                type -> {
                    try {
                        @SuppressWarnings("unchecked")
                        Controller<T> controller = (Controller<T>) type.newInstance();
                        controller.model = model;
                        controller.stage = stage;
                        return controller;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        Parent root = loader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.setOnCloseRequest(e -> onStageClosed.run());
        stage.show();
    }

    public static <T> void showStage(T model, String fxml, String title, Stage stage) throws IOException {
        showStage(model, fxml, title, stage, () -> {
        });
    }

    public static void showStage(String viewAssociationViewfxml, String nbAfx_View, Stage primaryStage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
