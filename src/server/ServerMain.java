package server;

import util.Strings;

import java.io.IOException;
import java.rmi.Naming;
import java.util.logging.*;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        Logger logger = startLogging();
        PrintServer server = new PrintServer(logger);
        Naming.rebind(Strings.ServerURL, server);
        System.out.println("Print Server is now running.");
    }

    public static Logger startLogging() throws IOException{
        Logger logger = Logger.getLogger(PrintServer.class.getName());
        Handler fileHandler = new FileHandler(Strings.LogFileLocation, true);
        Formatter simpleFormatter = new SimpleFormatter();
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(simpleFormatter);
        fileHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        return logger;
    }
}
