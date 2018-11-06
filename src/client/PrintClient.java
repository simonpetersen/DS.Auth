package client;

import server.IPrintServer;
import util.Strings;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PrintClient {

    private IPrintServer server;

    public void initRemoteConnection() throws RemoteException, NotBoundException, MalformedURLException {
        server = (IPrintServer) Naming.lookup(Strings.ServerURL);
    }

    public void addPrintJob() throws RemoteException {
        server.print("Test1", "Printer1");
    }

    public void printQueue() throws RemoteException {
        System.out.println(server.queue());
    }
}
