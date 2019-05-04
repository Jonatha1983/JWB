package org.gafner.jwbc.toggle;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.gafner.jwbc.paint_operation.DrawOperation;
import org.gafner.jwbc.paint_operation.EditOperation;
import org.gafner.jwbc.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class JWBCEraser extends JWBCToggleIAbstract {

    private final List<Pair<Double, Double>> points;

    JWBCEraser(Canvas canvas, ToggleButton eraseButton, ColorPicker colorPicker, ColorPicker colorPickerFill, ComboBox<Integer> comboBoxLineThickens) {
        super(canvas, eraseButton, colorPicker, colorPickerFill, comboBoxLineThickens);
        this.points = new ArrayList<>();
    }

    @Override
    public Cursor getCursor() {
        return Cursor.CLOSED_HAND;
    }

    @Override
    public void withCanvasPressedDo(StackPane stackPane, double x, double y) {
        stackPane.getChildren().add(canvas);
        graphicsContext2D.setStroke(Color.WHITE);
        graphicsContext2D.setLineWidth(comboBoxLineThickens.getValue());
        graphicsContext2D.beginPath();
        graphicsContext2D.moveTo(x, y);
        graphicsContext2D.stroke();
        points.add(Pair.create(x, y));

    }

    @Override
    public void withCanvasDraggedDo(StackPane stackPane, double x, double y) {
        graphicsContext2D.lineTo(x, y);
        graphicsContext2D.stroke();
        graphicsContext2D.closePath();
        graphicsContext2D.beginPath();
        graphicsContext2D.moveTo(x, y);
        points.add(Pair.create(x, y));
    }

    @Override
    public DrawOperation withCanvasReleaseDo(StackPane stackPane, double x, double y) {
        graphicsContext2D.clearRect(0, 0, canvas.getWidth(),canvas.getHeight());
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        return new EditOperation(points, Color.WHITE, comboBoxLineThickens.getValue());
    }
}
