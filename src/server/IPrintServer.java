package server;

import java.util.List;
import java.rmi.RemoteException;

public interface IPrintServer extends java.rmi.Remote {
    String print(String filename, String printer, String username, String password) throws RemoteException;   // prints file filename on the specified printer
    List<String> queue(String username, String password) throws RemoteException;   // lists the print queue on the user's display in lines of the form <job number> <file name>
    String topQueue(int job, String username, String password) throws RemoteException;   // moves job to the top of the queue
    String start(String username, String password) throws RemoteException;   // starts the print server
    String stop(String username, String password) throws RemoteException;   // stops the print server
    String restart(String username, String password) throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
    String status(String username, String password) throws RemoteException;  // prints status of printer on the user's display
    String readConfig(String parameter, String username, String password) throws RemoteException;   // prints the value of the parameter on the user's display
    String setConfig(String parameter, String value, String username, String password) throws RemoteException;   // sets the parameter to value
    boolean authenticate(String username, String password) throws RemoteException;
}
