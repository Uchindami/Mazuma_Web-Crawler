package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class userHomeController implements Initializable {

    @FXML
    public AnchorPane anchor;
    public Pane carpane;
    public Label username;
    public Label newArrivalsButton;
    public Label searchButton;
    public Label settingsButton;
    public StackPane sceneSwitch;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void loadViewScene(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserViewNewScene.fxml")));
        sceneSwitch.getChildren().removeAll();
        sceneSwitch.getChildren().setAll(fxml);
    }

    public void loadSearchScene(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserSearchandCompare.fxml")));
        sceneSwitch.getChildren().removeAll();
        sceneSwitch.getChildren().setAll(fxml);
    }

    public void loadSettingsScene(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserSettingsScene.fxml")));
        sceneSwitch.getChildren().removeAll();
        sceneSwitch.getChildren().setAll(fxml);
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
