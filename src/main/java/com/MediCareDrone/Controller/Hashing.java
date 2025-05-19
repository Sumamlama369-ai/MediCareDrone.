package com.MediCareDrone.Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 23048591 Suman Lama
 * Utility class for hashing passwords using SHA-256 algorithm
 */
public class Hashing {

    /**
     * Hashes a password using the SHA-256 algorithm
     *
     * @param password the plain-text password to hash
     * @return the hashed password as a hexadecimal string, or null if an error occurs
     */
    public static String hashPassword(String password) {
        try {
            // STEP 1: Initialize the SHA-256 MessageDigest
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // STEP 2: Convert the password to bytes and compute the hash
            byte[] hashBytes = md.digest(password.getBytes());

            // STEP 3: Convert the hash bytes to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            // STEP 4: Return the hashed password
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // STEP 5: Handle the case where SHA-256 is not available
            e.printStackTrace();
            return null;
        }
    }
}