package client_package;

import SiegeServerPackage.MainSiegeServer;
import rmi_siege.QuerySiegeInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClientSiege {

    private final QuerySiegeInterface querySiegeInterface;

    public MainClientSiege() throws RemoteException, NotBoundException {
        MainSiegeServer siegeServer = new MainSiegeServer();
        Registry registrySiege = LocateRegistry.getRegistry(siegeServer.getPort());
        this.querySiegeInterface = (QuerySiegeInterface) registrySiege.lookup("articleSiege");;
    }

    public QuerySiegeInterface getQuerySiegeInterface() {
        return querySiegeInterface;
    }

    public static void main(String[] args)throws Exception {
        try {

            MainSiegeServer siegeServer = new MainSiegeServer();
            Registry registrySiege = LocateRegistry.getRegistry(siegeServer.getPort());

            // Looking up the registry for the remote object
            QuerySiegeInterface querySiegeInterface = (QuerySiegeInterface) registrySiege.lookup("articleSiege");

            // querySiegeInterface.insertNewReference("Longboard","L00002");
            // querySiegeInterface.insertNewArticle("L00002", 20, 100, "Test2");
            //querySiegeInterface.updatePrice("L00003", 63.5);
            //querySiegeInterface.importCSVIntoDBSiege(false);
            querySiegeInterface.updateBillIsPaid("shop1:1588077365880");

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
