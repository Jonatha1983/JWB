package com.gafner.jwb.client;

import com.gafner.jwb.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@Controller
public class JWBCController implements Initializable{

    @Lazy
    @Autowired
    private StageManager stageManager;

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

    @Autowired
    private CanvasController canvasController;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colorPicker.setValue(Color.BLACK);
        colorPickerFill.setValue(Color.WHITE);
        initComboLineWidth();
    }

    @FXML
    public void undo(ActionEvent actionEvent) {
        if (canvasController.isInitialize()) {
            canvasController.undo();
        }
    }


    @FXML
    public void close(ActionEvent actionEvent) {
        stageManager.switchScene(FXMLViews.HOME);
    }

    @FXML
    public void redo(ActionEvent actionEvent) {
        if (canvasController.isInitialize()) {
            canvasController.redo();
        }
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        stageManager.switchScene(FXMLViews.HOME);
    }


    @FXML
    public void newGroup(ActionEvent actionEvent) {

        Alert alert = new JWBAlert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New or Join group ");
        alert.setHeaderText("Create a new JWB paint group or join to an existing one");
        alert.setContentText("Please choose: ");

        ButtonType buttonNew = new ButtonType("New");
        ButtonType buttonJoin = new ButtonType("Join");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonNew, buttonJoin, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(rg -> {
            if (rg == buttonNew) {
                // ... user chose "New group"
                newGroupInternal();

            } else if (rg == buttonJoin) {
                // ... user chose "Join
                joinGroupInternal();
            }  // ... user chose CANCEL or closed the dialog
        });
    }

    ComboBox<Integer> getComboBoxLineThickens() {
        return comboBoxLineThickens;
    }

    ColorPicker getColorPicker() {
        return colorPicker;
    }

    ColorPicker getColorPickerFill() {
        return colorPickerFill;
    }

    ToggleButton getEditButton() {
        return editButton;
    }

    ToggleButton getEraseButton() {
        return eraseButton;
    }

    ToggleButton getLineButton() {
        return lineButton;
    }

    ToggleButton getRectangleButton() {
        return rectangleButton;
    }

    ToggleButton getCircleButton() {
        return circleButton;
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

    private void newGroupInternal() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose a JWB paint group userName:");
        dialog.setContentText("Group userName:");
        Optional<String> resultNewGroup = dialog.showAndWait();
        resultNewGroup.ifPresent(groupName -> {
            if (canvasController.newGroup(groupName)) {
                stageManager.setTitle(groupName);
                stageManager.switchScene(FXMLViews.PAINT_CHOOSER);
                canvasContainer.getChildren().get(0).setVisible(true);
                JWBAlert jwbAlert = new JWBAlert(Alert.AlertType.INFORMATION, groupName + " created successfully.");
                jwbAlert.show();
            } else {
                JWBAlert alert = new JWBAlert(Alert.AlertType.ERROR, groupName + " already exists, chose a new one.");
                alert.show();
            }
        });
    }

    private void joinGroupInternal() {
        List<String> choices = canvasController.getExistingGroup();

        if (!choices.isEmpty()) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("Join a JWB Group");
            dialog.setHeaderText("Join one of existing JWB pain groups");
            dialog.setContentText("Choose your group:");

            // Traditional way to get the response value.
            Optional<String> choseResult = dialog.showAndWait();
            if (choseResult.isPresent()) {
                String groupName = choseResult.get();
                stageManager.setTitle(groupName);
                stageManager.switchScene(FXMLViews.PAINT_CHOOSER);
                canvasContainer.getChildren().get(0).setVisible(true);
                if(canvasController.joinGroup(groupName)){
                    canvasController.update();
                }else{
                    //user chose join but DB is empty
                    JWBAlert jwbAlert = new JWBAlert(Alert.AlertType.INFORMATION, " Could not join this group");
                    jwbAlert.show();
                }
            }
        } else {
            //user chose join but DB is empty
            JWBAlert jwbAlert = new JWBAlert(Alert.AlertType.INFORMATION, " No existing group in DB - create one to start drawing.");
            jwbAlert.show();
        }
    }

}