package org.gafner.jwbc;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("jwbc.fxml"));
        Parent root = loader.load();

        JWBCController mainController = loader.getController();

        mainController.setStageAndSetupListeners(primaryStage);

        primaryStage.setTitle("JWBC");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("jwbc-style.css").toExternalForm());

        primaryStage.setTitle("JWBC");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Paint");
            alert.setHeaderText("Exit Paint");
            alert.setContentText("Are you sure you want to quit Paint?\nUnsaved changes will be lost.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() != ButtonType.OK) {
                e.consume();
            }
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}