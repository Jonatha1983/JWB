package org.gafner.jwbc.paint_operation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class LineOperation implements DrawOperation {

    private final double startX;
    private final double startY;
    private final double endX;
    private final double endY;
    private final Paint stroke;
    private final Integer thickensLine;


    public LineOperation(double startXCorner, double startYCorner, double currentW, double currentH, Color stroke, Integer thickensLine) {
        this.startX = startXCorner;
        this.startY = startYCorner;
        this.endX = currentW;
        this.endY = currentH;
        this.stroke = stroke;
        this.thickensLine = thickensLine;
    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.setStroke(stroke);
        gc.setLineWidth(thickensLine);
        gc.strokeLine(startX,startY,endX,endY);
    }
}
