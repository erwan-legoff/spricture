package fr.erwil.Spricture.Configuration.Security.Utils;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtils {

    private static final int SALT_LENGTH = 16;

    public static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String convertBytesToBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
