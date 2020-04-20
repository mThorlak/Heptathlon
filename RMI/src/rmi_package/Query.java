package rmi_package;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Query implements QueryInterface {
    @Override
    public List<Article> getAllArticle() throws Exception {

        List<Article> list = new ArrayList<>();

        Database database = new Database();
        String query = "SELECT Article.Reference, Family, Price, Stock, Description FROM `Article`, `Family` WHERE Article.Reference = Family.Reference";
        ResultSet resultQuery = database.CreateAndExecuteStatement(query);

        //Extract data from result set
        while(resultQuery.next()) {
            // Retrieve by column name
            String reference  = resultQuery.getString("Reference");
            float price = resultQuery.getFloat("Price");
            int stock = resultQuery.getInt("Stock");
            String description = resultQuery.getString("Description");

            String familyName = resultQuery.getString("Family");

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
}
