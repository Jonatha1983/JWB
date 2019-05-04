package org.gafner.jwbc.toggle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import org.gafner.jwbc.paint_operation.DrawOperation;
import org.gafner.jwbc.paint_operation.OvalDrawOperation;

@SuppressWarnings("ALL")
public class JWBCOval extends JWBCToggleIAbstract {

    private double startXCorner, startYCorner;
    private double currentW, currentH;

    private SimpleDoubleProperty xProperty, yProperty;

    JWBCOval(Canvas canvas, ToggleButton circleButton, ColorPicker colorPicker, ColorPicker colorPickerFill, ComboBox<Integer> comboBoxLineThickens) {
        super(canvas, circleButton, colorPicker, colorPickerFill, comboBoxLineThickens);
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
        graphicsContext2D.fillOval(startXCorner, startYCorner, currentW, currentH);
        graphicsContext2D.strokeOval(startXCorner, startYCorner, currentW, currentH);
    }

    @Override
    public void withCanvasDraggedDo(StackPane stackPane, double x, double y) {
        graphicsContext2D.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double dx = x - startXCorner;
        double dy = y - startYCorner;

        currentW = Math.abs(dx);
        currentH = Math.abs(dy);
        if (x < startXCorner){
            if ( y < startYCorner){
                graphicsContext2D.fillOval(x, y, currentW, currentH);
                graphicsContext2D.strokeOval(x, y, currentW, currentH);
            }else{
                graphicsContext2D.fillOval(x, startYCorner, currentW, currentH);
                graphicsContext2D.strokeOval(x, startYCorner, currentW, currentH);
            }
        }else{
            if ( y < startYCorner){
                graphicsContext2D.fillOval(startXCorner, y, currentW, currentH);
                graphicsContext2D.strokeOval(startXCorner, y, currentW, currentH);
            }else{
                graphicsContext2D.fillOval(startXCorner, startYCorner, currentW, currentH);
                graphicsContext2D.strokeOval(startXCorner, startYCorner, currentW, currentH);
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

        return new OvalDrawOperation(startXCorner,startYCorner,currentW,currentH,colorPicker.getValue(),colorPickerFill.getValue(),comboBoxLineThickens.getValue());

    }
}
