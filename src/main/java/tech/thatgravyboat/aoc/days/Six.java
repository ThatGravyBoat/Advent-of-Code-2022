package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.StringUtils;

public class Six extends Template {

    public static void main(String[] args) {
        new Six().load(6);
    }

    private String input;

    /**
     * Get the first line of the input
     */
    @Override
    protected void onInputLoaded() {
        this.input = getInput().get(0);
    }

    /**
     * Run solve with distinct char length of 4
     */
    @Override
    public String partOne() {
        return Integer.toString(solve(4));
    }

    /**
     * Run solve with distinct char length of 14
     */
    @Override
    public String partTwo() {
        return Integer.toString(solve(14));
    }

    /**
     * Loop until the set of the sub string of the offset + the distinct char length is the same length as the distinct char length
     * <br>
     * Then return the offset + the distinct char length
     */
    private int solve(int length) {
        int i = 0;
        while (StringUtils.toSet(this.input.substring(i, i + length)).size() != length) {
            i += 1;
        }
        return i + length;
    }
}
