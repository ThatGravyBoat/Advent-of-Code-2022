package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntGrid;

import java.util.concurrent.atomic.AtomicInteger;

public class Eight extends Template {

    public static void main(String[] args) {
        new Eight().load(8);
    }

    private final IntGrid grid = new IntGrid(99, 99);

    @Override
    protected void onInputLoaded() {
        int row = 0;
        for (String s : getInput()) {
            grid.setRow(row, s.chars().map(i -> i - '0').toArray());
            row++;
        }
    }

    @Override
    public String partOne() {
        final AtomicInteger visible = new AtomicInteger(0);
        grid.forEach((x, y, value) -> {
            if (isVisible(x, y, value)) {
                visible.incrementAndGet();
            }
        });
        return visible.toString();
    }

    /**
     * Get all the directions from the point on the grid.
     * <br>
     * Loop through all the directions and check if the number in the direction is less than or equal to the value number.
     * <br>
     * If it is, then the number is not visible.
     * <br>
     * If it isn't, then the number is visible.
     */
    private boolean isVisible(int x, int y, int value) {
        directions:
        // I'm using a label here because I'm a bad person.
        for (int[] direction : grid.directionsLRTB(x, y)) {
            for (int i : direction) {
                if (value <= i) {
                    continue directions;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String partTwo() {
        final AtomicInteger highest = new AtomicInteger(0);
        grid.forEach((x, y, value) -> highest.getAndAccumulate(getScenicScore(x, y, value), Math::max));
        return highest.toString();
    }

    /**
     * Loop through the directions from the point to the edge of the grid.
     * <br>
     * Loop through the direction and increase distance by 1.
     * <br>
     * if the number in the direction is greater than the value number, then multiply the distance to the score.
     */
    private int getScenicScore(int x, int y, int value) {
        int score = 1;
        for (int[] direction : grid.directionsFrom(x, y)) {
            int distance = 0;
            for (int i : direction) {
                distance++;
                if (i >= value) {
                    break;
                }
            }
            score *= distance;
        }
        return score;
    }
}
