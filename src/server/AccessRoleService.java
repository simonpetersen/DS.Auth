package server;

import util.Role;
import util.Strings;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Logic to retrieve access role based on a username.
 */
public class AccessRoleService {

    public Role getRoleFromUsername(String username) {
        try {
            if(username == null) return Role.Unknown;
            // Read file with roles, to find username.
            BufferedReader br = new BufferedReader(new FileReader(Strings.RoleFilePath));
            for(String line; (line = br.readLine()) != null; ){
                String[] credentials = line.split(";");
                String userName = credentials[0].trim();
                if(userName.equals(username.trim())) {
                    int role = Integer.parseInt(credentials[1].trim());
                    return convertIntToRole(role);
                }
            }
        }
        catch(Exception e){
            //File does not exist.
            e.printStackTrace();
        }

        return Role.Unknown;
    }

    private Role convertIntToRole(int role) {
        switch (role) {
            case 4: return Role.Admin;
            case 3: return Role.Technician;
            case 2: return Role.PowerUser;
            case 1: return Role.Employee;
            default: return Role.Unknown;
        }
    }
}
