package rmi_shop;

import rmi_shop.tables.Article;
import rmi_general.Database;
import rmi_siege.QuerySiege;
import rmi_siege.tables.ArticleSiege;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class QueryShop implements QueryShopInterface {

    private final static String DATABASE_SHOP = "Shop";
    private final static String DATABASE_SIEGE = "Siege";

    private List<Article> getArticles(List<Article> list, ResultSet resultQuery) throws SQLException {
        while (resultQuery.next()) {
            // Retrieve by column name
            String reference = resultQuery.getString("Reference");
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
    public Article findArticleByReference(String reference) throws Exception {

        Database database = new Database(DATABASE_SIEGE);
        String sql = "SELECT * FROM Article WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, reference);

        ResultSet resultQuery = query.getResultSet();
        System.out.println("resultquery");
        System.out.println(resultQuery);

        Article article = new Article();

        if (isEmpty(resultQuery))
            return null;

        else {
            article.setReference(resultQuery.getString("Reference"));
            article.setPrice(resultQuery.getFloat("Price"));
        }
        query.close();
        return article;

    }

    @Override
    public List<Article> getAllArticle() throws Exception {

        List<Article> list = new ArrayList<>();

        Database database = new Database(DATABASE_SHOP);
        String query = "SELECT Article.Reference, Family, Price, Stock, Description FROM `Article`, `Family` WHERE Article.Reference = Family.Reference";
        ResultSet resultQuery = database.CreateAndExecuteStatement(query);

        //Extract data from result set
        return getArticles(list, resultQuery);
    }

    @Override
    public List<Article> getArticleByFamily(String familyName) throws Exception {

        List<Article> list = new ArrayList<>();
        Database database = new Database(DATABASE_SHOP);
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

        Database databaseShop = new Database(DATABASE_SHOP);
        Database databaseSiege = new Database(DATABASE_SIEGE);

        boolean referenceAlreadyExistInDBSiege = false;
        QuerySiege querySiegeGetReference = new QuerySiege();
        List<ArticleSiege> articleSiege = querySiegeGetReference.getArticleByFamily(familyName);

        for (ArticleSiege article : articleSiege) {
            if (article.getReference().equals(reference)) {
                referenceAlreadyExistInDBSiege = true;
                break;
            }
        }

        String sql = "INSERT INTO Family(Family, Reference) VALUES (?,?)";

        if (!referenceAlreadyExistInDBSiege) {
            PreparedStatement querySiege = databaseSiege.getConnection().prepareStatement(sql);
            querySiege.setString(1, familyName);
            querySiege.setString(2, reference);
            querySiege.executeUpdate();
        }

        PreparedStatement queryShop = databaseShop.getConnection().prepareStatement(sql);
        queryShop.setString(1, familyName);
        queryShop.setString(2, reference);
        queryShop.executeUpdate();
    }

    @Override
    public void insertNewArticle(String reference, double price, int stock, String description, String shop)
            throws Exception {

        Database databaseShop = new Database(DATABASE_SHOP);
        Database databaseSiege = new Database(DATABASE_SIEGE);

        boolean articleAlreadyExistInDBSiege = false;
        Article article1 = findArticleByReference(reference);

        if (!isNull(article1)) {
            articleAlreadyExistInDBSiege = true;
        }

        if (!articleAlreadyExistInDBSiege) {
            QuerySiege querySiege = new QuerySiege();
            querySiege.insertNewArticle(reference, price, description);

            String sqlInsertShopSiegeDB = "INSERT INTO Shop(Name, Reference, Stock) VALUES (?,?,?)";
            PreparedStatement queryShopSiegeDB = databaseSiege.getConnection().prepareStatement(sqlInsertShopSiegeDB);
            queryShopSiegeDB.setString(1, shop);
            queryShopSiegeDB.setString(2, reference);
            queryShopSiegeDB.setInt(3, stock);
            queryShopSiegeDB.executeUpdate();
        }

        String sqlInsertShopDB = "INSERT INTO Article(Reference, Price, Stock, Description) VALUES (?,?,?,?)";
        PreparedStatement queryShop = databaseShop.getConnection().prepareStatement(sqlInsertShopDB);
        queryShop.setString(1, reference);
        queryShop.setDouble(2, price);
        queryShop.setInt(3, stock);
        queryShop.setString(4, description);
        queryShop.executeUpdate();
    }

    @Override
    public void updateStock(String shop, String reference, int stock) throws Exception {

        Database databaseShop = new Database(DATABASE_SHOP);
        Database databaseSiege = new Database(DATABASE_SIEGE);

        String sqlUpdateStockShop = "UPDATE Article SET Stock=? WHERE Reference = ?";
        PreparedStatement queryShop = databaseShop.getConnection().prepareStatement(sqlUpdateStockShop);
        queryShop.setInt(1, stock);
        queryShop.setString(2, reference);
        queryShop.executeUpdate();

        String sqlUpdateStockSiege = "UPDATE Shop SET Stock = ? WHERE Name = ? AND Reference = ?";
        PreparedStatement querySiege = databaseSiege.getConnection().prepareStatement(sqlUpdateStockSiege);
        querySiege.setInt(1, stock);
        querySiege.setString(2, shop);
        querySiege.setString(3, reference);
        querySiege.executeUpdate();

    }

    @Override
    public void updatePrice(String reference, double price) throws Exception {

        Database database = new Database(DATABASE_SHOP);

        String sql = "UPDATE Article SET Price = ? WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setDouble(1, price);
        query.setString(2, reference);
        query.executeUpdate();
    }

    @Override
    public void importPriceFromSiegeDB(String shop) throws Exception {

        Database database = new Database(DATABASE_SHOP);
        QuerySiege querySiege = new QuerySiege();
        List<Article> articleList = querySiege.getArticleByShop(shop);

        String sql = "UPDATE Article SET Price = ? WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);

        for (Article article : articleList) {
            query.setDouble(1, article.getPrice());
            query.setString(2, article.getReference());
            query.executeUpdate();
        }
    }

}
