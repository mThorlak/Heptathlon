package rmi_siege;

import rmi_siege.tables.ArticleSiege;

import java.rmi.Remote;
import java.util.List;

public interface QuerySiegeInterface extends Remote {
    List<ArticleSiege> getAllArticle() throws Exception;
    List<ArticleSiege> getArticleByFamily(String familyName) throws Exception;
    List<ArticleSiege> getArticleByShop(String shop) throws Exception;
    List<String> getAllFamily() throws Exception;
    List<String> getAllShop() throws Exception;
    String findArticleByReferenceSiege(String reference) throws Exception;
    void insertNewReference(String familyName, String reference) throws Exception;
    void insertNewArticle(String reference, double price, String description) throws Exception;
    void updatePrice(String reference, double price) throws Exception;
    void importCSVIntoDBSiege(boolean isBillPaid) throws Exception;
    void updateBillIsPaid(String IDBill) throws Exception;
}
