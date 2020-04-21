package rmi_package;

import java.sql.*;

public class Database {

    private Connection connection = null;
    private Statement statement = null;

    public Database () throws ClassNotFoundException, SQLException {

        String jdbc_drive = "com.mysql.cj.jdbc.Driver";
        String db_url = "jdbc:mysql://localhost:3306/Heptathlon_Shop?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
        String user = "admin";
        String password = "root";

        Class.forName(jdbc_drive);

        //Open a connection
        System.out.println("Connecting to Heptathlon_Shop database...");
        // Database credentials
        this.connection = DriverManager.getConnection(db_url, user, password);
        System.out.println("Connected successfully !");
    }

    public ResultSet CreateAndExecuteStatement(String query) throws SQLException {
        this.statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}
