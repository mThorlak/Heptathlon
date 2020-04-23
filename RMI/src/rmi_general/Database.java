package rmi_general;

import java.sql.*;

public class Database {

    private final Connection connection;

    public Database (String DBName) throws ClassNotFoundException, SQLException {

        String jdbc_drive = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "";
        String user = "admin";
        String password = "root";

        if (DBName.equals("Shop")) {
            dbUrl = "jdbc:mysql://localhost:3306/Heptathlon_Shop?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
            //Open a connection
            System.out.println("Connecting to Heptathlon_Shop database...");
        }
        if (DBName.equals("Siege")) {
            dbUrl = "jdbc:mysql://localhost:3306/Heptathlon_Siege?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
            //Open a connection
            System.out.println("Connecting to Heptathlon_Siege database...");
        }

        Class.forName(jdbc_drive);

        // Database credentials
        this.connection = DriverManager.getConnection(dbUrl, user, password);
        System.out.println("Connected successfully !");
    }

    public ResultSet CreateAndExecuteStatement(String query) throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public Connection getConnection() {
        return connection;
    }

}
