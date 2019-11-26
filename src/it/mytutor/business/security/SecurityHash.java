package it.mytutor.business.security;

import it.mytutor.domain.User;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SecurityHash {
    public static String SetHash(String password) {
        String HashValue = "";
        byte[] b = password.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(b);
            byte[] digestBytes = messageDigest.digest();
            HashValue = DatatypeConverter.printHexBinary(digestBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HashValue;
    }

    public static Boolean equals(String password, User user) {
        String passwordHash = SecurityHash.SetHash(password);
        return user.getPassword().equals(passwordHash);
    }


}
