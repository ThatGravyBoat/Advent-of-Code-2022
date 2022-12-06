package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.StringUtils;

public class Six extends Template {

    public static void main(String[] args) {
        new Six().load(6);
    }

    private String input;

    @Override
    protected void onInputLoaded() {
        this.input = getInput().get(0);
    }

    @Override
    public String partOne() {
        return Integer.toString(solve(4));
    }

    @Override
    public String partTwo() {
        return Integer.toString(solve(14));
    }

    private int solve(int length) {
        int i = 0;
        while (StringUtils.toSet(this.input.substring(i, i + length)).size() != length) {
            i += 1;
        }
        return i + length;
    }
}
