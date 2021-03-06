package com.gafner.jwb.client.toggle;

import com.gafner.jwb.client.paint_operation.DrawOperation;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;

public interface JWBCToggleI {

    Cursor getCursor();

    boolean isSelected();

    void withCanvasPressedDo(StackPane stackPane, double x, double y);

    void withCanvasDraggedDo(StackPane stackPane, double x, double y);

    DrawOperation withCanvasReleaseDo(StackPane stackPane, double x, double y);
}
