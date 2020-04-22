package client_package;

import SiegeServerPackage.MainSiegeServer;
import rmi_general.Bill;
import rmi_general.CSVManager;
import rmi_shop.QueryShopInterface;
import rmi_shop.tables.Article;
import rmi_siege.QuerySiegeInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


public class MainClientSiege {
    public static void main(String[] args)throws Exception {
        try {

            MainSiegeServer siegeServer = new MainSiegeServer();
            Registry registrySiege = LocateRegistry.getRegistry(siegeServer.getPort());

            // Looking up the registry for the remote object
            QuerySiegeInterface querySiegeInterface = (QuerySiegeInterface) registrySiege.lookup("articleSiege");

            querySiegeInterface.insertNewReference("Longboard","L00010");
            querySiegeInterface.insertNewArticle("L00010", 50, 200, null);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}