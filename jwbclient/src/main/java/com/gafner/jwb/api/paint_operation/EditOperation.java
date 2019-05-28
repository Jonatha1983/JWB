package com.gafner.jwb.api.paint_operation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gafner.jwb.api.utils.Pair;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class EditOperation implements DrawOperation , Serializable {

    private final Integer thickensLine;

    private final List<Pair> points;


    @JsonSerialize(using = ColorCombineSerializer.ColorJsonSerializer.class)
    @JsonDeserialize(using = ColorCombineSerializer.ColorJsonDeserializer.class)
    private final Color stroke;



    public EditOperation(@JsonProperty("points")List<Pair> points, @JsonProperty("stroke")Color stroke, @JsonProperty("thickensLine")Integer thickensLine) {

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
            gc.lineTo(p.getFirst(), p.getSecond());
        });

        gc.closePath();
    }

    public Color getStroke() {
        return stroke;
    }

    public Integer getThickensLine() {
        return thickensLine;
    }

    public List<Pair> getPoints() {
        return points;
    }
}
