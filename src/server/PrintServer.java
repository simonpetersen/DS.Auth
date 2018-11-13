package server;

import util.Strings;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class PrintServer extends UnicastRemoteObject implements IPrintServer {

    private PrintService printService;
    private Logger logger;

    public PrintServer() throws RemoteException, IOException {
        super();
        printService = new PrintService();
        logger = Logger.getLogger(PrintServer.class.getName());
        Handler fileHandler = new FileHandler(Strings.logLocation);
        Formatter simpleFormatter = new SimpleFormatter();
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(simpleFormatter);
        fileHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
    }

    @Override
    public String print(String filename, String printer, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " called addPrintJob with following inputs. Filename: " + filename + " and printer: " + printer);
        return printService.addPrintJob(filename, printer);
    }

    @Override
    public List<String> queue(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            List<String> result = new ArrayList<>();
            result.add(Strings.UserNotAuthorized);
            return result;
        }
        logger.info("User: " + username + " viewed print queue");
        return printService.getPrintQueue();
    }

    @Override
    public String topQueue(int job, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " moved job: " + job + " to top of queue.");
        return printService.moveJobToTopOfQueue(job);
    }

    @Override
    public String start(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " started the printservice.");
        return printService.startPrinter();
    }

    @Override
    public String stop(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " stopped the printservice.");
        return printService.stop();
    }

    @Override
    public String restart(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " restarted the printservice.");
        return printService.restart();
    }

    @Override
    public String status(String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " viewed the status of the printservice.");
        return printService.status();
    }

    @Override
    public String readConfig(String parameter, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " read the config with parameter: " + parameter);
        return printService.readConfig(parameter);
    }

    @Override
    public String setConfig(String parameter, String value, String username, String password) {
        if (!AuthenticationService.Validate(username, password)) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " set the config with parameter: " + parameter + " to value: " + value);
        return printService.setConfig(parameter, value);
    }

    @Override
    public boolean authenticate(String username, String password) {
        boolean authenticated = AuthenticationService.Validate(username, password);
        if (authenticated)
            logger.info("User: " + username + " has successfully been authenticated.");
        else
            logger.warning("User: " + username + " could not be authenticated.");
        return authenticated;
    }
}
