package tech.thatgravyboat.aoc.utils;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    public IntMatchResult toMatchResult() {
        return new IntMatchResult(matcher.toMatchResult());
    }

    public Stream<IntMatchResult> results() {
        return matcher.results().map(IntMatchResult::new);
    }

    public Stream<IntMatchResult> allResults() {
        return Stream.concat(Stream.of(toMatchResult()), results());
    }

    public static class IntMatchResult {

        private final MatchResult result;

        public IntMatchResult(MatchResult result) {
            this.result = result;
        }

        public int group(int group) {
            return Integer.parseInt(result.group(group));
        }

        public int group() {
            return Integer.parseInt(result.group());
        }
    }
}
