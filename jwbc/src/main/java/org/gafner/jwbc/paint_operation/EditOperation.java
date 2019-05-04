package org.gafner.jwbc.paint_operation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.gafner.jwbc.utils.Pair;

import java.util.ArrayList;
import java.util.List;


public class EditOperation implements DrawOperation {


    private final Paint stroke;
    private final Integer thickensLine;
    private final List<Pair<Double, Double>> points;


    public EditOperation(List<Pair<Double, Double>> points, Color stroke, Integer thickensLine) {

        this.points = new ArrayList<>(points);
        this.stroke = stroke;
        this.thickensLine = thickensLine;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(stroke);
        gc.setLineWidth(thickensLine);
        gc.beginPath();
        points.forEach(p -> {
            gc.stroke();
            gc.lineTo(p.first, p.second);
        });

        gc.closePath();
    }
}
