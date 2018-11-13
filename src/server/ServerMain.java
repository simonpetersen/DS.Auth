package server;

import util.Strings;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.*;

public class ServerMain {

    public static Logger logger;
    static Handler fileHandler;
    static Formatter simpleFormatter;

    public static void main(String[] args) throws RemoteException, MalformedURLException, IOException {
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        startLogging();
        PrintServer server = new PrintServer(logger);
        Naming.rebind(Strings.ServerURL, server);
        System.out.println("Print Server is now running.");
    }

    public static void startLogging() throws IOException{
        logger = Logger.getLogger(PrintServer.class.getName());
        fileHandler = new FileHandler(Strings.logLocation);
        simpleFormatter = new SimpleFormatter();
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(simpleFormatter);
        fileHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
    }
}
