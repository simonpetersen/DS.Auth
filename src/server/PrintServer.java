package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PrintServer extends UnicastRemoteObject implements IPrintServer {

    private PrintService printService;

    public PrintServer() throws RemoteException {
        super();
        printService = new PrintService();
    }

    @Override
    public void print(String filename, String printer) {
        printService.addPrintJob(filename, printer);
    }

    @Override
    public List<String> queue() {
        return printService.getPrintQueue();
    }

    @Override
    public void topQueue(int job) {
        printService.moveJobToTopOfQueue(job);
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
        return printService.readConfig(parameter);
    }

    @Override
    public void setConfig(String parameter, String value) {
        printService.setConfig(parameter, value);
    }

    @Override
    public String authenticate(String username, String password) throws RemoteException {
        return printService.authenticate(username, password);
    }
}
