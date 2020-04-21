package client_package;

import rmi_package.Article;
import rmi_package.QueryShopInterface;
import rmi_package.CSVManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


public class Main_client {
    public static void main(String[] args)throws Exception {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object
            QueryShopInterface queryShopInterface = (QueryShopInterface) registry.lookup("article");

            // Calling the remote method using the obtained object
            // List<Article> list = (List)stub.getAllArticle();
            List<Article> list = (List) queryShopInterface.getArticleByFamily("Rugby");
            for (Article a:list) {

                // System.out.println("bc "+s.getBranch());
                System.out.println("Reference: " + a.getReference());
                System.out.println("Price: " + a.getPrice());
                System.out.println("Stock: " + a.getStock());
                System.out.println("Description: " + a.getDescription());
            }

            //CSVManager csvManager = new CSVManager();
            //csvManager.OpenCSVReaderLineByLine();

            //queryInterface.insertNewReference("Longboard","L00001");
            //queryInterface.insertNewArticle("L00001", 40, 50, "Roues orangatang");
            //queryInterface.updateStock("L00001", 130);
            //queryInterface.updatePrice("L00001", 150);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
