package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.Pair;
import tech.thatgravyboat.aoc.utils.Range;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Four extends Template {

    public static void main(String[] args) {
        new Four().load(4);
    }

    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

    private final List<Pair<Range, Range>> sections = new ArrayList<>();

    /**
     * Loop through each line of the input match the pattern.
     * <br>
     * Get 2 ranges from the pattern and add them to the sections list.
     */
    @Override
    public void loadData(List<String> input) {
        sections.addAll(Util.findInt(PATTERN, input, matcher -> new Pair<>(
                new Range(matcher.group(1), matcher.group(2)),
                new Range(matcher.group(3), matcher.group(4))
        )));
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
        return Integer.toString(sections.stream()
                .filter(section -> Range.isEitherASubsetOfTheOther(section.left(), section.right()))
                .mapToInt(s -> 1)
                .sum());
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
        return Integer.toString(sections.stream()
                .filter(section -> section.left().intersects(section.right()))
                .mapToInt(s -> 1)
                .sum());
    }
}
