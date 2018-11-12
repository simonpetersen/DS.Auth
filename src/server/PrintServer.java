package server;

import util.Strings;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PrintServer extends UnicastRemoteObject implements IPrintServer {

    private PrintService printService;

    public PrintServer() throws RemoteException {
        super();
        printService = new PrintService();
    }

    @Override
    public String print(String filename, String printer, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.addPrintJob(filename, printer);
    }

    @Override
    public List<String> queue(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            List<String> result = new ArrayList<>();
            result.add(Strings.UserNotAuthorized);
            return result;
        }

        return printService.getPrintQueue();
    }

    @Override
    public String topQueue(int job, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.moveJobToTopOfQueue(job);
    }

    @Override
    public String start(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.startPrinter();
    }

    @Override
    public String stop(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.stop();
    }

    @Override
    public String restart(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.restart();
    }

    @Override
    public String status(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.status();
    }

    @Override
    public String readConfig(String parameter, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.readConfig(parameter);
    }

    @Override
    public String setConfig(String parameter, String value, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }

        return printService.setConfig(parameter, value);
    }
}
