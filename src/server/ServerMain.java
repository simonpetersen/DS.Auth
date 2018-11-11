package server;

import util.Strings;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ServerMain {

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        java.rmi.registry.LocateRegistry.createRegistry(1099);

        PrintServer server = new PrintServer();
        Naming.rebind(Strings.ServerURL, server);
        System.out.println("Print Server is now running.");
    }
}
