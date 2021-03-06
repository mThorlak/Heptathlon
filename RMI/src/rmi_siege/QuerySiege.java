package rmi_siege;

import rmi_siege.tables.Bill;
import rmi_general.BillCSVManager;
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

    /**
     * Take result set given by a request to DB siege and convert it to a list of object ArticleSiege
     * @param resultQuery sql response
     * @return list of object ArticleSiege
     * @throws SQLException exception
     */
    private List<ArticleSiege> convertResultQueryIntoListArticleSiege(ResultSet resultQuery) throws SQLException {

        List<ArticleSiege> list = new ArrayList<>();
        while (resultQuery.next()) {
            // Retrieve by column name
            String reference = resultQuery.getString("Reference");
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

    /**
     * Request to DB siege to get all article
     * @return list of object ArticleSiege
     * @throws Exception exception
     */
    @Override
    public List<ArticleSiege> getAllArticle() throws Exception {

        Database database = new Database(DATABASE_NAME);
        String query = "SELECT * FROM Article";
        ResultSet resultQuery = database.CreateAndExecuteStatement(query);

        return convertResultQueryIntoListArticleSiege(resultQuery);
    }

    /**
     * Request to DB siege to get all article with family name given in param
     * @param familyName String
     * @return list of object ArticleSiege
     * @throws Exception exception
     */
    @Override
    public List<ArticleSiege> getArticleByFamily(String familyName) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Article.Reference, Price, Description, Family \n" +
                "FROM Article JOIN  Family \n" +
                "ON Article.Reference = Family.Reference \n" +
                "WHERE Family.Family = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, familyName);

        ResultSet resultQuery = query.executeQuery();

        //Extract data from result set
        return convertResultQueryIntoListArticleSiege(resultQuery);
    }

    /**
     * Request to DB siege to get all article with shop name given in param
     * @param shop String
     * @return list of object ArticleSiege
     * @throws Exception exception
     */
    @Override
    public List<ArticleSiege> getArticleByShop(String shop) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Article.Reference, Article.Price, Article.Description FROM Shop, Article " +
                "WHERE Shop.name = ? AND Shop.Reference = Article.Reference";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, shop);
        ResultSet resultQuery = query.executeQuery();

        return convertResultQueryIntoListArticleSiege(resultQuery);
    }

    /**
     * Request to DB siege to get all unique family name
     * @return list of String containing family name
     * @throws Exception exception
     */
    @Override
    public List<String> getAllFamily() throws Exception {

        Database database = new Database(DATABASE_NAME);
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
     * Request to DB siege to get all unique shop name
     * @return list of String containing shop name
     * @throws Exception exception
     */
    @Override
    public List<String> getAllShop() throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT DISTINCT Name FROM Shop";

        PreparedStatement query = database.getConnection().prepareStatement(sql);
        ResultSet resultQuery = query.executeQuery();

        List<String> shops = new ArrayList<>();
        while (resultQuery.next()) {
            shops.add(resultQuery.getString("Name"));
        }

        return shops;
    }

    /**
     * Check if the reference given in param match with an article existing in DB siege
     * @param reference String
     * @return String empty if no match, String containing article reference if match
     * @throws Exception exception
     */
    @Override
    public String findArticleByReferenceSiege(String reference) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT * FROM Article WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, reference);

        ResultSet resultQuery = query.executeQuery();

        ArticleSiege articleSiege = new ArticleSiege();

        while (resultQuery.next()) {
            articleSiege.setReference(resultQuery.getString("Reference"));
            articleSiege.setPrice(resultQuery.getFloat("Price"));
            articleSiege.setDescription(resultQuery.getString("Description"));
        }

        return articleSiege.getReference();
    }

    /**
     * Request to DB siege to insert a new reference with params given
     * @param familyName String
     * @param reference String
     * @throws Exception exception
     */
    @Override
    public void insertNewReference(String familyName, String reference) throws Exception {

        Database database = new Database(DATABASE_NAME);

        String sql = "INSERT INTO Family(Family, Reference) VALUES (?,?)";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, familyName);
        query.setString(2, reference);
        query.executeUpdate();
    }

    /**
     * Request to DB siege to insert new article, the reference must already existing
     * @see #insertNewReference(String, String)
     * @param reference String
     * @param price double
     * @param description String
     * @throws Exception exception
     */
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

    /**
     * Request to DB siege to update price of an article
     * @param reference String
     * @param price double
     * @throws Exception exception
     */
    @Override
    public void updatePrice(String reference, double price) throws Exception {

        Database database = new Database(DATABASE_NAME);

        String sql = "UPDATE Article SET Price=? WHERE Reference = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setDouble(1, price);
        query.setString(2, reference);
        query.executeUpdate();
    }

    /**
     * Request to DB siege to update isPaid boolean to true for a bill with reference given in parameter
     * @param IDBill String
     * @param paymentMethod String
     * @throws SQLException exception
     * @throws ClassNotFoundException exception
     */
    @Override
    public void updateBillIsPaid(String IDBill, String paymentMethod) throws SQLException, ClassNotFoundException {

        Database database = new Database(DATABASE_NAME);

        String sql = "UPDATE Bill SET Payment = ?, Paid=? WHERE IDBill = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, paymentMethod);
        query.setBoolean(2, true);
        query.setString(3, IDBill);
        query.executeUpdate();
    }

    /**
     * Request to DB siege to get a bill with ID given in param
     * @param ID String
     * @return object Bill
     * @throws Exception exception
     */
    @Override
    public Bill getBillByID(String ID) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Bill.IDBill, Shop, Date, Total, Payment, Paid, Reference, Quantity, Price " +
                "FROM Bill " +
                "JOIN Bill_Details " +
                "ON Bill.IDBill = Bill_Details.IDBill " +
                "WHERE Bill.IDBill = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);
        query.setString(1, ID);

        ResultSet resultQuery = query.executeQuery();
        List<Article> articles = new ArrayList<>();

        while (resultQuery.next()) {
            Article article = new Article();
            article.setReference(resultQuery.getString("Reference"));
            article.setPrice(resultQuery.getFloat("Price"));
            article.setStock(resultQuery.getInt("Quantity"));
            articles.add(article);
        }
        resultQuery.first();

        return new Bill(
                resultQuery.getString("Date"),
                resultQuery.getString("IDBill"),
                resultQuery.getString("Shop"),
                resultQuery.getFloat("Total"),
                resultQuery.getString("Payment"),
                articles,
                resultQuery.getBoolean("Paid"));
    }

    /**
     * Request to DB siege to get all bill
     * @return list object Bill
     * @throws Exception exception
     */
    @Override
    public List<Bill> getAllBill() throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Bill.IDBill, Shop, Date, Total, Payment, Paid, Reference, Quantity, Price " +
                "FROM Bill " +
                "JOIN Bill_Details " +
                "ON Bill.IDBill = Bill_Details.IDBill ";

        PreparedStatement query = database.getConnection().prepareStatement(sql);
        ResultSet resultQuery = query.executeQuery();

        return convertResultQueryIntoBillList(resultQuery);
    }

    /**
     * Take result set given by a request to DB siege and convert it to a list of object Bill
     * @param resultQuery sql response
     * @return list of object Bill
     * @throws SQLException exception
     */
    private List<Bill> convertResultQueryIntoBillList(ResultSet resultQuery) throws SQLException {
        List<Bill> bills = new ArrayList<>();

        while (resultQuery.next()) {

            Article article = new Article();
            article.setReference(resultQuery.getString("Reference"));
            article.setPrice(resultQuery.getFloat("Price"));
            article.setStock(resultQuery.getInt("Quantity"));

            ArrayList<Article> articles = new ArrayList<>();
            articles.add(article);

            Bill bill = new Bill(resultQuery.getString("Date"),
                    resultQuery.getString("IDBill"),
                    resultQuery.getString("Shop"),
                    resultQuery.getFloat("Total"),
                    resultQuery.getString("Payment"),
                    articles,
                    resultQuery.getBoolean("Paid"));

            if (bills.isEmpty())
                bills.add(bill);

            else if (bills.get(bills.size()-1).getId().equals(bill.getId())) {
                List<Article> tmpArticles = bills.get(bills.size()-1).getArticles();
                tmpArticles.addAll(bill.getArticles());
                bills.get(bills.size()-1).setArticles(tmpArticles);
            }

            else
                bills.add(bill);
        }
        return bills;
    }

    /**
     * Request to DB siege to get all bill with date given in param
     * @param date String
     * @return list of object Bill
     * @throws Exception exception
     */
    @Override
    public List<Bill> getBillByDate(String date) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Bill.IDBill, Shop, Date, Total, Payment, Paid, Reference, Quantity, Price " +
                "FROM Bill " +
                "JOIN Bill_Details " +
                "ON Bill.IDBill = Bill_Details.IDBill " +
                "WHERE Date = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);

        query.setString(1, date);

        ResultSet resultQuery = query.executeQuery();

        return convertResultQueryIntoBillList(resultQuery);
    }

    /**
     * Request to DB siege to get all bill with shop given in param
     * @param shop String
     * @return list of object Bill
     * @throws Exception exception
     */
    @Override
    public List<Bill> getBillByShop(String shop) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Bill.IDBill, Shop, Date, Total, Payment, Paid, Reference, Quantity, Price " +
                "FROM Bill " +
                "JOIN Bill_Details " +
                "ON Bill.IDBill = Bill_Details.IDBill " +
                "WHERE Shop = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);

        query.setString(1, shop);

        ResultSet resultQuery = query.executeQuery();

        return convertResultQueryIntoBillList(resultQuery);
    }

    /**
     * Request to DB siege to get all bill with date and shop given in param
     * @param date String
     * @param shop String
     * @return list of object Bill
     * @throws Exception exception
     */
    @Override
    public List<Bill> getBillByDateAndShop(String date, String shop) throws Exception {

        Database database = new Database(DATABASE_NAME);
        String sql = "SELECT Bill.IDBill, Shop, Date, Total, Payment, Paid, Reference, Quantity, Price " +
                "FROM Bill " +
                "JOIN Bill_Details " +
                "ON Bill.IDBill = Bill_Details.IDBill " +
                "WHERE Shop = ? AND Date = ?";
        PreparedStatement query = database.getConnection().prepareStatement(sql);

        query.setString(1, shop);
        query.setString(2, date);

        ResultSet resultQuery = query.executeQuery();

        return convertResultQueryIntoBillList(resultQuery);
    }

    /**
     * Calculate the CA of a shop with all it bill paid for date given in param
     * @param date String
     * @param shop String
     * @return CA double
     * @throws Exception exception
     */
    @Override
    public double calculateCAShopDayBillPaid(String date, String shop) throws Exception {

        List<Bill> bills = getBillByDateAndShop(date, shop);
        float total = 0;

        for (Bill bill : bills) {
            if (bill.isPaid())
                total = total + bill.getTotal();
        }

        return total;
    }

    /**
     * Calculate the CA of a shop with all it bill non paid for date given in param
     * @param date String
     * @param shop String
     * @return CA double
     * @throws Exception exception
     */
    @Override
    public double calculateCAShopDayBillNonPaid(String date, String shop) throws Exception {

        List<Bill> bills = getBillByDateAndShop(date, shop);
        float total = 0;

        for (Bill bill : bills) {
            if (!bill.isPaid())
                total = total + bill.getTotal();
        }

        return total;
    }

    /**
     * Calculate the CA of a shop with all it bill for date given in param
     * @param date String
     * @param shop String
     * @return CA double
     * @throws Exception exception
     */
    @Override
    public double calculateCAShopDayAllBill(String date, String shop) throws Exception {

        List<Bill> bills = getBillByDateAndShop(date, shop);
        float total = 0;

        for (Bill bill : bills) {
            total = total + bill.getTotal();
        }

        return total;
    }

    /**
     * Import the bill csv of a shop chosen (bill paid or non paid) into DB siege
     * @param isBillPaid Boolean
     * @throws SQLException exception
     * @throws ClassNotFoundException exception
     */
    @Override
    public void importCSVIntoDBSiege(boolean isBillPaid) throws SQLException, ClassNotFoundException {

        Database databaseSiege = new Database(DATABASE_NAME);

        BillCSVManager billCSVManager = new BillCSVManager();
        List<String[]> CSVBill;
        if (isBillPaid)
            CSVBill = billCSVManager.readLineByLine(billCSVManager.getBillPaidPath());
        else
            CSVBill = billCSVManager.readLineByLine(billCSVManager.getBillPath());
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
            Bill bill = billCSVManager.convertLineInBill(line);
            queryInsertIntoBill.setString(1, bill.getId());
            queryInsertIntoBill.setString(2, bill.getShop());
            queryInsertIntoBill.setString(3, bill.getDate());
            queryInsertIntoBill.setFloat(4, bill.getTotal());
            queryInsertIntoBill.setString(5, bill.getPayment());
            queryInsertIntoBill.setBoolean(6, isBillPaid);
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
