package client_package;

import ShopServerPackage.MainShopServer;
import rmi_shop.QueryShopInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ClientShop {

    private final QueryShopInterface queryShopInterface;

    public ClientShop() throws RemoteException, NotBoundException {
        MainShopServer shopServer = new MainShopServer();
        Registry registryShop = LocateRegistry.getRegistry(shopServer.getPort());
        this.queryShopInterface = (QueryShopInterface) registryShop.lookup("articleShop");
    }

    public QueryShopInterface getQueryShopInterface() {
        return queryShopInterface;
    }

}
