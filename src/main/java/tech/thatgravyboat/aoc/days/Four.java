package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntMatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Four extends Template {

    public static void main(String[] args) {
        new Four().load(4);
    }

    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

    private final List<Range[]> sections = new ArrayList<>();

    /**
     * Loop through each line of the input match the pattern.
     * <br>
     * Get 2 ranges from the pattern and add them to the sections list.
     */
    @Override
    public void loadData(List<String> input) {
        for (String s : input) {
            IntMatcher matcher = IntMatcher.find(PATTERN, s);
            sections.add(new Range[]{
                new Range(matcher.group(1), matcher.group(2)),
                new Range(matcher.group(3), matcher.group(4))
            });
        }
    }

    /**
     * Loop through the sections.
     * <br>
     * If the range 1 is in range 0 or range 0 in range 1 then increment the counter.
     * <br>
     * return the counter.
     */
    @Override
    public String partOne() {
        int count = 0;
        for (Range[] section : sections) {
            if (section[0].inRange(section[1]) || section[1].inRange(section[0])) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    /**
     * Loop through the sections.
     * <br>
     * If any of the values intersect then add to the counter.
     * <br>
     * return the counter.
     */
    @Override
    public String partTwo() {
        int count = 0;
        for (Range[] section : sections) {
            Set<Integer> first = section[0].getAsSet();
            first.retainAll(section[1].getAsSet());
            if (!first.isEmpty()) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    private record Range(int start, int end) {

        private Set<Integer> getAsSet() {
            Set<Integer> set = new HashSet<>();
            for (int i = start; i <= end; i++) {
                set.add(i);
            }
            return set;
        }

        public boolean inRange(Range value) {
            return value.start >= start && value.start <= end && value.end >= start && value.end <= end;
        }

    }
}
