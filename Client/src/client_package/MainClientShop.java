package client_package;

import ShopServerPackage.MainShopServer;
import rmi_shop.tables.Article;
import rmi_general.Bill;
import rmi_general.CSVManager;
import rmi_shop.QueryShopInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


public class MainClientShop {
    public static void main(String[] args)throws Exception {
        try {
            MainShopServer shopServer = new MainShopServer();
            // Getting the registry
            Registry registryShop = LocateRegistry.getRegistry(shopServer.getPort());

            // Looking up the registry for the remote object
            QueryShopInterface queryShopInterface = (QueryShopInterface) registryShop.lookup("articleShop");
            queryShopInterface.importPriceFromSiegeDB("shop1");

            // Calling the remote method using the obtained object
             // List<Article> list = (List)queryShopInterface.getAllArticle();


            //List<Article> list = (List) queryShopInterface.getArticleByFamily("Rugby");

            /*
            System.out.println("#################################################################################");
            for (Article a:list) {
                // System.out.println("bc "+s.getBranch());
                System.out.println("Reference: " + a.getReference());
                System.out.println("Price: " + a.getPrice());
                System.out.println("Stock: " + a.getStock());
                System.out.println("Description: " + a.getDescription());
                System.out.println("---------------------------------------");
            }
            System.out.println("#################################################################################");
            */

            // queryShopInterface.insertNewReference("Longboard","L00002");
            //queryShopInterface.insertNewArticle("L00002", 50, 100, "Test2", "shop1");
            //queryShopInterface.updateStock("shop1", "L00003", 200);
            //queryShopInterface.updatePrice("L00003", 50);

            /*
            list = (List)queryShopInterface.getAllArticle();

            System.out.println("#################################################################################");
            for (Article a:list) {
                System.out.println("Reference: " + a.getReference());
                System.out.println("Price: " + a.getPrice());
                System.out.println("Stock: " + a.getStock());
                System.out.println("Description: " + a.getDescription());
                System.out.println("---------------------------------------");
            }
            System.out.println("#################################################################################");
            */

            // CSVManager csvManager = new CSVManager();
            // List<String[]> billList = csvManager.readLineByLine(csvManager.getBillPath());
            // Bill bill1 = new Bill("21/04/2020", "shop1", 150, "carte bleu", list);
            // csvManager.writeNewBill(bill1, false);
            // csvManager.payBill("shop11587733041245");

            //csvManager.convertLineInBill(billList.get(5));

/*            for (String[] line:bill) {
                for (String s : line) {
                    System.out.println(s);
                }
            }*/

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
