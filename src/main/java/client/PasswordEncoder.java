package client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    public static String getHexString(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            String s;
            for (byte b : bytes) {
                s = Integer.toHexString(b);
                try {
                    builder.append(s.substring(s.length() - 2));
                } catch (IndexOutOfBoundsException e) {
                    builder.append("0").append(s);
                }
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
