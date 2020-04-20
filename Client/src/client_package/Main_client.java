package client_package;

import rmi_package.Article;
import rmi_package.QueryInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


public class Main_client {
    public static void main(String[] args)throws Exception {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object
            QueryInterface stub = (QueryInterface) registry.lookup("article");

            // Calling the remote method using the obtained object
            List<Article> list = (List)stub.getAllArticle();
            for (Article a:list) {

                // System.out.println("bc "+s.getBranch());
                System.out.println("Reference: " + a.getReference());
                System.out.println("Price: " + a.getPrice());
                System.out.println("Stock: " + a.getStock());
                System.out.println("Description: " + a.getDescription());
            }
            // System.out.println(list);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
