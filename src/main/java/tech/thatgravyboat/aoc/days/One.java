package tech.thatgravyboat.aoc.days;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import tech.thatgravyboat.aoc.templates.Template;

import java.util.Collections;

public class One extends Template {

    public static void main(String[] args) {
        new One().load(1);
    }

    private final IntList elves = new IntArrayList();

    /**
     * Loop through the input and if a line is empty that means we have reached the end of the section,
     * and we add the count to the list.
     * <br>
     * If the line is not empty then we parse the line as an int and then add it to count.
     * <br>
     * We then sort it so that its from lowest to highest.
     * <br>
     * We then reverse it so that its from highest to lowest.
     */
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

    /**
     * Return the top elf in the lists calories.
     */
    @Override
    public String partOne() {
        return Integer.toString(this.elves.get(0));
    }

    /**
     * Return the top 3 elves in the list total calories.
     */
    @Override
    public String partTwo() {
        return Integer.toString(this.elves.get(0) + this.elves.get(1) + this.elves.get(2));
    }
}
