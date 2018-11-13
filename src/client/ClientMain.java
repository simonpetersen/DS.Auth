package client;

import server.AuthenticationService;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        new PrintClient().runClient();
    }
}
