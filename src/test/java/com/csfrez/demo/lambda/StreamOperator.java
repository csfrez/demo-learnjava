package com.csfrez.demo.lambda;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOperator {

    public static void main(String[] args) {
        Stream.of(1, 2, 3)
            .map(v -> v + 1)
            .flatMap(v -> Stream.of(v * 5, v * 10))
            .forEach(System.out::println);

        Stream.of(1, 2, 3)
            .takeWhile(v -> v < 3)
            .dropWhile(v -> v < 2)
            .forEach(System.out::println);

        // 输出 6
        Stream.of(1, 2, 3).reduce((v1, v2) -> v1 + v2)
            .ifPresent(System.out::println);

        // 输出 120
        int result1 = Stream.of(1, 2, 3, 4, 5)
                .reduce(1, (v1, v2) -> v1 * v2);
        System.out.println(result1);

        // 输出 15
        int result2 = Stream.of(1, 2, 3, 4, 5)
                .parallel()
                .reduce(0, (v1, v2) -> v1 + v2, (v1, v2) -> v1 + v2);
        System.out.println(result2);

        Map<Character, List<String>> names = Stream.of("Alex", "Bob", "David", "Amy")
                .collect(Collectors.groupingBy(v -> v.charAt(0)));
        System.out.println(names);

        String str = Stream.of("a", "b", "c")
                .collect(Collectors.joining(", "));
        System.out.println(str);

        double avgLength = Stream.of("hello", "world", "a")
                .collect(Collectors.averagingInt(String::length));
        System.out.println(avgLength);

        IntSummaryStatistics statistics = Stream.of("a", "b", "cd")
                .collect(Collectors.summarizingInt(String::length));
        System.out.println(statistics.getAverage());
        System.out.println(statistics.getCount());

    }
}
