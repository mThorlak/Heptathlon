package rmi_shop;

import rmi_shop.tables.Article;

import java.rmi.Remote;
import java.util.*;

public interface QueryShopInterface extends Remote {
    Article getArticleByReference(String reference) throws Exception;
    List<Article> getAllArticle() throws Exception;
    List<Article> getArticleByFamily(String familyName) throws Exception;
    List<String> getAllFamily() throws Exception;
    void insertNewReference(String familyName, String reference) throws Exception;
    void insertNewArticle(String reference, double price, int stock, String description, String shop) throws Exception;
    void updateStock(String shop, String reference, int stock) throws Exception;
    void importPriceFromSiegeDB(String shop) throws Exception;
}
