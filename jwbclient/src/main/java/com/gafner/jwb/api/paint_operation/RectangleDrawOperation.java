package com.gafner.jwb.api.paint_operation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;


@SuppressWarnings("unused")
public class RectangleDrawOperation implements DrawOperation, Serializable {

    private final double startXCorner;
    private final double startYCorner;
    private final double currentW;
    private final double currentH;
    @JsonSerialize(using = ColorCombineSerializer.ColorJsonSerializer.class)
    @JsonDeserialize(using = ColorCombineSerializer.ColorJsonDeserializer.class)
    private final Color stroke;
    @JsonSerialize(using = ColorCombineSerializer.ColorJsonSerializer.class)
    @JsonDeserialize(using = ColorCombineSerializer.ColorJsonDeserializer.class)
    private final Color fill;
    private final Integer thickensLine;


    @JsonCreator
    public RectangleDrawOperation(@JsonProperty("startXCorner") double startXCorner,
                                  @JsonProperty("startYCorner") double startYCorner,
                                  @JsonProperty("currentW") double currentW,
                                  @JsonProperty("currentH") double currentH,
                                  @JsonProperty("stroke") Color stroke,
                                  @JsonProperty("fill") Color fill,
                                  @JsonProperty("thickensLine") Integer thickensLine) {
        this.startXCorner = startXCorner;
        this.startYCorner = startYCorner;
        this.currentW = currentW;
        this.currentH = currentH;
        this.stroke = stroke;
        this.fill = fill;
        this.thickensLine = thickensLine;
    }


    public double getStartXCorner() {
        return startXCorner;
    }

    public double getStartYCorner() {
        return startYCorner;
    }

    public double getCurrentW() {
        return currentW;
    }

    public double getCurrentH() {
        return currentH;
    }

    public Color getStroke() {
        return stroke;
    }

    public Color getFill() {
        return fill;
    }

    public Integer getThickensLine() {
        return thickensLine;
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
