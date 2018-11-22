package server;

import util.Role;
import util.Strings;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class PrintServer extends UnicastRemoteObject implements IPrintServer {

    private PrintService printService;
    private AuthenticationService authenticationService;
    private AccessRoleService accessRoleService;
    Logger logger;

    public PrintServer(Logger log) throws RemoteException, IOException {
        super();
        this.logger = log;
        printService = new PrintService(logger);
        authenticationService = new AuthenticationService();
        accessRoleService = new AccessRoleService();
    }

    @Override
    public String print(String filename, String printer, String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Print")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " called addPrintJob with following inputs. Filename: " + filename + " and printer: " + printer);
        return printService.addPrintJob(filename, printer);
    }

    @Override
    public List<String> queue(String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Queue")) {
            List<String> result = new ArrayList<>();
            result.add(Strings.UserNotAuthorized);
            return result;
        }
        logger.info("User: " + username + " viewed print queue");
        return printService.getPrintQueue();
    }

    @Override
    public String topQueue(int job, String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "TopQueue")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " moved job: " + job + " to top of queue.");
        return printService.moveJobToTopOfQueue(job);
    }

    @Override
    public String start(String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Start")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " started the printservice.");
        return printService.startPrinter();
    }

    @Override
    public String stop(String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Stop")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " stopped the printservice.");
        return printService.stop();
    }

    @Override
    public String restart(String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Restart")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " restarted the printservice.");
        return printService.restart();
    }

    @Override
    public String status(String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Status")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " viewed the status of the printservice.");
        return printService.status();
    }

    @Override
    public String readConfig(String parameter, String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Read config")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " read the config with parameter: " + parameter);
        return printService.readConfig(parameter);
    }

    @Override
    public String setConfig(String parameter, String value, String username, String password) {
        if (!authenticationService.validate(username, password) || !checkRole(username, "Write config")) {
            return Strings.UserNotAuthorized;
        }
        logger.info("User: " + username + " set the config with parameter: " + parameter + " to value: " + value);
        return printService.setConfig(parameter, value);
    }

    @Override
    public Role authenticate(String username, String password) {
        if (!authenticationService.validate(username, password)) {
            logger.warning("User: " + username + " could not be authenticated.");
            return Role.Unknown;
        }
        logger.info("User: " + username + " has successfully been authenticated.");
        return accessRoleService.getRoleFromPassword(username);
    }

    @Override
    public boolean checkRole(String username, String job){
        Role role = accessRoleService.getRoleFromPassword(username);
        if (role == Role.Unknown)
                return false;
        switch(job){
            case "Print":
                return role != Role.Technician;
            case "Queue":
                return role != Role.Technician;
            case "Status":
                return role == Role.Admin || role == Role.Technician;
            case "TopQueue":
                return role == Role.Admin || role == Role.PowerUser;
            case "Start":
                return role == Role.Admin || role == Role.Technician;
            case "Stop":
                return role == Role.Admin || role == Role.Technician;
            case "Restart":
                return role != Role.Employee;
            case "Read config":
                return role == Role.Admin || role == Role.Technician;
            case "Write config":
                return role == Role.Admin || role == Role.Technician;
        }
        return false;
    }
}
