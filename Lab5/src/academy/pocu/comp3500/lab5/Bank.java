package academy.pocu.comp3500.lab5;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Bank {
//    private byte[][] pubKeys;
    private ArrayList<byte[]> pubKeys = new ArrayList<>();
//    private long[] amounts;
    private ArrayList<Long> amounts = new ArrayList<>();
    HashMap<byte[], Integer> pubKeysHashMap = new HashMap<>();

    public Bank(byte[][] pubKeys, final long[] amounts) {
        for (int i = 0; i < pubKeys.length; i++) {
            this.pubKeys.add(pubKeys[i]);
        }
        for (int i = 0; i < amounts.length; i++) {
            this.amounts.add(amounts[i]);
        }
        // public key의 해시코드를 순서대로 배열에 저장
        for (int i = 0; i < pubKeys.length; i++) {
            pubKeysHashMap.put(pubKeys[i], i);
        }
    }

    public long getBalance(final byte[] pubKey) {
        Integer index = pubKeysHashMap.get(pubKey);
        if (index == null)
            return 0;
        return amounts.get(index.intValue());
    }

    public boolean transfer(final byte[] from, final byte[] to, final long amount, final byte[] signature) {
        // check if wallets are valid
        if (pubKeysHashMap.get(from) == null)    // from의 지갑이 유효하지 않으면 false
            return false;
        if (pubKeysHashMap.get(to) == null) {    // to의 지갑이 유효하지 않으면 새 지갑을 만들어 넣기?
            pubKeys.add(to);
            amounts.add((long)0);
            pubKeysHashMap.put(to, pubKeys.size() - 1);
        }

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
        int fromIndex = pubKeysHashMap.get(from);
        int toIndex = pubKeysHashMap.get(to);
        amounts.set(fromIndex, amounts.get(fromIndex) - amount);
        amounts.set(toIndex, amounts.get(toIndex) + amount);

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