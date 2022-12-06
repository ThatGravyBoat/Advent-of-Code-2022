package tech.thatgravyboat.aoc.utils;

import java.util.stream.Collector;

public final class StringUtils {

    private StringUtils() {}

    public static String[] splitEveryX(String input, int x) {
        int size = (input.length() + x - 1) / x;
        String[] output = new String[size];
        for (int i = 0; i < size; i++) {
            output[i] = input.substring(i * x, Math.min(input.length(), (i + 1) * x));
        }
        return output;
    }

    public static <T> Collector<T, StringBuilder, StringBuilder> collectToString() {
        return Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append);
    }
}
