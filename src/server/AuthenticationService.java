package server;

import util.Strings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthenticationService {

    public static boolean Validate(String user, String pass){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Strings.Credentials));
            for(String line; (line = br.readLine()) != null; ){
                String[] credentials = line.split(";");
                if (credentials[0].trim().equals(user.trim()) && credentials[1].trim().equals(pass.trim())) {
                    System.out.println("User Authenticated");
                    return true;
                }
            }
        }
        catch(IOException e){
            //File does not exist
            e.printStackTrace();
        }
        System.out.println("User not found");
        return false;
    }
}
