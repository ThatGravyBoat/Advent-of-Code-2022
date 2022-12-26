package tech.thatgravyboat.aoc.days;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import tech.thatgravyboat.aoc.templates.Template;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Twenty extends Template {

    private static final long DECRYPTION_KEY = 811589153;

    public static void main(String[] args) {
        new Twenty().load(20);
    }

    private final List<Line> sequence = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        for (String s : getInput()) {
            sequence.add(new Line(Long.parseLong(s)));
        }
    }

    @Override
    public String partOne() {
        return Long.toString(solve(1, 1));
    }

    @Override
    public String partTwo() {
        return Long.toString(solve(10, DECRYPTION_KEY));
    }

    private long solve(int rounds, long multiple) {
        final var newSequence = this.sequence.stream().map(line -> line.multiply(multiple)).toList(); //Rewrite every sequence with its multiple
        final var copy = new ArrayList<>(newSequence); //Copy the sequence so we can modify it.
        final int lastIndex = copy.size() - 1; //Store the last index as it won't change.

        for (int i = 0; i < rounds; i++) {
            for (var number : newSequence) {
                final int index = copy.indexOf(number);
                copy.remove(index);
                final int newIndex = (int) ((((index + number.value()) % lastIndex) + lastIndex) % lastIndex);
                copy.add(newIndex, number);
            }
        }

        final LongList mixed = copy.stream().map(Line::value).collect(Collectors.toCollection(LongArrayList::new));

        final int zeroIndex = mixed.indexOf(0L);
        long output = 0;
        for (int i = 1; i <= 3; i++) {
            output += mixed.get(zeroIndex + (i * 1000));
        }

        return output;
    }

    /**
     * This is needed because index of pulls the wrong value if it's just a long because it's not that exact instance.
     * <br>
     * And this cant be replaced with a custom IdentityArrayList because it would still pull the wrong value because numbers aren't unique.
     * <br>
     * So we just wrap it and make a custom equals method that checks if it's the specific instance.
     */
    public record Line(long value) {

        public Line multiply(long multiple) {
            return new Line(this.value * multiple);
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this;
        }
    }
}
