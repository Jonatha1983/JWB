package com.gafner.jwb.client.toggle;

import com.gafner.jwb.client.paint_operation.DrawOperation;
import com.gafner.jwb.client.paint_operation.EditOperation;
import com.gafner.jwb.client.utils.Pair;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class JWBCEdit extends JWBCToggleIAbstract {

    private List<Pair> points;

    JWBCEdit(Canvas canvas, ToggleButton editButton, ColorPicker colorPicker, ColorPicker colorPickerFill, ComboBox<Integer> comboBoxLineThickens) {
        super(canvas, editButton, colorPicker, colorPickerFill, comboBoxLineThickens);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.DEFAULT;
    }

    @Override
    public void withCanvasPressedDo(StackPane stackPane, double x, double y) {
        this.points = new ArrayList<>();
        stackPane.getChildren().add(canvas);
        graphicsContext.setStroke(colorPicker.getValue());
        graphicsContext.setLineWidth(comboBoxLineThickens.getValue());
        graphicsContext.beginPath();
        graphicsContext.moveTo(x, y);
        graphicsContext.stroke();
        points.add(Pair.create(x, y));
    }

    @Override
    public void withCanvasDraggedDo(StackPane stackPane, double x, double y) {
        graphicsContext.lineTo(x, y);
        graphicsContext.stroke();
        graphicsContext.closePath();
        graphicsContext.beginPath();
        graphicsContext.moveTo(x, y);
        points.add(Pair.create(x, y));
    }

    @Override
    public DrawOperation withCanvasReleaseDo(StackPane stackPane, double x, double y) {
        graphicsContext.clearRect(0, 0, canvas.getWidth(),canvas.getHeight());
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
        return new EditOperation(points,colorPicker.getValue(),comboBoxLineThickens.getValue());
    }
}
