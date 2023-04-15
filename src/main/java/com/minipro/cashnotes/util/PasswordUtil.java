package com.minipro.cashnotes.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int COST_FACTOR = 10;

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(COST_FACTOR);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
