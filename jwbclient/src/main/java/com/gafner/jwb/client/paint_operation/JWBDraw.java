package com.gafner.jwb.client.paint_operation;

import com.fasterxml.jackson.annotation.JacksonInject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JWBDraw {

    private final List<DrawOperation> drawOperations = new ArrayList<>();

    @JacksonInject
    private GraphicsContext graphicsContext;

    private int historyIndex = -1;

    public JWBDraw() {
    }

    public JWBDraw(GraphicsContext graphicsContext2D) {
        this.graphicsContext = graphicsContext2D;
    }

    public void redraw() {
        Canvas c = graphicsContext.getCanvas();
        graphicsContext.clearRect(0, 0, c.getWidth(), c.getHeight());
        for (int i = 0; i <= historyIndex; i++) {
            drawOperations.get(i).draw(graphicsContext);
        }
    }

    public void redrawFromLoad() {
        Canvas c = graphicsContext.getCanvas();
        graphicsContext.clearRect(0, 0, c.getWidth(), c.getHeight());
        for (int i = 0; i <= historyIndex; i++) {
            drawOperations.get(i).draw(graphicsContext);
        }
    }

    public void addDrawOperation(@NotNull DrawOperation drawOperation) {
        // clear history after current postion
        drawOperations.subList(historyIndex + 1, drawOperations.size()).clear();
        // add new operation
        drawOperations.add(drawOperation);
        historyIndex++;
        drawOperation.draw(graphicsContext);
    }

    public void undo() {
        if (historyIndex >= 0) {
            historyIndex--;
            redraw();
        }
    }

    public void redo() {
        if (historyIndex < drawOperations.size() - 1) {
            historyIndex++;
            drawOperations.get(historyIndex).draw(graphicsContext);
        }
    }

    public int getHistoryIndex() {
        return historyIndex;
    }

    public List<DrawOperation> getDrawOperations() {
        return drawOperations;
    }

}
