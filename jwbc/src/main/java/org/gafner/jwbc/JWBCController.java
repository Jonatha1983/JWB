package org.gafner.jwbc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class JWBCController implements Initializable {


    private Stage primaryStage;


    @FXML
    public ComboBox<Integer> comboBoxLineThickens;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ColorPicker colorPickerFill;
    @FXML
    private ToggleButton editButton;
    @FXML
    private ToggleButton eraseButton;
    @FXML
    public ToggleButton lineButton;
    @FXML
    public ToggleButton rectangleButton;
    @FXML
    public ToggleButton circleButton;

    @FXML
    public StackPane canvasContainer;


    private CanvasController canvasController;


    void setStageAndSetupListeners(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colorPicker.setValue(Color.BLACK);
        colorPickerFill.setValue(Color.TRANSPARENT);
        initComboLineWidth();

    }

    private void initComboLineWidth() {
        comboBoxLineThickens.getItems().addAll(2, 3, 5, 7, 10);
        Callback<ListView<Integer>, ListCell<Integer>> value = new Callback<>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> p) {
                return new ListCell<>() {
                    private final Line line;

                    {
                        setContentDisplay(ContentDisplay.LEFT);
                        line = new Line(0, 0, 15, 0);
                    }

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            line.setStrokeWidth(item);
                            setGraphic(line);
                        }
                    }
                };
            }
        };
        comboBoxLineThickens.setCellFactory(value);
        comboBoxLineThickens.setButtonCell(value.call(null));
        comboBoxLineThickens.getSelectionModel().selectFirst();
    }

    @FXML
    public void undo(ActionEvent actionEvent) {
        if (canvasController != null) {
            canvasController.undo();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {

        if (canvasController != null && !canvasController.isSaved()) {
            canvasController.save();
        }
    }

    @FXML
    public void redo(ActionEvent actionEvent) {
        if (canvasController != null) {
            canvasController.redo();
        }
    }

    public void zoomIn(ActionEvent actionEvent) {

    }

    public void zoomOut(ActionEvent actionEvent) {

    }


    public void newFile(ActionEvent actionEvent) {

        if (canvasController != null && !canvasController.isSaved()) {
            //need to validate if user is in the middle of paintin)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New File");
            alert.setHeaderText("New File");
            alert.setContentText("Are you sure that you want to exit this file, and create a new file?\nUnsaved changes will be lost.");

            Optional<ButtonType> res = alert.showAndWait();
            if (res.isPresent() && res.get() != ButtonType.OK)
                return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose paint name:");
        dialog.setContentText("File name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(w -> {
            primaryStage.setTitle(w);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("jwbc-canvas.fxml"));
                Node load = loader.load();
                canvasController = loader.getController();
                canvasController.connectToPrimaryController(this);
                canvasContainer.getChildren().add(load);

            } catch (IOException e) {
                throw new UncheckedIOException("Could not load ", e);
            }

        });
    }

    public ComboBox<Integer> getComboBoxLineThickens() {
        return comboBoxLineThickens;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public ColorPicker getColorPickerFill() {
        return colorPickerFill;
    }

    public ToggleButton getEditButton() {
        return editButton;
    }

    public ToggleButton getEraseButton() {
        return eraseButton;
    }

    public ToggleButton getLineButton() {
        return lineButton;
    }

    public ToggleButton getRectangleButton() {
        return rectangleButton;
    }

    public ToggleButton getCircleButton() {
        return circleButton;
    }

    public void openFile(ActionEvent actionEvent) {
        // TODO: 2019-04-26 Implement
    }

    public void close(ActionEvent actionEvent) {
        // TODO: 2019-04-26 Implement
    }

}