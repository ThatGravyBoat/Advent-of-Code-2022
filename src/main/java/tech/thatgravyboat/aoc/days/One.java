package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class One extends Template {

    public static void main(String[] args) {
        new One().load(1);
    }

    private final List<Integer> elves = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        int count = 0;
        for (String s : getInput()) {
            if (s.isEmpty()) {
                this.elves.add(count);
                count = 0;
                continue;
            }
            count += Integer.parseInt(s);
        }
        Collections.sort(this.elves);
        Collections.reverse(this.elves);
    }

    @Override
    public String partOne() {
        return String.valueOf(this.elves.get(0));
    }

    @Override
    public String partTwo() {
        return String.valueOf(this.elves.get(0) + this.elves.get(1) + this.elves.get(2));
    }
}
