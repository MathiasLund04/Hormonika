package org.example.hormonika;

import  javafx.fxml.FXML;
import  javafx.fxml.FXMLLoader;
import  javafx.scene.Scene;
import  javafx.scene.control.*;
import  javafx.stage.Stage;

import  java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private static final List<String> usernames = new ArrayList<>();
    private static final List<String> passwords = new ArrayList<>();

    static {
        usernames.add("Mads");
        usernames.add("Ida");
        usernames.add("Fie");
        usernames.add("Mie");
        usernames.add("Monika");

        passwords.add("Mads123");
        passwords.add("Ida123");
        passwords.add("Fie123");
        passwords.add("Mie123");
        passwords.add("Monika123");

    }

    @FXML
    private void onLogin() {
        String username = usernameField.getText().trim().toLowerCase();
        String password = passwordField.getText().trim().toLowerCase();

        if (validateUser(username) && validatePassword(username, password)) {
            openCalenderView();
        } else {
            errorLabel.setText("Forkert brugernavn eller adgangskode");
        }
    }

    private boolean validateUser(String username) {
        return username.contains(username);
    }

    private boolean validatePassword(String username, String password) {
        int index = usernames.indexOf(username);
        return (index >= 0) && passwords.get(index).equals(password);
    }

    private void openCalenderView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CalenderView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Kalender");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




