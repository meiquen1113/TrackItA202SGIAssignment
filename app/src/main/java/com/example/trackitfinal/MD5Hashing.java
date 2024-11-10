package com.example.trackitfinal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


// Simple MD5 Hashing Algorithm for password used in Expense Tracker.
public class MD5Hashing {
    // function to hash the input string and return a MD5 hash string.
    public  String hashPass(String passwordToHash)
    {
        String generatedPassword = null;

        try
        {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Add password bytes to digest
            md.update(passwordToHash.getBytes());
            // Get the hash's bytes
            byte[] bytes = md.digest();
            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
