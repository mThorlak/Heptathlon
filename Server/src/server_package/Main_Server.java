package server_package;

import rmi_package.Article_Implementation;
import rmi_package.Article_Manager_Interface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main_Server extends Article_Implementation {

    public static void main(String[] args) {
        try {
            // Instantiating the implementation class
            Article_Implementation obj = new Article_Implementation();

            // Exporting the object of implementation class (
            // here we are exporting the remote object to the stub)
            Article_Manager_Interface stub = (Article_Manager_Interface) UnicastRemoteObject.exportObject(obj, 0);

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
