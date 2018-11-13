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
    private  static String username, password;

    public void initRemoteConnection(String username, String password) throws RemoteException, NotBoundException, MalformedURLException {
        server = (IPrintServer) Naming.lookup(Strings.ServerURL);
        this.username = username;
        this.password = password;
    }

    public void addPrintJob(String filename, String printer) throws RemoteException {
        server.print(filename, printer, this.username, this.password);
    }

    public void printQueue() throws RemoteException {
        print(server.queue(this.username, this.password));
    }

    public void getStatus() throws RemoteException {
        print(server.status(this.username, this.password));
    }

    public void topQueue(int job) throws RemoteException{
        print(server.topQueue(job, this.username, this.password));
    }

    public void start() throws RemoteException{
        print(server.start(this.username, this.password));
    }

    public void stop() throws RemoteException{
        print(server.stop(this.username, this.password));
    }

    public void restart() throws RemoteException {
        print(server.restart(this.username, this.password));
    }

    public void readConfig(String parameter) throws RemoteException {
        print(server.readConfig(parameter, this.username, this.password));
    }

    public void setConfig(String parameter, String value) throws RemoteException {
        print(server.setConfig(parameter, value, this.username, this.password));
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void print(List<String> list) {
        list.stream().forEach(s -> System.out.println(s));
    }
}
