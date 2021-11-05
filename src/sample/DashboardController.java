package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    public Button ViewandManageUsers;
    public Button UserStatsandGraphs;
    public Button administrativeSettings;
    public StackPane stackPane;
    public Button exitButton;
    public StackPane vMU_STACKPANE;
    public Button displayUsersButton;
    public Button addUsersManually;
    public Button addthroughCSV;
    public Button exportButton;
    public TextField csvPath;
    public Button browseButton;
    public Label messageBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadViewandManageUsers(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewAndManageUsers.fxml")));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(fxml);
    }

    public void loadUserStatsandGraphs(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserStatsandGraphs.fxml")));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(fxml);
    }

    public void loadAdministrativeSettings(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("administrativeSettings.fxml")));
        stackPane.getChildren().removeAll();
        stackPane.getChildren().setAll(fxml);
    }

    public void setCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void displayUsersAction(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("displayusers.fxml")));
        vMU_STACKPANE.getChildren().removeAll();
        vMU_STACKPANE.getChildren().setAll(fxml);
    }

    public void addthroughCSVACtion(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddUsersCSV.fxml")));
        vMU_STACKPANE.getChildren().removeAll();
        vMU_STACKPANE.getChildren().setAll(fxml);
    }

    public void addUsersManuallyButton(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddUsersManually.fxml")));
        vMU_STACKPANE.getChildren().removeAll();
        vMU_STACKPANE.getChildren().setAll(fxml);
    }

    public void loadExportPage(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Export.fxml")));
        vMU_STACKPANE.getChildren().removeAll();
        vMU_STACKPANE.getChildren().setAll(fxml);
    }

    public void browseAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        String filePath = file.getAbsolutePath();
        csvPath.setText(filePath);
    }

    public void exportAction(ActionEvent actionEvent) {
        databaseConnector connector = new databaseConnector();
        Connection connection = connector.getDatabaselink();

        try {
            PrintWriter pw = new PrintWriter(new File("(C:\\Users\\Manfred Chirambo\\Desktop\\New Text Document.csv"));
            StringBuilder sb = new StringBuilder();

            ResultSet rs = null;

            String query = "select * from application_users";
            PreparedStatement ps = connection.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs.next()) {
                sb.append(rs.getString("username"));
                sb.append(",");
                sb.append(rs.getString("location"));
                sb.append(",");
                sb.append(rs.getString("phonenumber"));
                sb.append(",");
                sb.append(rs.getString("job"));
                sb.append(",");
                sb.append(rs.getString("password"));
                sb.append("\r\n");
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("finished");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}


