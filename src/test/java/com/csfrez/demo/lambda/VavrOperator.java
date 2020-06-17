package com.csfrez.demo.lambda;

import io.vavr.*;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import static io.vavr.API.*;

public class VavrOperator {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static void main(String[] args) {
        Tuple2<String, Integer> tuple2 = Tuple.of("Hello", 100);
        Tuple2<String, Integer> updatedTuple2 = tuple2.map(String::toUpperCase, v -> v * 5);
        String result = updatedTuple2.apply((str, number) -> String.join(", ", str, number.toString()));
        System.out.println(result);

        Function3< Integer, Integer, Integer, Integer> function3 = (v1, v2, v3) -> (v1 + v2) * v3;
        Function3< Integer, Integer, Integer, Integer> composed = function3.andThen(v -> v * 100);
        System.out.println(composed.apply(1, 2, 3));
        // 输出结果 900

        Function1< String, String> function1 = String::toUpperCase;
        Function1< Object, String> toUpperCase = function1.compose(Object::toString);
        String str = toUpperCase.apply(List.of("a", "b"));
        System.out.println(str);
        // 输出结果[A, B]

        Function4< Integer, Integer, Integer, Integer, Integer> function4 = (v1, v2, v3, v4) -> (v1 + v2) * (v3 + v4);
        Function2< Integer, Integer, Integer> function2 = function4.apply(1, 2);
        System.out.println(function2.apply(4, 5));
        // 输出 27

        Function3<Integer, Integer, Integer, Integer> function3Curried = (v1, v2, v3) -> (v1 + v2) * v3;
        int resultCurried = function3Curried.curried().apply(1).curried().apply(2).curried().apply(3);
        System.out.println(resultCurried);

        Function2<BigInteger, Integer, BigInteger> pow = BigInteger::pow;
        Function2<BigInteger, Integer, BigInteger> memoized = pow.memoized();
        long start = System.currentTimeMillis();
        memoized.apply(BigInteger.valueOf(1024), 1024);
        long end1 = System.currentTimeMillis();
        memoized.apply(BigInteger.valueOf(1024), 1024);
        long end2 = System.currentTimeMillis();
        System.out.println(String.format("%d ms -> %d ms", end1 - start, end2 - end1));

        Either<String, String> either = compute()
                .map(s -> s + " World")
                .mapLeft(Throwable::getMessage);
        System.out.println(either);

        Try<Integer> value = Try.of(() -> 1 / 0).recover(e -> 1);
        System.out.println(value);

        Try<String> lines = Try.of(() -> Files.readAllLines(Paths.get("1.txt")))
                .map(list -> String.join(",", list))
                .andThen((Consumer<String>) System.out::println);
        System.out.println(lines);

        Lazy<BigInteger> lazy = Lazy.of(() ->
                BigInteger.valueOf(1024).pow(1024));
        System.out.println(lazy.isEvaluated());
        System.out.println(lazy.get());
        System.out.println(lazy.isEvaluated());


        Map<Boolean, io.vavr.collection.List<Integer>> booleanListMap = Stream.ofAll(1, 2, 3, 4, 5)
                .groupBy(v -> v % 2 == 0)
                .mapValues(Value::toList);
        System.out.println(booleanListMap);
        // 输出 LinkedHashMap((false, List(1, 3, 5)), (true, List(2, 4)))

        Tuple2<io.vavr.collection.List<Integer>, io.vavr.collection.List<Integer>> listTuple2 = Stream.ofAll(1, 2, 3, 4)
                .partition(v -> v > 2)
                .map(Value::toList, Value::toList);
        System.out.println(listTuple2);
        // 输出 (List(3, 4), List(1, 2))

        io.vavr.collection.List<Integer> integers = Stream.ofAll(List.of("Hello", "World", "a"))
                .scanLeft(0, (sum, s) -> sum + s.length())
                .toList();
        System.out.println(integers);
        // 输出 List(0, 5, 10, 11)

        io.vavr.collection.List<Tuple2<Integer, String>> tuple2List = Stream.ofAll(1, 2, 3, 4)
                .zip(List.of("a", "b", "c"))
                .toList();
        System.out.println(tuple2List);
        // 输出 List((1, a), (2, b), (3, c))

        String input = "g";
        String match = Match(input).of(
                Case($("g"), "good"),
                Case($("b"), "bad"),
                Case($(), "unknown")
        );
        System.out.println(match);
        // 输出 good

        int vv = -1;
        Match(vv).of(
                Case($(v -> v > 0), o -> run(() -> System.out.println("> 0"))),
                Case($(0), o -> run(() -> System.out.println("0"))),
                Case($(), o -> run(() -> System.out.println("< 0")))
        );
        // 输出 < 0
    }

    private static Either<Throwable, String> compute() {
        return random.nextBoolean()
                ? Either.left(new RuntimeException("Boom!"))
                : Either.right("Hello");
    }
}
