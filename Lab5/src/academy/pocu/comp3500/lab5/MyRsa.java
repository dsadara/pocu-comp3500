package academy.pocu.comp3500.lab5;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class MyRsa {
    public static String MyDecrypt(String Ciphertext, byte[] publicKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(Ciphertext);
            Cipher cipher = Cipher.getInstance("RSA");
            PublicKey publicKeyConv = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
            cipher.init(Cipher.DECRYPT_MODE, publicKeyConv);
            byte[] plaintext = cipher.doFinal(bytes);
            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
