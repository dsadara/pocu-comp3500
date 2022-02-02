package academy.pocu.comp3500.lab5;

import java.math.BigInteger;

public class KeyGenerator {
    public static boolean isPrime(final BigInteger number) {
        if (number.compareTo(BigInteger.TWO) == -1)     // 0, 1은 소수가 아님
            return false;
        for (int i = 2; i <= Math.sqrt(number.doubleValue()); i++) {
            if (number.mod(BigInteger.valueOf(i)).compareTo(BigInteger.ZERO) == 0)
                return false;
        }
        return true;
    }
}