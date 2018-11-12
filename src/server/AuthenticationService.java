package server;

import util.Hasher;
import util.Strings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AuthenticationService {

    public static boolean Validate(String user, String pass){
        try {
            BufferedReader br = new BufferedReader(new FileReader(Strings.Credentials));
            for(String line; (line = br.readLine()) != null; ){
                String[] credentials = line.split(";");
                String userName = credentials[0].trim();
                String hashedPass = credentials[1].trim();
                String salt = credentials[2].trim();
                String HashedPassword = Hasher.hash(pass, salt);
                if (userName.equals(user.trim()) && HashedPassword.equals(hashedPass.trim())) {
                    return true;
                }
            }
        }
        catch(Exception e){
            //File does not exist or noSuchAlgorithm for hashing.
            e.printStackTrace();
        }
        return false;
    }
}
