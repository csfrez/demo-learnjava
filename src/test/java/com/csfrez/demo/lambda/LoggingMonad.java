package com.csfrez.demo.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LoggingMonad<T> {

    private final T value;
    private final List<String> logs;

    public LoggingMonad(T value, List<String> logs) {
        this.value = value;
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "LoggingMonad{" +
                "value=" + value +
                ", logs=" + logs +
                '}';
    }

    public static <T> LoggingMonad<T> unit(T value) {
        return new LoggingMonad<>(value, List.of());
    }

    public static <T1, T2> LoggingMonad<T2> bind(LoggingMonad<T1> input,
                                                 Function<T1, LoggingMonad<T2>> transform) {
        final LoggingMonad<T2> result = transform.apply(input.value);
        List<String> logs = new ArrayList<>(input.logs);
        logs.addAll(result.logs);
        return new LoggingMonad<>(result.value, logs);
    }

    public static <T> LoggingMonad<T> pipeline(LoggingMonad<T> monad,
                                               List<Function<T, LoggingMonad<T>>> transforms) {
        LoggingMonad<T> result = monad;
        for (Function<T, LoggingMonad<T>> transform : transforms) {
            result = bind(result, transform);
        }
        return result;
    }

    public static void main(String[] args) {
        Function<Integer, LoggingMonad<Integer>> transform1 =
                v -> new LoggingMonad<>(v * 4, List.of(v + " * 4"));
        Function<Integer, LoggingMonad<Integer>> transform2 =
                v -> new LoggingMonad<>(v / 2, List.of(v + " / 2"));
        final LoggingMonad<Integer> result = pipeline(LoggingMonad.unit(8), List.of(transform1, transform2));
        System.out.println(result);
        // 输出为 LoggingMonad{value=16, logs=[8 * 4, 32 / 2]}
}
}