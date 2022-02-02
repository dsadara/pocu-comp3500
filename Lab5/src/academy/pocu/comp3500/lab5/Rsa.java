package academy.pocu.comp3500.lab5;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

public class Rsa {
    public static String decrypt(String Ciphertext, String publicKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(Ciphertext);
            Cipher cipher = Cipher.getInstance("RSA");
            PublicKey publicKey1 =
            cipher.init(Cipher.DECRYPT_MODE);
            byte[] plaintext = cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
