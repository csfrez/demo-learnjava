package com.csfrez.demo.lambda;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.function.Function;

public class StateMonad {

    public static <T, S> Function<S, Tuple2<T, S>> unit(T value) {
        return s -> Tuple.of(value, s);
    }

    public static <T1, T2, S> Function<S, Tuple2<T2, S>> bind(Function<S, Tuple2<T1, S>> input,
         Function<T1, Function<S, Tuple2<T2, S>>> transform) {
        return s -> {
            Tuple2<T1, S> result = input.apply(s);
            return transform.apply(result._1).apply(result._2);
        };
    }

    public static void main(String[] args) {
        Function<String, Function<String, Function<State, Tuple2<String, State>>>> transform =
                prefix -> value -> s -> Tuple .of(prefix + value, new State(s.getValue() + value.length()));

        Function<State, Tuple2<String, State>> m1 = unit("Hello");
        Function<State, Tuple2<String, State>> m2 = bind(m1, transform.apply("1"));
        Function<State, Tuple2<String, State>> m3 = bind(m2, transform.apply("2"));
        Tuple2<String, State> result = m3.apply(new State(0));
        System.out.println(result);
    }

    static class State {

        private final int value;

        public State(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "State{" +
                    "value=" + value +
                    '}';
        }
    }
}