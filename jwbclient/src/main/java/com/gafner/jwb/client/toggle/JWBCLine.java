package com.gafner.jwb.client.toggle;

import com.gafner.jwb.client.paint_operation.DrawOperation;
import com.gafner.jwb.client.paint_operation.LineOperation;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;


public class JWBCLine extends JWBCToggleIAbstract {


    private double startX, startY;

    JWBCLine(Canvas canvas , ToggleButton lineButton, ColorPicker colorPicker, ColorPicker colorPickerFill, ComboBox<Integer> comboBoxLineThickens) {
        super(canvas, lineButton,colorPicker,colorPickerFill, comboBoxLineThickens);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.CROSSHAIR;
    }

    @Override
    public void withCanvasPressedDo(StackPane stackPane, double x, double y) {
        startX = x;
        startY = y;
        stackPane.getChildren().add(canvas);
        graphicsContext.setStroke(colorPicker.getValue());
        graphicsContext.setLineWidth(comboBoxLineThickens.getValue());
    }

    @Override
    public void withCanvasDraggedDo(StackPane stackPane, double x, double y) {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.strokeLine(startX, startY, x,y);
    }

    @Override
    public DrawOperation withCanvasReleaseDo(StackPane stackPane, double x, double y) {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        return new LineOperation(startX, startY,x,y,colorPicker.getValue(),comboBoxLineThickens.getValue());
    }

}
