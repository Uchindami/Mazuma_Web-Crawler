package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnector {

    public Connection databaselink;

    public Connection getDatabaselink() {
        String databaseName = "bussinessmangementdatabase";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return databaselink;
    }
}
