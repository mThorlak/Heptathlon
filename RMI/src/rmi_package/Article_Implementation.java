package rmi_package;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Article_Implementation implements Article_Manager_Interface {
    @Override
    public List<Article> getArticle() throws Exception {

        List<Article> list = new ArrayList<Article>();

        // JDBC driver name and database URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/Heptathlon?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";

        // Database credentials
        String USER = "admin";
        String PASS = "root";

        Connection conn = null;
        Statement stmt = null;

        //Register JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        //Open a connection
        System.out.println("Connecting to a selected database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected database successfully...");

        //Execute a query
        System.out.println("Creating statement...");

        stmt = conn.createStatement();
        String sql = "SELECT * FROM Article";
        ResultSet rs = stmt.executeQuery(sql);

        //Extract data from result set
        while(rs.next()) {
            // Retrieve by column name
            int reference  = rs.getInt("Reference");
            float price = rs.getFloat("Price");
            int stock = rs.getInt("Stock");
            String family = rs.getString("Family");

            // Setting the values
            Article article = new Article();
            article.setReference(reference);
            article.setPrice(price);
            article.setStock(stock);
            article.setFamily(family);
            list.add(article);
        }
        rs.close();
        return list;
    }
}
