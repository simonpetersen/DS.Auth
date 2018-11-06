package server;

import java.util.List;
import java.rmi.RemoteException;

public interface IPrintServer extends java.rmi.Remote {
    void print(String filename, String printer) throws RemoteException;   // prints file filename on the specified printer
    List<String> queue() throws RemoteException;   // lists the print queue on the user's display in lines of the form <job number>   <file name>
    void topQueue(int job) throws RemoteException;   // moves job to the top of the queue
    String start() throws RemoteException;   // starts the print server
    String stop() throws RemoteException;   // stops the print server
    String restart() throws RemoteException;   // stops the print server, clears the print queue and starts the print server again
    String status() throws RemoteException;  // prints status of printer on the user's display
    String readConfig(String parameter) throws RemoteException;   // prints the value of the parameter on the user's display
    String setConfig(String parameter, String value) throws RemoteException;   // sets the parameter to value
}
