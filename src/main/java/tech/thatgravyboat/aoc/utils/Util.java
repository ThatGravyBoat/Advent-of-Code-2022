package tech.thatgravyboat.aoc.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
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

    public static <T> List<T> listFill(int amount, Supplier<T> supplier) {
        List<T> list = new ArrayList<>();
        Util.repeat(amount, () -> list.add(supplier.get()));
        return list;
    }

    public static <T> Collector<T, ?, List<T>> collectReverseList() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.reverse(list);
            return list;
        });
    }

    public static void repeat(int i, Runnable runnable) {
        IntStream.range(0, i).forEachOrdered(i1 -> runnable.run());
    }

    @SafeVarargs
    public static <T> Set<T> intersection(Set<T> set, Set<T>... sets) {
        Set<T> intersection = new HashSet<>(set);
        for (Set<T> s : sets) {
            intersection.retainAll(s);
        }
        return intersection;
    }

    public static <T> List<List<T>> groupLists(List<T> list, int x) {
        List<List<T>> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % x == 0) {
                lists.add(new ArrayList<>(x));
            }
            lists.get(i / x).add(list.get(i));
        }
        return lists;
    }

    public static int[] reverseIntArray(int[] array) {
        int[] reversed = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - i - 1];
        }
        return reversed;
    }
}
