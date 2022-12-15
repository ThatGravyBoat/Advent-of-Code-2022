package tech.thatgravyboat.aoc.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

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

    public boolean contains(int i) {
        return i >= min && i <= max;
    }

    public boolean intersects(Range other) {
        return contains(other.min) || contains(other.max) || other.contains(min) || other.contains(max);
    }

    public int[] toArray() {
        int[] array = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = min + i;
        }
        return array;
    }

    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            list.add(i);
        }
        return list;
    }

    public void forEach(Consumer<Integer> consumer) {
        toList().forEach(consumer);
    }

    @Override
    public int compareTo(Range o) {
        return Comparator.comparingInt(Range::min).thenComparingInt(Range::max).compare(this, o);
    }
}
