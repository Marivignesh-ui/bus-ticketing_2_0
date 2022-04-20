package com.mari.bus_ticketing2.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {

    public static String hash(char[] sensitiveData) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        Base64.Encoder encoder = Base64.getEncoder();
        String hash = encoder.encodeToString(
                        digest.digest(
                        new String(sensitiveData).getBytes(StandardCharsets.UTF_8)));
        return hash;
    }
    
}
