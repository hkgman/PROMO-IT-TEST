package org.example;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptUtil {
    private static final String SECRET_KEY = System.getenv("MY_SECRET_KEY");

    public static String encryptText(String text) throws Exception{
        SecretKey myDesKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher desCipher;
        desCipher = Cipher.getInstance("AES");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
        byte[] encryptedBytes = desCipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    public static String decryptText(String text) throws Exception{
            SecretKey myDesKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher desCipher;
            desCipher = Cipher.getInstance("AES");
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(text);
            byte[] textDecrypted  = desCipher.doFinal(encryptedBytes);
            return new String(textDecrypted);
    }
}
