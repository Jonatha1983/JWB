package org.gafner.jwbc.toggle;


import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import org.gafner.jwbc.paint_operation.DrawOperation;

public interface JWBCToggleI {

    Cursor getCursor();

    boolean isSelected();

    void withCanvasPressedDo(StackPane stackPane, double x, double y);

    void withCanvasDraggedDo(StackPane stackPane, double x, double y);

    DrawOperation withCanvasReleaseDo(StackPane stackPane, double x, double y);
}
