package academy.pocu.comp3500.lab5;

import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {

    // (x^y) % p
    static BigInteger power(BigInteger x, BigInteger y, BigInteger p) {
        BigInteger res = BigInteger.ONE;

        x = x.mod(p);

        while (y.compareTo(BigInteger.ZERO) == 1) { // while (y > 0)
            if (y.mod(BigInteger.TWO).compareTo(BigInteger.ONE) == 0)   // if y is odd
                res = (res.multiply(x).mod(p)); // (res * x) % p

            y = y.divide(BigInteger.TWO);
            x = x.multiply(x).mod(p);
        }

        return res;
    }

    static boolean millerTest(BigInteger d, BigInteger number) {
        // pick random number ins 2 ~ n-2
        Double numBits = Math.log(number.doubleValue() - 4);   // make log(number-4)
        BigInteger a = new BigInteger(numBits.intValue(), new Random()).add(BigInteger.TWO);    // random number in 2 ~ n-2

        // comupte a^d % n
        BigInteger x = power(a, d, number);

        if (x.compareTo(BigInteger.ONE) == 0 || x.compareTo(number.subtract(BigInteger.ONE)) == 0)
            return true;

        while (d.compareTo(number.subtract(BigInteger.ONE)) != 0) { // d != number - 1
            x = x.multiply(x).mod(number);  // x = (x * x) % n
            d = d.multiply(BigInteger.TWO);     // d *= 2

            if (x.compareTo(BigInteger.ONE) == 0)
                return false;
            if (x.compareTo(number.subtract(BigInteger.ONE)) == 0)
                return true;
        }
        return false;
    }

    public static boolean isPrime(final BigInteger number) {
//        if (number.compareTo(BigInteger.TWO) == -1)     // 0, 1은 소수가 아님
//            return false;
//        for (int i = 2; i <= Math.sqrt(number.doubleValue()); i++) {
//            if (number.mod(BigInteger.valueOf(i)).compareTo(BigInteger.ZERO) == 0)
//                return false;
//        }
//        return true;
        if (number.compareTo(BigInteger.TWO) == -1) // if number is 0, 1
            return false;
        if (number.compareTo(BigInteger.valueOf(3)) != 1)  // if number is 2, 3
            return true;
        if (number.compareTo(BigInteger.valueOf(4)) == 0)   // if number is 4
            return false;
        if (number.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0)    // if n is even
            return false;

        // n = 2^d * r + 1 인 r 찾기
        BigInteger d = number.subtract(BigInteger.ONE); // d = number - 1

        while (d.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {     // while(d % 2 == 0)
            d = d.divide(BigInteger.TWO);   // d /= 2
        }
        // now d is r

        for (int i = 0; i < 10; i++) {
            if (!millerTest(d, number))
                return false;
        }
        return true;
    }
}