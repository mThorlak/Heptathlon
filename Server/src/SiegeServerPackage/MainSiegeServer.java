package SiegeServerPackage;

import rmi_siege.QuerySiege;
import rmi_siege.QuerySiegeInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainSiegeServer extends QuerySiege {

    private static final int PORT_SIEGE = 1100;

    public static void main(String[] args) {
        try {
            // Instantiating the implementation class
            QuerySiege obj = new QuerySiege();

            // Exporting the object of implementation class (
            // here we are exporting the remote object to the stub)
            QuerySiegeInterface stub = (QuerySiegeInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(PORT_SIEGE);

            registry.bind("articleSiege", stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public int getPort() {
        return PORT_SIEGE;
    }
}
