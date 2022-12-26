package tech.thatgravyboat.aoc.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Util {

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    private Util() {
    }

    public static List<Matcher> find(Pattern pattern, Collection<String> input) {
        return find(pattern, input, matcher -> matcher);
    }

    public static <T> List<T> find(Pattern pattern, Collection<String> input, Function<Matcher, T> mapper) {
        List<T> matches = new ArrayList<>(input.size());
        input.forEach(s -> {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                matches.add(mapper.apply(matcher));
            } else {
                throw new IllegalArgumentException("No matches found for " + s);
            }
        });
        return matches;
    }

    public static List<IntMatcher> findInt(Pattern pattern, Collection<String> input) {
        return findInt(pattern, input, matcher -> matcher);
    }

    public static <T> List<T> findInt(Pattern pattern, Collection<String> input, Function<IntMatcher, T> mapper) {
        List<T> matches = new ArrayList<>(input.size());
        input.forEach(s -> matches.add(mapper.apply(IntMatcher.find(pattern, s))));
        return matches;
    }

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

    public static <T> Set<T> intersection(List<? extends Set<T>> sets) {
        Set<T> intersection = new HashSet<>(sets.get(0));
        for (int i = 1; i < sets.size(); i++) {
            intersection.retainAll(sets.get(i));
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

    @SafeVarargs
    public static <T> Set<T> copyAndAdd(Set<T> list, T... elements) {
        Set<T> copy = new HashSet<>(list);
        copy.addAll(Arrays.asList(elements));
        return copy;
    }

    public static <T> void bsf(T start, BsfConsumer<T> consumer) {
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);
            consumer.accept(current, queue);
        }
    }

    public static boolean chance(float chance) {
        return chance >= 1 || RANDOM.nextFloat() < chance;
    }

    public static int product(int... ints) {
        return Arrays.stream(ints).reduce(1, (a, b) -> a * b);
    }

    public static int sum(int... ints) {
        return Arrays.stream(ints).sum();
    }

    @FunctionalInterface
    public interface BsfConsumer<T> {
        void accept(T t, Queue<T> queue);
    }
}
