package org.gafner.jwbc.paint_operation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class RectangleDrawOperation implements DrawOperation {

    private final double startXCorner;
    private final double startYCorner;
    private final double currentW;
    private final double currentH;
    private final Paint stroke;
    private final Paint fill;
    private final Integer thickensLine;


    public RectangleDrawOperation(double startXCorner, double startYCorner, double currentW, double currentH, Color stroke, Color fill, Integer thickensLine) {
        this.startXCorner = startXCorner;
        this.startYCorner = startYCorner;
        this.currentW = currentW;
        this.currentH = currentH;
        this.stroke = stroke;
        this.fill = fill;
        this.thickensLine = thickensLine;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(stroke);
        gc.setFill(fill);
        gc.setLineWidth(thickensLine);
        gc.fillRect(startXCorner, startYCorner, currentW, currentH);
        gc.strokeRect(startXCorner, startYCorner, currentW, currentH);
    }
}
