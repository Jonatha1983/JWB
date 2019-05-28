package com.gafner.jwb.api;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * Implemented to custom the Alert look
 */
public class JWBAlert extends Alert {

    JWBAlert(AlertType alertType) {
        this(alertType, "");

    }

    public JWBAlert(AlertType information, String s) {
        super(information, s);
        DialogPane dialogPaneParent = getDialogPane();
        String externalForm = getClass().getResource("/fxml/jwbc-style.css").toExternalForm();
        dialogPaneParent.getStylesheets().add(externalForm);
        dialogPaneParent.getStyleClass().add("jwbDialog");
    }
}
