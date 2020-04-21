package ShopServerPackage;

import rmi_package.QueryShop;
import rmi_package.QueryShopInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainShopServer extends QueryShop {

    public static void main(String[] args) {
        try {
            // Instantiating the implementation class
            QueryShop obj = new QueryShop();

            // Exporting the object of implementation class (
            // here we are exporting the remote object to the stub)
            QueryShopInterface stub = (QueryShopInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            registry.bind("article", stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
