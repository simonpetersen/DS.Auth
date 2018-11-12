package client;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        PrintClient client = new PrintClient();
        client.initRemoteConnection();
        client.addPrintJob("File1", "Printer-1");
        client.addPrintJob("File2", "Printer-2");
        //client.printQueue();
    }
}
