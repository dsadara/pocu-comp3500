package academy.pocu.comp3500.lab5;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class Bank {
    private byte[][] pubKeys;
    private long[] amounts;
    HashMap<Integer, Integer> pubKeysHashMap = new HashMap<>();

    public Bank(byte[][] pubKeys, final long[] amounts) {
        this.pubKeys = pubKeys;
        this.amounts = amounts;
        // public key의 해시코드를 순서대로 배열에 저장
        for (int i = 0; i < pubKeys.length; i++) {
            int hashCode = pubKeys[i].hashCode();
            pubKeysHashMap.put(hashCode, i);
        }
    }

    public long getBalance(final byte[] pubKey) {
        Integer index = pubKeysHashMap.get(pubKey.hashCode());
        if (index == null)
            return 0;
        return amounts[index.intValue()];
    }

    public boolean transfer(final byte[] from, final byte[] to, final long amount, final byte[] signature) {
        // check if wallets are valid
        if (pubKeysHashMap.get(to.hashCode()) == null)      // to의 지갑이 유효하지 않으면 false
            return false;
        if (pubKeysHashMap.get(from.hashCode()) == null)    // from의 지갑이 유효하지 않으면 false
            return false;

        // check if amount are valid
        long fromBalance = getBalance(from);
        long toBalance = getBalance(to);
        if (amount <= 0)                 // if amount is not valid
            return false;
        if (fromBalance - amount < 0)   // if amount exceed balance
            return false;
        if (toBalance + amount < 0)     // check if receiver too rich so overflow
            return false;


        String receivedMessageSHA256 = decryptWithPublicKey(signature, from);
        if (receivedMessageSHA256 == null)
            return false;
        String validatingMessageSHA256;

        ByteBuffer buffer = ByteBuffer.allocate(from.length + to.length + Long.BYTES);
        buffer.put(from);
        buffer.put(to);
        buffer.putLong(amount);
        byte[] message = buffer.array();

        // sha256([from, to, amount])
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(message);
        byte[] digest = md.digest();
        validatingMessageSHA256 = Base64.getEncoder().encodeToString(digest);
        if (!receivedMessageSHA256.equals(validatingMessageSHA256))
            return false;

        // transfer
        int fromIndex = pubKeysHashMap.get(from.hashCode());
        int toIndex = pubKeysHashMap.get(to.hashCode());
        amounts[fromIndex] -= amount;
        amounts[toIndex] += amount;


        return true;
    }

    private static String decryptWithPublicKey(final byte[] signature, final byte[] senderPublicKey) {
        try {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(senderPublicKey));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] plaintext = cipher.doFinal(signature);
            return Base64.getEncoder().encodeToString(plaintext);
//            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (BadPaddingException e) {
            e.printStackTrace();    // if wrong publickey is wrong
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("에러!!");
        }
    }

}