package com.gafner.jwb.client.paint_operation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;


@SuppressWarnings("unused")
public class LineOperation implements DrawOperation, Serializable {

    private final double startX;
    private final double startY;
    private final double endX;
    private final double endY;
    @JsonSerialize(using = ColorCombineSerializer.ColorJsonSerializer.class)
    @JsonDeserialize(using = ColorCombineSerializer.ColorJsonDeserializer.class)
    private final Color stroke;
    private final Integer thickensLine;


    public LineOperation(@JsonProperty("startXCorner") double startXCorner,
                         @JsonProperty("startYCorner") double startYCorner,
                         @JsonProperty("currentW") double currentW,
                         @JsonProperty("currentH") double currentH,
                         @JsonProperty("stroke") Color stroke,
                         @JsonProperty("thickensLine") Integer thickensLine) {
        this.startX = startXCorner;
        this.startY = startYCorner;
        this.endX = currentW;
        this.endY = currentH;
        this.stroke = stroke;
        this.thickensLine = thickensLine;
    }


    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    public Color getStroke() {
        return stroke;
    }

    public Integer getThickensLine() {
        return thickensLine;
    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.setStroke(stroke);
        gc.setLineWidth(thickensLine);
        gc.strokeLine(startX, startY, endX, endY);
    }
}
