package rmi_package;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Query implements QueryInterface {

    private List<Article> getArticles(List<Article> list, ResultSet resultQuery) throws SQLException {
        while(resultQuery.next()) {
            // Retrieve by column name
            String reference  = resultQuery.getString("Reference");
            float price = resultQuery.getFloat("Price");
            int stock = resultQuery.getInt("Stock");
            String description = resultQuery.getString("Description");

            // Setting the values for article
            Article article = new Article();
            article.setReference(reference);
            article.setPrice(price);
            article.setStock(stock);
            article.setDescription(description);

            list.add(article);
        }

        resultQuery.close();
        return list;
    }

    @Override
    public List<Article> getAllArticle() throws Exception {

        List<Article> list = new ArrayList<>();

        Database database = new Database();
        String query = "SELECT Article.Reference, Family, Price, Stock, Description FROM `Article`, `Family` WHERE Article.Reference = Family.Reference";
        ResultSet resultQuery = database.CreateAndExecuteStatement(query);

        //Extract data from result set
        return getArticles(list, resultQuery);
    }

    @Override
    public List<Article> getArticleByFamily(String familyName) throws Exception {

        List<Article> list = new ArrayList<>();
        Database database = new Database();
        String sql = "SELECT Article.Reference, Price, Stock, Description " +
                "FROM Article, Family " +
                "WHERE Family.Family = ? " +
                "GROUP BY Article.Reference";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, familyName);

        ResultSet resultQuery = query.executeQuery();

        //Extract data from result set
        return getArticles(list, resultQuery);
    }

    @Override
    public void insertNewReference(String familyName, String reference) throws Exception {

        Database database = new Database();

        String sql = "INSERT INTO Family(Family, Reference) VALUES (?,?)";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, familyName);
        query.setString(2, reference);
        query.executeUpdate();
    }

    @Override
    public void insertNewArticle(String reference, double price, int stock, String description) throws Exception {

        Database database = new Database();

        String sql = "INSERT INTO Article(Reference, Price, Stock, Description) VALUES (?,?,?,?)";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, reference);
        query.setDouble(2, price);
        query.setInt(3, stock);
        query.setString(4, description);
        query.executeUpdate();
    }

    @Override
    public void updateStock(String reference, int stock) throws Exception {

        Database database = new Database();

        String sql = "UPDATE Article SET Stock=? WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setInt(1, stock);
        query.setString(2, reference);
        query.executeUpdate();
    }

    @Override
    public void updatePrice(String reference, double price) throws Exception {

        Database database = new Database();

        String sql = "UPDATE Article SET Price=? WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setDouble(1, price);
        query.setString(2, reference);
        query.executeUpdate();
    }
}
