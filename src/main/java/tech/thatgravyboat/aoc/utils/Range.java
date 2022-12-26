package tech.thatgravyboat.aoc.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Range implements Comparable<Range> {

    private final int min;
    private final int max;

    public Range(int one, int two) {
        this.min = Math.min(one, two);
        this.max = Math.max(one, two);
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    public Range offset(int offset) {
        return new Range(min + offset, max + offset);
    }

    public boolean contains(int i) {
        return i >= min && i <= max;
    }

    public boolean contains(Range range) {
        return contains(range.min) && contains(range.max);
    }

    public boolean intersects(Range other) {
        return contains(other.min) || contains(other.max) || other.contains(min) || other.contains(max);
    }

    public List<Integer> toList() {
        return IntStream.range(min, max + 1).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public void forEach(Consumer<Integer> consumer) {
        toList().forEach(consumer);
    }

    @Override
    public int compareTo(Range o) {
        return Comparator.comparingInt(Range::min).thenComparingInt(Range::max).compare(this, o);
    }

    public static boolean isEitherASubsetOfTheOther(Range one, Range two) {
        return one.contains(two) || two.contains(one);
    }
}
