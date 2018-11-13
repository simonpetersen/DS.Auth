package client;

import server.IPrintServer;
import util.Strings;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class PrintClient {

    private IPrintServer server;
    private String username, password;

    public void runClient() throws Exception {
        //Console program
        Scanner sc = new Scanner(System.in);

        // Create remote connection.
        initRemoteConnection();

        //Should login at first
        print("Hello, please log in.");
        boolean authenticated;
        // Try to authenticate, until successful.
        do {
            print("User:");
            username = sc.nextLine();
            print("Password:");
            password = sc.nextLine();
            authenticated = server.authenticate(username, password);
            String msg = authenticated ? "Hello " + username : "Wrong Credentials. Please try again.";
            print(msg);
        } while(!authenticated);
        // Authentication has been successful, user can call server.
        String input;
        do {
            presentMenu();
            input = sc.nextLine();
        } while (chooseMenu(input, sc));
    }

    private void initRemoteConnection() throws RemoteException, NotBoundException, MalformedURLException {
        server = (IPrintServer) Naming.lookup(Strings.ServerURL);
    }

    private void presentMenu(){
        print("Please choose an option:");
        print("1: Add printjob");
        print("2: Print queue");
        print("3: Get status");
        print("4: Move to top of queue");
        print("5: Start printer service");
        print("6: Stop printer service");
        print("7: Restart printer service");
        print("8: Read config");
        print("9: Set config");
        print("0: Exit");
    }

    private boolean chooseMenu(String input, Scanner scanner) throws RemoteException{
        switch (input){
            case "1":
                print("State filename");
                String filename = scanner.nextLine();
                print("State printer");
                String printer = scanner.nextLine();
                addPrintJob(filename, printer);
                break;
            case "2":
                print("Printing Queue");
                printQueue();
                break;
            case "3":
                print("Status:");
                getStatus();
                break;
            case "4":
                print("Choose which file to move to top of queue. (has to bean integer)");
                try {
                    int job = Integer.parseInt(scanner.nextLine());
                    topQueue(job);
                }
                catch(Exception e){
                    print("Wrong input type. Input has to be an integer.");
                }
                break;
            case "5":
                print("Starting printer service...");
                start();
                break;
            case "6":
                print("Stopping printer service...");
                stop();
                break;
            case "7":
                print("Restarting printer service...");
                restart();
                break;
            case "8":
                print("Please select config by parameter:");
                String parameter = scanner.nextLine();
                readConfig(parameter);
                break;
            case "9":
                print("Please input config parameter:");
                String setParameter = scanner.nextLine();
                print("Please input config value:");
                String value = scanner.nextLine();
                setConfig(setParameter, value);
                break;
            case "0":
                print("Goodbye");
                return false;
            default:
                print("Wrong input type, please select one of the options.");
                break;
        }
        return true;
    }

    private void addPrintJob(String filename, String printer) throws RemoteException {
        server.print(filename, printer, this.username, this.password);
    }

    private void printQueue() throws RemoteException {
        print(server.queue(this.username, this.password));
    }

    private void getStatus() throws RemoteException {
        print(server.status(this.username, this.password));
    }

    private void topQueue(int job) throws RemoteException{
        print(server.topQueue(job, this.username, this.password));
    }

    private void start() throws RemoteException{
        print(server.start(this.username, this.password));
    }

    private void stop() throws RemoteException{
        print(server.stop(this.username, this.password));
    }

    private void restart() throws RemoteException {
        print(server.restart(this.username, this.password));
    }

    private void readConfig(String parameter) throws RemoteException {
        print(server.readConfig(parameter, this.username, this.password));
    }

    private void setConfig(String parameter, String value) throws RemoteException {
        print(server.setConfig(parameter, value, this.username, this.password));
    }

    private void print(String s) {
        System.out.println(s);
    }

    private void print(List<String> list) {
        list.stream().forEach(s -> System.out.println(s));
    }
}
