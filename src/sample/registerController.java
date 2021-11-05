package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class registerController implements Initializable {

    @FXML
    public Button exitButton;
    public Button submitButton;
    @FXML
    public TextField usernameField;

    public TextField phoneNumberField;
    public TextField adrressField;
    public TextField jobsField;
    public Label messageBox;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public ImageView clearScreenButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exitButton(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void clearScreenAction(MouseEvent mouseEvent) {
        usernameField.setText(" ");
        phoneNumberField.setText(" ");
        adrressField.setText(" ");
        jobsField.setText(" ");
        messageBox.setText(" ");
        passwordField.setText(" ");
        confirmPasswordField.setText(" ");
    }

    public void submitForm(ActionEvent actionEvent) {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
        } else {
            messageBox.setText("Password does not match");
        }
    }


    public void registerUser() {
        databaseConnector connector = new databaseConnector();
        Connection connection = connector.getDatabaselink();

        String username = usernameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String jobs = jobsField.getText();
        String adress = adrressField.getText();
        String password = passwordField.getText();

        String insertFields = "INSERT INTO `application_users`(`id`, `username`, `location`, `phonenumber`, `job`, `password`) VALUES ('";
        String insertValues = 0 + "','" + username + "','" + adress + "','" + phoneNumber + "','" + jobs + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connector.databaselink.createStatement();
            statement.executeUpdate(insertToRegister);
            messageBox.setText("user added successfully");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
