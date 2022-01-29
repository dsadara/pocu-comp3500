package academy.pocu.comp3500.lab4;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.CRC32;


public final class Cracker {
    final User[] userTable;
    final String email;
    final String password;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;
    }

    public String[] run(final RainbowTable[] rainbowTables) {
        if (rainbowTables.length != 5)
            return null;

        int hashAlgo = 0;   // 기본 해쉬 알고리즘은 CRC32로
        String myPWHash;
        byte[] inputBytes = password.getBytes(StandardCharsets.UTF_8);

        // Check if Hash Algorithm is CRC32
        CRC32 crc32 = new CRC32();
        crc32.update(inputBytes);
        myPWHash = String.valueOf(crc32.getValue());
        for (User user: userTable) {
            if (user.getPasswordHash().equals(myPWHash))
                hashAlgo = 0;
        }

        // Check if Hash Algorithm is MD2
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            md.update(inputBytes);
            byte[] digest = md.digest();
            myPWHash = Base64.getEncoder().encodeToString(digest);
            for (User user: userTable) {
                if (user.getPasswordHash().equals(myPWHash))
                    hashAlgo = 1;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        // Check if Hash Algorithm is MD5
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputBytes);
            byte[] digest = md.digest();
            myPWHash = Base64.getEncoder().encodeToString(digest);
            for (User user: userTable) {
                if (user.getPasswordHash().equals(myPWHash))
                    hashAlgo = 2;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Check if Hash Algorithm is SHA1
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(inputBytes);
            byte[] digest = md.digest();
            myPWHash = Base64.getEncoder().encodeToString(digest);
            for (User user: userTable) {
                if (user.getPasswordHash().equals(myPWHash))
                    hashAlgo = 3;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Check if Hash Algorithm is SHA256
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(inputBytes);
            byte[] digest = md.digest();
            myPWHash = Base64.getEncoder().encodeToString(digest);
            for (User user: userTable) {
                if (user.getPasswordHash().equals(myPWHash))
                    hashAlgo = 4;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String[] plaintextPWs = new String[userTable.length];
        for (int i = 0; i < userTable.length; i++) {
                String userPassWordHash = userTable[i].getPasswordHash();
                if (rainbowTables[hashAlgo].contains(userPassWordHash))
                    plaintextPWs[i] = rainbowTables[hashAlgo].get(userPassWordHash);
        }
        return plaintextPWs;
    }
}
