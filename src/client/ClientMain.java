package client;

import server.AuthenticationService;

import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        PrintClient client = new PrintClient();
        //client.initRemoteConnection("simon", "1234");
        //client.getStatus();
        //client.addPrintJob("File1", "Printer-1");
        //client.addPrintJob("File2", "Printer-2");
        //client.printQueue();
        //Console program
        Scanner sc = new Scanner(System.in);

        //Should login at first
        String user = null, password = null;
        System.out.println("Hello, please log in.");
        while(!AuthenticationService.Validate(user, password)) {
            System.out.println("User:");
            user = sc.nextLine();
            System.out.println("Password:");
            password = sc.nextLine();
            if (AuthenticationService.Validate(user, password))
                System.out.println("Hello " + user);
            else
                System.out.println("Wrong Credentials. Please try again.");
        }
        client.initRemoteConnection(user, password);
        presentMenu();
        String input = sc.nextLine();
        while (ChooseMenu(input, sc, client)) {
            presentMenu();
            input = sc.nextLine();
        }
    }

    private static void presentMenu(){
        System.out.println("Please choose an option:");
        System.out.println("1: Add printjob");
        System.out.println("2: Print queue");
        System.out.println("3: Get status");
        System.out.println("4: Move to top of queue");
        System.out.println("5: Start printer service");
        System.out.println("6: Stop printer service");
        System.out.println("7: Restart printer service");
        System.out.println("8: Read config");
        System.out.println("9: set config");
        System.out.println("0: exit");
    }

    private static boolean ChooseMenu(String input, Scanner scanner, PrintClient client) throws RemoteException{
        switch (input){
            case "1":
                System.out.println("State filename");
                String filename = scanner.nextLine();
                System.out.println("State printer");
                String printer = scanner.nextLine();
                client.addPrintJob(filename, printer);
                break;
            case "2":
                System.out.println("Printing Queue");
                client.printQueue();
                break;
            case "3":
                System.out.println("Status:");
                client.getStatus();
                break;
            case "4":
                System.out.println("Choose which file to move to top of queue. (has to bean integer)");
                try {
                    int job = Integer.parseInt(scanner.nextLine());
                    client.topQueue(job);
                }
                catch(Exception e){
                    client.print("Wrong input type. Input has to be an integer.");
                }
                break;
            case "5":
                System.out.println("Starting printer service...");
                client.start();
                break;
            case "6":
                System.out.println("Stopping printer service...");
                client.stop();
                break;
            case "7":
                System.out.println("Restarting printer service...");
                client.restart();
                break;
            case "8":
                System.out.println("Please select config by parameter:");
                String parameter = scanner.nextLine();
                client.readConfig(parameter);
                break;
            case "9":
                System.out.println("Please input config parameter:");
                String setParameter = scanner.nextLine();
                System.out.println("Please input config value:");
                String value = scanner.nextLine();
                client.setConfig(setParameter, value);
                break;
            case "0":
                System.out.println("Goodbye");
                return false;
            default:
                System.out.println("Wrong input type, please select one of the options.");
                break;
        }
        return true;
    }
}
