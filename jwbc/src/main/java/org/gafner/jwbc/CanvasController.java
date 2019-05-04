package org.gafner.jwbc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.gafner.jwbc.paint_operation.DrawOperation;
import org.gafner.jwbc.paint_operation.JWBDraw;
import org.gafner.jwbc.toggle.JWBCToggleManager;

import java.net.URL;
import java.util.ResourceBundle;


public class CanvasController implements Initializable {

    @FXML
    public StackPane stackPane;

    @FXML
    private Canvas canvas;

    private JWBCToggleManager toggleManager;
    private boolean saved;
    private JWBDraw jwbDraw;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jwbDraw = new JWBDraw(canvas.getGraphicsContext2D());
    }


    @FXML
    public void mousePressed(MouseEvent mouseEvent) {
        saved = false;
        toggleManager.withCanvasPressedDo(stackPane, mouseEvent);
    }

    @FXML
    public void mouseDragged(MouseEvent mouseEvent) {
        saved = false;
        toggleManager.withCanvasDraggedDo(stackPane, mouseEvent);
    }

    @FXML
    public void mouseReleased(MouseEvent mouseEvent) {
        saved = false;
        DrawOperation DrawOperation = toggleManager.withCanvasReleaseDo(stackPane, mouseEvent);
        if (DrawOperation != null) {
            jwbDraw.addDrawOperation(DrawOperation);
        }
    }

    @FXML
    public void mouseEntered(MouseEvent mouseEvent) {
        toggleManager.withEnteredSetCursor();
    }

    @FXML
    public void onMouseExited(MouseEvent mouseEvent) {
        toggleManager.withExitedResetCursor();
    }

    public boolean isSaved() {
        return saved;
    }

    public void save() {
        saved = true;
    }

    public void undo() {
        jwbDraw.undo();
    }

    public void redo() {
        jwbDraw.redo();
    }

    public void connectToPrimaryController(JWBCController jwbcController) {
        this.toggleManager = new JWBCToggleManager(canvas,
                jwbcController.getEditButton(),
                jwbcController.getEraseButton(),
                jwbcController.getRectangleButton(),
                jwbcController.getLineButton(),
                jwbcController.getCircleButton(),
                jwbcController.getComboBoxLineThickens(),
                jwbcController.getColorPicker(),
                jwbcController.getColorPickerFill());
    }
}
