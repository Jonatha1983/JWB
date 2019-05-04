package org.gafner.jwbc.toggle;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.gafner.jwbc.paint_operation.DrawOperation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class JWBCToggleManager {

    private final Set<JWBCToggleI> toggleButtons;
    private Canvas canvas;

    public JWBCToggleManager(Canvas canvas, ToggleButton editButton, ToggleButton eraseButton, ToggleButton rectangleButton, ToggleButton lineButton, ToggleButton circleButton, ComboBox<Integer> comboBoxLineThickens, ColorPicker colorPicker, ColorPicker colorPickerFill) {
        this.canvas = canvas;
        JWBCToggleI toggleIEdit = new JWBCEdit(canvas, editButton, colorPicker, colorPickerFill, comboBoxLineThickens);
        JWBCToggleI toggleIErase = new JWBCEraser(canvas, eraseButton, colorPicker, colorPickerFill, comboBoxLineThickens);
        JWBCToggleI toggleIRectangle = new JWBCRectangle(canvas, rectangleButton, colorPicker, colorPickerFill, comboBoxLineThickens);
        JWBCToggleI toggleILine = new JWBCLine(canvas, lineButton, colorPicker, colorPickerFill, comboBoxLineThickens);
        JWBCToggleI toggleICircle = new JWBCOval(canvas, circleButton, colorPicker, colorPickerFill, comboBoxLineThickens);
        toggleButtons = Set.of(toggleIEdit, toggleIErase, toggleIRectangle, toggleILine, toggleICircle);
    }

    public void withCanvasPressedDo(StackPane stackPane, MouseEvent e) {
        getSelected().ifPresent(t -> t.withCanvasPressedDo(stackPane, e.getX(), e.getY()));
    }

    public void withCanvasDraggedDo(StackPane stackPane, MouseEvent e) {
        getSelected().ifPresent(t -> t.withCanvasDraggedDo(stackPane, e.getX(), e.getY()));
    }

    @Nullable
    public DrawOperation withCanvasReleaseDo(StackPane stackPane, MouseEvent e) {
        return getSelected().map(t -> t.withCanvasReleaseDo(stackPane, e.getX(), e.getY())).orElse(null);
    }

    private Optional<JWBCToggleI> getSelected() {
        return toggleButtons.stream().filter(JWBCToggleI::isSelected).findFirst();
    }

    public void withEnteredSetCursor() {
        getSelected().ifPresent(t -> canvas.setCursor(t.getCursor()));
    }

    public void withExitedResetCursor() {
        canvas.setCursor(Cursor.DEFAULT);
    }
}
