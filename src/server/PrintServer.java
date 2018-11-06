package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PrintServer extends UnicastRemoteObject implements IPrintServer {

    private List<String> printers;
    private List<String> prints;

    public PrintServer() throws RemoteException {
        super();
        printers = new ArrayList<>();
        prints = new ArrayList<>();
    }

    @Override
    public void print(String filename, String printer) {
        printers.add(printer);
        prints.add(filename);
    }

    @Override
    public List<String> queue() {
        List<String> queue = new ArrayList<>();
        for (int i = 0; i < prints.size(); i++) {
            String s = String.format("%s %s", i, prints.get(i));
            queue.add(s);
        }

        return queue;
    }

    @Override
    public void topQueue(int job) {

    }

    @Override
    public String start() {
        return null;
    }

    @Override
    public String stop() {
        return null;
    }

    @Override
    public String restart() {
        return null;
    }

    @Override
    public String status() {
        return null;
    }

    @Override
    public String readConfig(String parameter) {
        return null;
    }

    @Override
    public String setConfig(String parameter, String value) {
        return null;
    }
}
