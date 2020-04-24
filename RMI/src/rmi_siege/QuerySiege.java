package rmi_siege;

import rmi_general.Database;
import rmi_shop.tables.Article;
import rmi_siege.tables.ArticleSiege;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuerySiege implements QuerySiegeInterface {

    private final static String DATABASE_NAME = "Siege";

    private List<ArticleSiege> getArticles(List<ArticleSiege> list, ResultSet resultQuery) throws SQLException {
        while(resultQuery.next()) {
            // Retrieve by column name
            String reference  = resultQuery.getString("Reference");
            float price = resultQuery.getFloat("Price");
            String description = resultQuery.getString("Description");

            // Setting the values for article
            ArticleSiege article = new ArticleSiege();
            article.setReference(reference);
            article.setPrice(price);
            article.setDescription(description);

            list.add(article);
        }

        resultQuery.close();
        return list;
    }

    @Override
    public List<ArticleSiege> getAllArticle() throws Exception {

        List<ArticleSiege> list = new ArrayList<>();

        Database database = new Database(DATABASE_NAME);
        String query = "SELECT Article.Reference, Family, Price, Stock, Description FROM `Article`, `Family` WHERE Article.Reference = Family.Reference";
        ResultSet resultQuery = database.CreateAndExecuteStatement(query);

        //Extract data from result set
        return getArticles(list, resultQuery);
    }

    @Override
    public List<ArticleSiege> getArticleByFamily(String familyName) throws Exception {

        List<ArticleSiege> list = new ArrayList<>();
        Database database = new Database(DATABASE_NAME);
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
    public List<Article> getArticleByShop(String shop) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Article.Reference, Article.Price FROM Shop, Article WHERE Shop.name = ? AND Shop.Reference = Article.Reference";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, shop);
        ResultSet resultQuery = query.executeQuery();

        List<Article> articleList = new ArrayList<>();

        while(resultQuery.next()) {
            // Retrieve by column name
            String reference  = resultQuery.getString("Reference");
            float price = resultQuery.getFloat("Price");
            Article article = new Article(reference, price);
            articleList.add(article);
        }

        resultQuery.close();
        return articleList;
    }

    @Override
    public void insertNewReference(String familyName, String reference) throws Exception {

        Database database = new Database(DATABASE_NAME);

        String sql = "INSERT INTO Family(Family, Reference) VALUES (?,?)";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, familyName);
        query.setString(2, reference);
        query.executeUpdate();
    }

    @Override
    public void insertNewArticle(String reference, double price, int stock, String description) throws Exception {

        Database databaseSiege = new Database(DATABASE_NAME);

        String sqlInsertArticleSiegeDB = "INSERT INTO Article(Reference, Price, Description) VALUES (?,?,?)";
        PreparedStatement queryArticleSiegeDB = databaseSiege.getConnection().prepareStatement(sqlInsertArticleSiegeDB);
        queryArticleSiegeDB.setString(1, reference);
        queryArticleSiegeDB.setDouble(2, price);
        queryArticleSiegeDB.setString(3, description);
        queryArticleSiegeDB.executeUpdate();
    }

    @Override
    public void updatePrice(String reference, double price) throws Exception {

        Database database = new Database(DATABASE_NAME);

        String sql = "UPDATE Article SET Price=? WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setDouble(1, price);
        query.setString(2, reference);
        query.executeUpdate();
    }

}
