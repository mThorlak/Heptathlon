package client_package;

import SiegeServerPackage.MainSiegeServer;
import rmi_siege.QuerySiegeInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientSiege {

    private final QuerySiegeInterface querySiegeInterface;

    public ClientSiege() throws RemoteException, NotBoundException {
        MainSiegeServer siegeServer = new MainSiegeServer();
        Registry registrySiege = LocateRegistry.getRegistry(siegeServer.getPort());
        this.querySiegeInterface = (QuerySiegeInterface) registrySiege.lookup("articleSiege");
    }

    public QuerySiegeInterface getQuerySiegeInterface() {
        return querySiegeInterface;
    }

}
