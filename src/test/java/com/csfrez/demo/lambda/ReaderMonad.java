package com.csfrez.demo.lambda;

import java.util.function.Function;

public class ReaderMonad {

    public static <T, E> Function<E, T> unit(T value) {
        return e -> value;
    }

    public static <T1, T2, E> Function<E, T2> bind(Function<E, T1> input, Function<T1, Function<E, T2>> transform) {
        return e -> transform.apply(input.apply(e)).apply(e);
    }

    public static void main(String[] args) {
        Function<Environment, String> m1 = unit("Hello");
        Function<Environment, String> m2 = bind(m1, value -> e ->
                e.getPrefix() + value);
        Function<Environment, Integer> m3 = bind(m2, value -> e ->
                e.getBase() + value.length());
        int result = m3.apply(new Environment());
        System.out.println(result);
    }

    static class Environment {

        public String getPrefix() {
            return "$$";
        }

        public int getBase() {
            return 100;
        }
    }
}
