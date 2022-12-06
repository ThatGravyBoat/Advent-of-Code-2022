package tech.thatgravyboat.aoc.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Util {

    private Util() {}

    public static <T> T make(T object, Consumer<T> consumer) {
        consumer.accept(object);
        return object;
    }

    public static <T> Stack<T> newStack(Collection<T> collection) {
        return Util.make(new Stack<>(), s -> s.addAll(collection));
    }

    public static <T> List<List<T>> listsWithSize(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> (List<T>) new ArrayList<T>())
                .toList();
    }

    public static <T> Collector<T, List<T>, List<T>> collectReverseList() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.reverse(list);
            return list;
        });
    }

    public static void repeat(int i, Runnable runnable) {
        IntStream.range(0, i).forEachOrdered(i1 -> runnable.run());
    }
}
