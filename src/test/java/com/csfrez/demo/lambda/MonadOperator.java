package com.csfrez.demo.lambda;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.function.Function;

public class MonadOperator {

    public static void main(String[] args) {
        MonadOperator monadOperator = new MonadOperator();
        Tuple2<Integer, Integer> integerTuple2 = monadOperator.doCompose(1);
        System.out.println(integerTuple2);
    }

    Tuple2<Integer, Integer> increase1(int x) {
        return Tuple.of(x + 1, 1);
    }

    Tuple2<Integer, Integer> decrease1(int x) {
        return Tuple.of(x - 1, 1);
    }

    Function<Integer, Tuple2<Integer, Integer>> compose(
            Function<Integer, Tuple2<Integer, Integer>> func1,
            Function<Integer, Tuple2<Integer, Integer>> func2) {
        return x -> {
            Tuple2<Integer, Integer> result1 = func1.apply(x);
            Tuple2<Integer, Integer> result2 = func2.apply(result1._1);
            return Tuple.of(result2._1, result1._2 + result2._2);
        };
    }

    Tuple2<Integer, Integer> doCompose(int x) {
        return compose(this::increase1, this::decrease1).apply(x);
    }
}
