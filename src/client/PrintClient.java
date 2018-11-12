package client;

import server.IPrintServer;
import util.Strings;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class PrintClient {

    private IPrintServer server;
    private String username, password;

    public void initRemoteConnection() throws RemoteException, NotBoundException, MalformedURLException {
        server = (IPrintServer) Naming.lookup(Strings.ServerURL);
        username = "simon";
        password = "abcd";
    }

    public void addPrintJob(String filename, String printer) throws RemoteException {
        server.print(filename, printer, "", "");
    }

    public void printQueue() throws RemoteException {
        print(server.queue(username, password));
    }

    public void getStatus() throws RemoteException {
        print(server.status(username, password));
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void print(List<String> list) {
        list.stream().forEach(s -> System.out.println(s));
    }
}
