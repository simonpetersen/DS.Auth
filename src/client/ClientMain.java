package client;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        PrintClient client = new PrintClient();
        client.initRemoteConnection();
        client.addPrintJob();
        client.printQueue();
        server.AuthenticationService.Validate("rasmus", "1234");
    }
}
