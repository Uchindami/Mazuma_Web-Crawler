package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class LoginController {

    public javafx.scene.control.TextField passwordField;
    public javafx.scene.control.TextField usernameField;
    public Button logInButton;
    public Label errorpop;
    public Label signupbutton;
    public Button GOTOUSER;
    public TextField tryagain;
    @FXML
    private Button cancelButton;

    public void setCancelButton(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void setLogInButton(ActionEvent actionEvent) {
        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            validateCredentials();
        } else {
            errorpop.setText("Please enter Valid Credentials");
        }
    }

    public void validateCredentials() {

        databaseConnector connectNow = new databaseConnector();
        Connection connectDB = connectNow.getDatabaselink();//kaya possible fault//nope just alright

        String verifyLogin = "SELECT count(1) FROM `application_users` WHERE username = '" + usernameField.getText() + "' AND password = '" + passwordField.getText() + "'";

        //this will check if the entered credentials are for an admin
        //this will be removed in future
        if (usernameField.getText().equals("Admin") && passwordField.getText().equals("Admin")) {

            //close the current stage
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

            //load dashboard
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Dashboard.fxml")));
                //System.out.println("it works"); sometimes null maybe returned so this is just to try
                Stage registerStage = new Stage();
                registerStage.initStyle(StageStyle.UNDECORATED);
                registerStage.setScene(new Scene(root, 1027, 647));
                registerStage.show();

            } catch (IOException e) {
                e.printStackTrace();
                e.getCause();
            }
            //if not admin the system will then check if the entered credentials are of a user
        } else {
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        errorpop.setText("Match Found");
                        loadUserHomePage();
                    } else {
                        errorpop.setText("No Matching Credentials found");
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }

    public void loadSignUpPage(MouseEvent mouseEvent) {

        //close current stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        //load registration form
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));

            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 572, 666));
            registerStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loadUserHomePage() {
        //close the current stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        //load UserHomePage
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userHome.fxml")));
            //System.out.println("it works"); sometimes null maybe returned so this is just to try
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 1067, 662));
            registerStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void GOTOUSER(ActionEvent actionEvent) {
        usernameField.setText("White");
        passwordField.setText("4321");
        validateCredentials();
    }
}
