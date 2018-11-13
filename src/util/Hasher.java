package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hasher {

    public static String hash(String value, String salt) throws NoSuchAlgorithmException {
        byte[] saltBytes = hexStringToByteArray(salt);

        //append saltvalue to value bytearray
        byte[] valueByte = value.getBytes(StandardCharsets.UTF_8);
        byte[] saltetValue = new byte[valueByte.length + saltBytes.length];
        System.arraycopy(valueByte, 0, saltetValue, 0, valueByte.length);
        System.arraycopy(saltBytes, 0, saltetValue, valueByte.length, saltBytes.length);

        //Computes hashvalue of saltetValue
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(saltetValue);

        //Converts hashed password to hex-format for comparison purposes
        return bytesToString(encodedhash);

    }

    //Used to create salt values for new users
    public static String createSalt() throws NoSuchAlgorithmException{
        //Computes bytes of random saltValue
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte salt[] = new byte[20];
        random.nextBytes(salt);
        return bytesToString(salt);
    }

    //Converts byte arrays to strings in hex-format
    private static String bytesToString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i++){
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    //Converts hex-format string to bytearrays
    private static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] output = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            output[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return output;
    }
}
