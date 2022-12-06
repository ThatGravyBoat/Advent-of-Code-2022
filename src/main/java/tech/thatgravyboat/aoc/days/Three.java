package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.StringUtils;
import tech.thatgravyboat.aoc.utils.Util;

import java.util.*;

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
        int priority = 0;

        for (String s : getInput()) {
            int half = s.length() / 2;
            Set<Character> intersection = Util.intersection(StringUtils.toSet(s.substring(0, half)), StringUtils.toSet(s.substring(half)));
            for (Character c : intersection) {
                priority += INDEXES.indexOf(c);
            }
        }
        return String.valueOf(priority);
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
        int priority = 0;
        for (List<String> s : Util.groupLists(getInput(), 3)) {
            Set<Character> intersection = Util.intersection(StringUtils.toSet(s.get(0)), StringUtils.toSet(s.get(1)), StringUtils.toSet(s.get(2)));
            for (Character c : intersection) {
                priority += INDEXES.indexOf(c);
                break;
            }
        }
        return String.valueOf(priority);
    }
}
