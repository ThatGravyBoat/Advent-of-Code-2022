package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.StringUtils;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Three extends Template {

    private static final String INDEXES = "\0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        new Three().load(3);
    }

    /**
     * Loop through the input and get the middle of the line.
     * <br>
     * Substring the middle of the line into 2 strings.
     * <br>
     * Turn those 2 strings into a char set.
     * <br>
     * Get the intersection of the 2 char sets.
     * <br>
     * Loop through the intersection and add the char priority to the total.
     */
    @Override
    public String partOne() {
        Stream<List<String>> intersections = getInput()
                .stream()
                .map(line -> List.of(line.substring(0, line.length() / 2), line.substring(line.length() / 2)));
        return Integer.toString(solve(intersections));
    }

    /**
     * Group the input into sublist of 3.
     * <br>
     * Loop through the sublists and turn the 3 lists into sets.
     * <br>
     * Get the intersection of the 3 sets.
     * <br>
     * Loop through the intersection and add the first char priority to the total as there should only be 1 char in the intersection.
     */
    @Override
    public String partTwo() {
        return Integer.toString(solve(Util.groupLists(getInput(), 3).stream()));
    }

    private int solve(Stream<List<String>> intersections) {
        return intersections
                .map(line -> line.stream().map(StringUtils::toSet).toList())
                .map(Util::intersection)
                .flatMap(Collection::stream)
                .mapToInt(INDEXES::indexOf)
                .sum();
    }
}
