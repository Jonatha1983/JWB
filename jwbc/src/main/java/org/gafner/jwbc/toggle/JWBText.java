package org.gafner.jwbc.toggle;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import org.gafner.jwbc.paint_operation.DrawOperation;

public class JWBText extends JWBCToggleIAbstract {
    public JWBText(Canvas canvas, ToggleButton textButton, ColorPicker colorPicker, ColorPicker colorPickerFill, ComboBox<Integer> comboBoxLineThickens) {
        super(canvas, textButton, colorPicker, colorPickerFill, comboBoxLineThickens);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.TEXT;
    }

    @Override
    public void withCanvasPressedDo(StackPane stackPane, double x, double y) {
        graphicsContext2D.setLineWidth(1.0);
        graphicsContext2D.setFill(colorPicker.getValue());
    }

    @Override
    public void withCanvasDraggedDo(StackPane stackPane, double x, double y) {

    }

    @Override
    public DrawOperation withCanvasReleaseDo(StackPane stackPane, double x, double y) {
        return null;
    }
}
