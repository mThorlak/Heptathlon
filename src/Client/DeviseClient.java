package Client;

import Server.DeviseRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class DeviseClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        DeviseRemote d = (DeviseRemote) Naming.lookup("convert");
        System.out.println(d.convert(1000, 0.22));
    }

}
