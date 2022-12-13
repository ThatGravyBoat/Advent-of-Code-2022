package tech.thatgravyboat.aoc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntMatcher {

    private final Matcher matcher;

    public IntMatcher(Pattern pattern, String input) {
        this.matcher = pattern.matcher(input);
    }

    public static IntMatcher find(Pattern pattern, String input) {
        IntMatcher matcher = new IntMatcher(pattern, input);
        if (!matcher.find()) throw new IllegalStateException("No match found");
        return matcher;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public boolean find() {
        return matcher.find();
    }

    public int group(int group) {
        return Integer.parseInt(matcher.group(group));
    }

    public int group() {
        return Integer.parseInt(matcher.group());
    }

    public int end() {
        return matcher.end();
    }
}
