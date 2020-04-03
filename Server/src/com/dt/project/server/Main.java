package com.dt.project.server;

import com.dt.project.api.LuckyNumber;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        LuckyNumberImpl luckyNumberImpl = new LuckyNumberImpl();
        LuckyNumber luckyNumber = (LuckyNumber) UnicastRemoteObject.exportObject(luckyNumberImpl, 0);
        registry.rebind("lucky", luckyNumber);
        System.out.println("Server is running");
    }
}
