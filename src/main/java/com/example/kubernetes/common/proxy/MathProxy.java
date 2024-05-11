package com.example.kubernetes.common.proxy;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MathProxy {
    public static Function<Integer, Integer> absInt = Math::abs;

    //Math:ceil

    //Math:floor

    //Math:round

    public static BiFunction<Integer, Integer, Integer> maxInt = Math::max;
    public static Supplier<Double> randomDouble = Math::random;

    public static BiFunction<Integer, Integer, Integer> randomint = (a, b) -> (int) (Math.random() * (b - a));

    //Integer.parseInt
}
