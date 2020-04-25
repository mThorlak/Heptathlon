package rmi_shop;

import rmi_shop.tables.Article;

import java.rmi.Remote;
import java.util.*;

public interface QueryShopInterface extends Remote {
    public Article findArticleByReference(String reference) throws Exception;
    public List<Article> getAllArticle() throws Exception;
    public List<Article> getArticleByFamily(String familyName) throws Exception;
    public void insertNewReference(String familyName, String reference) throws Exception;
    public void insertNewArticle(String reference, double price, int stock, String description, String shop) throws Exception;
    public void updateStock(String shop, String reference, int stock) throws Exception;
    public void updatePrice(String reference, double price) throws Exception;
    public void importPriceFromSiegeDB(String shop) throws Exception;
}
