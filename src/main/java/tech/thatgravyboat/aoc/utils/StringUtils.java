package tech.thatgravyboat.aoc.utils;

import it.unimi.dsi.fastutil.chars.CharOpenHashSet;
import it.unimi.dsi.fastutil.chars.CharSet;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class StringUtils {

    private StringUtils() {
    }

    public static CharSet toSet(String s) {
        return new CharOpenHashSet(s.toCharArray());
    }

    public static String[] splitEveryX(String input, int x) {
        int size = (input.length() + x - 1) / x;
        String[] output = new String[size];
        for (int i = 0; i < size; i++) {
            output[i] = input.substring(i * x, Math.min(input.length(), (i + 1) * x));
        }
        return output;
    }

    public static <T> Collector<T, StringBuilder, String> collectToString() {
        return Collectors.collectingAndThen(
                Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append),
                StringBuilder::toString
        );
    }
}
