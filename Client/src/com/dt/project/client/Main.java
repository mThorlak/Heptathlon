package com.dt.project.client;

import com.dt.project.api.LuckyNumber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);

        LuckyNumber luckyNumber = (LuckyNumber) registry.lookup("lucky");

        Scanner input = new Scanner(System.in);

        String again = "";

        while (!again.equalsIgnoreCase("no")) {
            System.out.println("Please input the lucky number ???");
            String response = luckyNumber.process(input.nextLine());
            System.out.println(response);

            System.out.println("\nInput again ???");
            again = input.nextLine();
        }
    }
}
