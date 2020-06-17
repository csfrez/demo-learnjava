package com.csfrez.demo.lambda;

import java.math.BigInteger;

public class Fib {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(fib(40));
        long time = System.currentTimeMillis() - start;
        System.out.println("time " + time + "ms");
    }

    private static BigInteger fib(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        } else if (n == 1) {
            return BigInteger.ONE;
        }
        return fib(n - 1).add(fib(n - 2));
    }
}