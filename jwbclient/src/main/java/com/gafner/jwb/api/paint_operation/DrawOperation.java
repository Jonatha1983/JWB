package com.gafner.jwb.api.paint_operation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.canvas.GraphicsContext;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RectangleDrawOperation.class, name = "rect"),
        @JsonSubTypes.Type(value = OvalDrawOperation.class, name = "oval"),
        @JsonSubTypes.Type(value = LineOperation.class, name = "line"),
        @JsonSubTypes.Type(value = EditOperation.class, name = "edit")})
public interface DrawOperation {
    void draw(GraphicsContext gc);
}
