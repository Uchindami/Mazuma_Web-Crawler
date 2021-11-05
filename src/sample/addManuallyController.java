package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class addManuallyController implements Initializable {

    public Button addUserButton;
    public Button updateUser;
    public Button deleteUser;
    public TextField passwordfield;
    public TextField usernameField;
    public TextField locationfield;
    public TextField phonenumberField;
    public TextField jobField;
    public Label messageBox;
    public ImageView refreshButton;
    public TextField updateIDField;
    public Button updatecsv;
    public TextField csvdirecory;
    public Button browseFilesButton;
    public Button updateButton;


    @FXML
    private TableView<Users> Table;

    @FXML
    private TableColumn<Users, Integer> idColumn;

    @FXML
    private TableColumn<Users, String> usernameColumn;

    @FXML
    private TableColumn<Users, String> locationColumn;

    @FXML
    private TableColumn<Users, String> phoneNumbercolumn;

    @FXML
    private TableColumn<Users, String> jobColumn;

    @FXML
    private TableColumn<Users, String> passwordColumn;


    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bussinessmangementdatabase", "root", "");
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    public ObservableList<Users> getUsers() {

        ObservableList<Users> userslist = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String selectQuery = "SELECT * FROM application_users";

        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectQuery);
            Users usersOb;

            while (resultSet.next()) {
                usersOb = new Users(resultSet.getInt("id"),
                        resultSet.getString("username"), resultSet.getString("location"),
                        resultSet.getString("phonenumber"), resultSet.getString("job"),
                        resultSet.getString("password"));
                userslist.add(usersOb);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userslist;
    }

    public void showUsers() {
        ObservableList<Users> userList = getUsers();

        idColumn.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("location"));
        phoneNumbercolumn.setCellValueFactory(new PropertyValueFactory<Users, String>("phoneNumber"));
        jobColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("job"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("password"));

        Table.setItems(userList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsers();
    }

    public void addUserAction(ActionEvent actionEvent) {
        databaseConnector connector = new databaseConnector();
        Connection connection = connector.getDatabaselink();

        String username = usernameField.getText();
        String phoneNumber = phonenumberField.getText();
        String jobs = jobField.getText();
        String adress = locationfield.getText();
        String password = passwordfield.getText();

        String insertFields = "INSERT INTO `application_users`(`id`, `username`, `location`, `phonenumber`, `job`, `password`) VALUES ('";
        String insertValues = 0 + "','" + username + "','" + adress + "','" + phoneNumber + "','" + jobs + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connector.databaselink.createStatement();
            statement.executeUpdate(insertToRegister);
            messageBox.setText("user added successfully");
            refreshPageMeathod();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void refreshPageMeathod() {
        showUsers();
        usernameField.setText("");
        phonenumberField.setText("");
        locationfield.setText("");
        jobField.setText("");
        messageBox.setText("");
        passwordfield.setText("");
    }

    public void updateUserAction(ActionEvent actionEvent) throws SQLException {
        databaseConnector connector = new databaseConnector();
        Connection connection = connector.getDatabaselink();

        PreparedStatement pst = connection.prepareStatement("UPDATE `application_users` SET `username`=?,`location`=?,`phonenumber`=?,`job`=?,`password`=? WHERE id='" + updateIDField.getText() + "'");

        pst.setString(1, usernameField.getText());
        pst.setString(2, locationfield.getText());
        pst.setString(3, phonenumberField.getText());
        pst.setString(4, jobField.getText());
        pst.setString(5, passwordfield.getText());

        pst.executeUpdate();
        messageBox.setText("update success");
        refreshPageMeathod();
    }

    public void deleteUserAction(ActionEvent actionEvent) {
        databaseConnector connector = new databaseConnector();
        Connection connection = connector.getDatabaselink();

        String query = "DELETE FROM `application_users` WHERE id = '" + updateIDField.getText() + "'";

        try {
            Statement statement = connector.databaselink.createStatement();
            statement.executeUpdate(query);
            refreshPageMeathod();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void refreshPage(MouseEvent mouseEvent) throws IOException {
        refreshPageMeathod();
    }

    //add users to to database from csv file
    public void updatecsv(ActionEvent actionEvent) {
        databaseConnector connector = new databaseConnector();
        Connection connection = connector.getDatabaselink();

        //String csvFile = "src\\AddUsers.csv";
        String filePath = csvdirecory.getText();

        //the main guy
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String data;

            while ((data = bufferedReader.readLine()) != null) {
                String[] value = data.split(",");
                try {
                    //the sql
                    PreparedStatement pst = connection.prepareStatement("INSERT INTO `application_users`(`username`, `location`, `phonenumber`, `job`, `password`) VALUES (?,?,?,?,?)");

                    pst.setString(1, value[0]);
                    pst.setString(2, value[1]);
                    pst.setString(3, value[2]);
                    pst.setString(4, value[3]);
                    pst.setString(5, value[4]);

                    pst.executeUpdate();
                    showUsers();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void browseFilesAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        String filePath = file.getAbsolutePath();
        csvdirecory.setText(filePath);
    }

    public void recordSearch(String searchKey) {

    }
}

