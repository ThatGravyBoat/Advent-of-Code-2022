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
