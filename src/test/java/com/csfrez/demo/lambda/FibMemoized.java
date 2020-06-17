package com.csfrez.demo.lambda;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class FibMemoized {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(fib(40));
        long time = System.currentTimeMillis() - start;
        System.out.println("time " + time + "ms");
    }

    private static Map<Integer, BigInteger> lookupTable = new HashMap<>();

    static {
        lookupTable.put(0, BigInteger.ZERO);
        lookupTable.put(1, BigInteger.ONE);
    }

    private static BigInteger fib(int n) {
        if (lookupTable.containsKey(n)) {
            return lookupTable.get(n);
        } else {
            BigInteger result = fib(n - 1).add(fib(n - 2));
            lookupTable.put(n, result);
            return result;
        }
    }
}
