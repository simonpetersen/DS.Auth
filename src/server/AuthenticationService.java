package server;

import util.Hasher;
import util.Strings;

import java.io.BufferedReader;
import java.io.FileReader;

public class AuthenticationService {

    public boolean validate(String user, String pass){
        try {
            if(user == null || pass == null)
                return false;
            BufferedReader br = new BufferedReader(new FileReader(Strings.CredentialsFilePath));
            for(String line; (line = br.readLine()) != null; ){
                String[] credentials = line.split(";");
                String userName = credentials[0].trim();
                if(userName.equals(user.trim())) {
                    String hashedPass = credentials[1].trim();
                    String salt = credentials[2].trim();
                    String HashedPassword = Hasher.hash(pass, salt);
                    if (userName.equals(user.trim()) && HashedPassword.equals(hashedPass.trim())) {
                        return true;
                    }
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
