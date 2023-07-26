package site.doggyyummy.gaebap.global.security.util;

import java.security.SecureRandom;

public class PasswordUtil {

    public static final Integer PASSWORD_LENGTH = 8;
    public static String generateRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
