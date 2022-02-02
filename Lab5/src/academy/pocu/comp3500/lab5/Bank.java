package academy.pocu.comp3500.lab5;

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
            System.out.println("pubKeysHashMap[" + i + "]: " + hashCode);
        }
    }

    public long getBalance(final byte[] pubKey) {
        int index = pubKeysHashMap.get(pubKey.hashCode());
        return amounts[index];
    }

    public boolean transfer(final byte[] from, byte[] to, final long amount, final byte[] signature) {

        return false;
    }
}