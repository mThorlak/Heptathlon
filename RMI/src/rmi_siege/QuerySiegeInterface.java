package rmi_siege;

import rmi_shop.tables.Article;
import rmi_siege.tables.ArticleSiege;

import java.rmi.Remote;
import java.util.List;

public interface QuerySiegeInterface extends Remote {
    public List<ArticleSiege> getAllArticle() throws Exception;
    public List<ArticleSiege> getArticleByFamily(String familyName) throws Exception;
    public List<ArticleSiege> getArticleByShop(String shop) throws Exception;
    public void insertNewReference(String familyName, String reference) throws Exception;
    public void insertNewArticle(String reference, double price, String description) throws Exception;
    public void updatePrice(String reference, double price) throws Exception;
    public void importCSVIntoDBSiege(boolean isBillPaid) throws Exception;
    public void updateBillIsPaid(String IDBill) throws Exception;
}
