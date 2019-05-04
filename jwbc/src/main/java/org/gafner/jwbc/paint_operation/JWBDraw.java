package org.gafner.jwbc.paint_operation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JWBDraw {

    private final List<DrawOperation> operations = new ArrayList<>();
    private final GraphicsContext gc;
    private int historyIndex = -1;


    public JWBDraw(GraphicsContext gc) {
        this.gc = gc;
    }

    public void redraw() {
        Canvas c = gc.getCanvas();
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());
        for (int i = 0; i <= historyIndex; i++) {
            operations.get(i).draw(gc);
        }
    }

    public void addDrawOperation(@NotNull DrawOperation op) {
        // clear history after current postion
        operations.subList(historyIndex + 1, operations.size()).clear();

        // add new operation
        operations.add(op);
        historyIndex++;
        op.draw(gc);
    }

    public void undo() {
        if (historyIndex >= 0) {
            historyIndex--;
            redraw();
        }
    }

    public void redo() {
        if (historyIndex < operations.size() - 1) {
            historyIndex++;
            operations.get(historyIndex).draw(gc);
        }
    }
}
