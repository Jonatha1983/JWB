package com.gafner.jwb.client.toggle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class JWBCToggleIAbstract implements JWBCToggleI {

    ColorPicker colorPicker;
    ColorPicker colorPickerFill;
    ComboBox<Integer> comboBoxLineThickens;
    Canvas canvas;
    GraphicsContext graphicsContext;

    @NotNull
    private final ToggleButton toggleButton;


    JWBCToggleIAbstract(@NotNull Canvas canvas, @NotNull ToggleButton toggleButton, ColorPicker colorPicker, ColorPicker colorPickerFill, ComboBox<Integer> comboBoxLineThickens) {
        this.toggleButton = toggleButton;
        this.colorPicker = colorPicker;
        this.colorPickerFill = colorPickerFill;
        this.comboBoxLineThickens = comboBoxLineThickens;
        this.canvas = new Canvas();
        this.canvas.widthProperty().bind(canvas.widthProperty());
        this.canvas.heightProperty().bind(canvas.heightProperty());
        this.graphicsContext = this.canvas.getGraphicsContext2D();
    }


    @NotNull
    private ToggleButton getToggleButton() {
        return toggleButton;
    }

    public boolean isSelected() {
        return toggleButton.isSelected();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JWBCToggleIAbstract)) return false;
        JWBCToggleIAbstract that = (JWBCToggleIAbstract) o;
        return getToggleButton().equals(that.getToggleButton());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToggleButton());
    }
}
