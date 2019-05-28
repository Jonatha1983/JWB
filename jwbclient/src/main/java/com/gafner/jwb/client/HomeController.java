package com.gafner.jwb.client;

import com.gafner.jwb.api.service.users.UserConnectionService;
import com.gafner.jwb.config.StageManager;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings({"unused", "SpringJavaAutowiredFieldsWarningInspection"})
@Controller
public class HomeController implements Initializable {
    public TextField firstName, lastName, email, emailSignUp;
    public PasswordField password, passwordSignUp, passwordConfirmation;
    public Button signUp;
    public Button login;

    @Autowired
    private UserConnectionService userConnectionService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanBinding emailBinding = Bindings.createBooleanBinding(() -> email.getText().trim().isEmpty(), email.textProperty());
        BooleanBinding passwordBinding = Bindings.createBooleanBinding(() -> password.getText().trim().isEmpty(), password.textProperty());

        login.disableProperty().bind(emailBinding.or(passwordBinding));


        BooleanBinding firstNameSignUpBinding = Bindings.createBooleanBinding(() -> firstName.getText().trim().isEmpty(), firstName.textProperty());
        BooleanBinding lastNameSignUpBinding = Bindings.createBooleanBinding(() -> lastName.getText().trim().isEmpty(), lastName.textProperty());
        BooleanBinding emailSignUpBinding = Bindings.createBooleanBinding(() -> emailSignUp.getText().trim().isEmpty(), emailSignUp.textProperty());
        BooleanBinding passwordSignUpBinding = Bindings.createBooleanBinding(() -> passwordSignUp.getText().trim().isEmpty(), passwordSignUp.textProperty());
        BooleanBinding confirmSignUpBinding = Bindings.createBooleanBinding(() -> passwordConfirmation.getText().trim().isEmpty(), passwordConfirmation.textProperty());

        signUp.disableProperty().bind(firstNameSignUpBinding.or(lastNameSignUpBinding).or(emailSignUpBinding).or(passwordSignUpBinding).or(confirmSignUpBinding));

    }

    @FXML
    public void login(ActionEvent actionEvent) {
        if (userConnectionService.authenticate(email.getText(), password.getText())) {
            stageManager.switchScene(FXMLViews.PAINT_CHOOSER);
        } else {
            alertUserForFailed("Wrong user or password , please retry");
        }
    }

    @FXML
    public void signUp(ActionEvent actionEvent) {
        if (validate("First Name", getFirstName(), "[a-zA-Z]+") &&
                validate("Last Name", getLastName(), "[a-zA-Z]+") &&
                validate("Email", getEmailSignUp(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
                validateEqual()) {
            boolean saveUserSucceed = userConnectionService.saveUserParameters(getFirstName(), getLastName(), getEmailSignUp(), getPasswordSignUp());

            saveAlert(saveUserSucceed);

            if (saveUserSucceed) {
                stageManager.switchScene(FXMLViews.PAINT_CHOOSER);
            }
        }
    }

    private boolean validateEqual() {
        if (!getPasswordSignUp().equals(getPasswordConfirmation())) {
            alertUserForFailed("Password did not match, please retry");
            return false;
        }
        return true;
    }

    private void saveAlert(boolean saveOperationSucceed) {
        if (saveOperationSucceed) {
            JWBAlert alert = new JWBAlert(Alert.AlertType.INFORMATION);
            alert.setTitle("User saved successfully.");
            alert.setContentText("The user " + getFirstName() + " " + getLastName() + " has been created successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new JWBAlert(Alert.AlertType.ERROR);
            alert.setTitle("User could not be saved");
            alert.setContentText("User " + getEmailSignUp() + " already in use");
            alert.showAndWait();
        }
    }

    private void alertUserForFailed(String s) {
        Alert alert = new JWBAlert(Alert.AlertType.INFORMATION);
        alert.setTitle("Failed Operation");
        alert.setContentText(s);
        alert.showAndWait();

    }

    private String getFirstName() {
        return firstName.getText();
    }

    private String getLastName() {
        return lastName.getText();
    }

    private String getEmailSignUp() {
        return emailSignUp.getText();
    }

    private String getPasswordSignUp() {
        return passwordSignUp.getText();
    }

    private String getPasswordConfirmation() {
        return passwordConfirmation.getText();
    }

    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    String getUsedEmail() {
        if (email.getText() != null && !email.getText().isBlank()) {
            return email.getText();
        }
        return emailSignUp.getText();
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new JWBAlert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) alert.setContentText("Please Select " + field);
        else {
            if (empty) alert.setContentText("Please Enter " + field);
            else alert.setContentText("Please Enter Valid " + field);
        }
        alert.showAndWait();
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

}
