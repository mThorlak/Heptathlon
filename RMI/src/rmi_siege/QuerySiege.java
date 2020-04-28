package rmi_siege;

import rmi_general.Bill;
import rmi_general.CSVManager;
import rmi_general.Database;
import rmi_shop.tables.Article;
import rmi_siege.tables.ArticleSiege;

import java.io.FileNotFoundException;
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
        String sql = "SELECT Article.Reference, Price, Description " +
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
            int stock = resultQuery.getInt("Stock");
            Article article = new Article(reference, price, stock);
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
    public void insertNewArticle(String reference, double price, String description) throws Exception {

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

    @Override
    public void importCSVIntoDBSiege() throws SQLException, ClassNotFoundException, FileNotFoundException {

        Database databaseSiege = new Database(DATABASE_NAME);

        CSVManager csvManager = new CSVManager();
        List<String[]> CSVBill = csvManager.readLineByLine(csvManager.getBillPath());
        int cpt = 0;

        String sqlInsertIntoBill = "INSERT INTO Bill(IDBill, Shop, Date, Total, Payment, Paid) VALUES (?,?,?,?,?,?)";
        String sqlInsertIntoBillDetails = "INSERT INTO Bill_Details(IDBill, Reference, Quantity, Price) VALUES (?,?,?,?)";
        PreparedStatement queryInsertIntoBill = databaseSiege.getConnection().prepareStatement(sqlInsertIntoBill);
        PreparedStatement queryInsertIntoBillDetails = databaseSiege.getConnection().prepareStatement(sqlInsertIntoBillDetails);

        for (String[] line : CSVBill) {
            if (cpt == 0) {
                cpt++;
                continue;
            }
            Bill bill = csvManager.convertLineInBill(line);
            queryInsertIntoBill.setString(1, bill.getId());
            queryInsertIntoBill.setString(2, bill.getShop());
            queryInsertIntoBill.setString(3, bill.getDate());
            queryInsertIntoBill.setFloat(4, bill.getTotal());
            queryInsertIntoBill.setString(5, bill.getPayment());
            queryInsertIntoBill.setBoolean(6, false);
            queryInsertIntoBill.executeUpdate();

            List<Article> articles = bill.getArticles();
            for (Article article : articles) {
                queryInsertIntoBillDetails.setString(1, bill.getId());
                queryInsertIntoBillDetails.setString(2, article.getReference());
                queryInsertIntoBillDetails.setInt(3, article.getStock());
                queryInsertIntoBillDetails.setFloat(4, article.getPrice());
                queryInsertIntoBillDetails.executeUpdate();
            }
        }
        queryInsertIntoBill.close();
        queryInsertIntoBillDetails.close();
    }

}
