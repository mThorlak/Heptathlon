package rmi_siege;

import rmi_siege.tables.ArticleSiege;

import java.rmi.Remote;
import java.util.List;

public interface QuerySiegeInterface extends Remote {
    public List<ArticleSiege> getAllArticle() throws Exception;
    public List<ArticleSiege> getArticleByFamily(String familyName) throws Exception;
    public void insertNewReference(String familyName, String reference) throws Exception;
    public void insertNewArticle(String reference, double price, int stock, String description) throws Exception;
    // public void updateStock(String reference, int stock) throws Exception;
    public void updatePrice(String reference, double price) throws Exception;
}
