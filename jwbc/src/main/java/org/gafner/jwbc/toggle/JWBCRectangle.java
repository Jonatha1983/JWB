package org.gafner.jwbc.toggle;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.gafner.jwbc.paint_operation.DrawOperation;
import org.gafner.jwbc.paint_operation.RectangleDrawOperation;

public class JWBCRectangle extends JWBCToggleIAbstract {

    private double startXCorner, startYCorner;
    private double currentW, currentH;

    JWBCRectangle(Canvas canvas, ToggleButton rectangleButton, ColorPicker colorPicker, ColorPicker colorPickerFill, ComboBox<Integer> comboBoxLineThickens) {
        super(canvas, rectangleButton, colorPicker, colorPickerFill, comboBoxLineThickens);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.CROSSHAIR;
    }

    @Override
    public void withCanvasPressedDo(StackPane stackPane, double x, double y) {
        startXCorner = x;
        startYCorner = y;
        currentW = 0;
        currentH = 0;
        stackPane.getChildren().add(canvas);
        graphicsContext2D.setFill(colorPickerFill.getValue());
        graphicsContext2D.setStroke(colorPicker.getValue());
        graphicsContext2D.setLineWidth(comboBoxLineThickens.getValue());
    }

    @Override
    public void withCanvasDraggedDo(StackPane stackPane, double x, double y) {
        graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double dx = x - startXCorner;
        double dy = y - startYCorner;

        currentW = Math.abs(dx);
        currentH = Math.abs(dy);
        if (x < startXCorner) {
            if (y < startYCorner) {
                graphicsContext2D.fillRect(x, y, currentW, currentH);
                graphicsContext2D.strokeRect(x, y, currentW, currentH);
            } else {
                graphicsContext2D.fillRect(x, startYCorner, currentW, currentH);
                graphicsContext2D.strokeRect(x, startYCorner, currentW, currentH);
            }
        } else {
            if (y < startYCorner) {
                graphicsContext2D.fillRect(startXCorner, y, currentW, currentH);
                graphicsContext2D.strokeRect(startXCorner, y, currentW, currentH);
            } else {
                graphicsContext2D.fillRect(startXCorner, startYCorner, currentW, currentH);
                graphicsContext2D.strokeRect(startXCorner, startYCorner, currentW, currentH);
            }
        }
    }


    @Override
    public DrawOperation withCanvasReleaseDo(StackPane stackPane, double x, double y) {

        graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);

        currentW = Math.abs(x - startXCorner);
        currentH = Math.abs(y - startYCorner);

        startXCorner = Math.min(x, startXCorner);
        startYCorner = Math.min(y, startYCorner);

        return new RectangleDrawOperation(startXCorner,startYCorner,currentW,currentH,colorPicker.getValue(),colorPickerFill.getValue(),comboBoxLineThickens.getValue());
    }

}
