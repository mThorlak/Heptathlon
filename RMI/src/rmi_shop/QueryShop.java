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

    /**
     * Take result set given by a request to DB shop and convert it to a list of object Article
     * @param resultQuery sql response
     * @return list of article
     * @throws SQLException exception
     */
    private List<Article> convertResultQueryIntoListArticleShop(ResultSet resultQuery) throws SQLException {

        List<Article> list = new ArrayList<>();
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

    /**
     * Request to DB shop to get all article with reference given
     * @param reference string
     * @return null if request to db return nothing, return article if it founds with reference given
     * @throws Exception exception
     */
    @Override
    public Article getArticleByReference(String reference) throws Exception {

        Database database = new Database(DATABASE_SHOP);
        String sql = "SELECT * FROM Article WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, reference);

        ResultSet resultQuery = query.executeQuery();

        Article article = new Article();

        if (isEmpty(resultQuery)) {
            return null;
        }

        else {
            while (resultQuery.next()) {
                article.setReference(resultQuery.getString("Reference"));
                article.setPrice(resultQuery.getFloat("Price"));
                article.setStock(resultQuery.getInt("Stock"));
                article.setDescription(resultQuery.getString("Description"));
            }
        }
        query.close();
        return article;
    }

    /**
     * Request to DB shop to get all article, article with  0 stock are skipped
     * @return list of article object
     * @throws Exception exception
     */
    @Override
    public List<Article> getAllArticle() throws Exception {

        Database database = new Database(DATABASE_SHOP);
        String query = "SELECT * FROM Article WHERE Stock > 0";
        ResultSet resultQuery = database.CreateAndExecuteStatement(query);

        return convertResultQueryIntoListArticleShop(resultQuery);
    }

    /**
     * Request to DB shop to get all article with family name given in param, article with 0 stock are skipped
     * @param familyName String
     * @return list of object article
     * @throws Exception exception
     */
    @Override
    public List<Article> getArticleByFamily(String familyName) throws Exception {

        Database database = new Database(DATABASE_SHOP);
        String sql = "SELECT Article.Reference, Price, Stock, Description, Family \n" +
                "FROM Article JOIN  Family \n" +
                "ON Article.Reference = Family.Reference \n" +
                "WHERE Family.Family = ? AND Stock > 0";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, familyName);

        ResultSet resultQuery = query.executeQuery();

        return convertResultQueryIntoListArticleShop(resultQuery);
    }

    /**
     * Request to DB shop to get all unique family name
     * @return list of String containing family name
     * @throws Exception exception
     */
    @Override
    public List<String> getAllFamily() throws Exception {

        Database database = new Database(DATABASE_SHOP);
        String sql = "SELECT DISTINCT Family FROM Family";

        PreparedStatement query = database.getConnection().prepareStatement(sql);
        ResultSet resultQuery = query.executeQuery();

        List<String> families = new ArrayList<>();
        while (resultQuery.next()) {
            families.add(resultQuery.getString("Family"));
        }

        return families;
    }

    /**
     * Request to DB shop to insert a new reference with params given
     * @param familyName String
     * @param reference String
     * @throws Exception exception
     */
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

    /**
     * Request to DB shop to insert new article, the reference must already existing
     * The article is also added to DB Siege to keep link between two DB and to find out from the
     * DB siege which article each shop have
     * @see #insertNewReference(String, String)
     * @param reference String
     * @param price double
     * @param stock int
     * @param description String
     * @param shop String
     * @throws Exception exception
     */
    @Override
    public void insertNewArticle(String reference, double price, int stock, String description, String shop)
            throws Exception {

        Database databaseShop = new Database(DATABASE_SHOP);
        Database databaseSiege = new Database(DATABASE_SIEGE);

        boolean articleAlreadyExistInDBSiege = false;
        QuerySiege querySiege = new QuerySiege();
        String referenceSiege = querySiege.findArticleByReferenceSiege(reference);

        if (!isNull(referenceSiege)) {
            articleAlreadyExistInDBSiege = true;
        }

        if (!articleAlreadyExistInDBSiege) {
            querySiege.insertNewArticle(reference, price, description);

            String sqlInsertShopSiegeDB = "INSERT INTO Shop(Name, Reference, Stock) VALUES (?,?,?)";
            PreparedStatement queryShopSiegeDB = databaseSiege.getConnection().prepareStatement(sqlInsertShopSiegeDB);
            queryShopSiegeDB.setString(1, shop);
            queryShopSiegeDB.setString(2, reference);
            queryShopSiegeDB.setInt(3, stock);
            queryShopSiegeDB.executeUpdate();
        }
        else {
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

    /**
     * Request to DB shop to update stock of an article
     * @param shop String
     * @param reference String
     * @param stock int
     * @throws Exception exception
     */
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

    /**
     * Update the price of article in DB shop with the price of the same article in DB siege
     * @param shop String
     * @throws Exception exception
     */
    @Override
    public void importPriceFromSiegeDB(String shop) throws Exception {

        Database database = new Database(DATABASE_SHOP);
        QuerySiege querySiege = new QuerySiege();
        List<ArticleSiege> articleList = querySiege.getArticleByShop(shop);

        String sql = "UPDATE Article SET Price = ? WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);

        for (ArticleSiege article : articleList) {
            query.setDouble(1, article.getPrice());
            query.setString(2, article.getReference());
            query.executeUpdate();
        }
    }

}
