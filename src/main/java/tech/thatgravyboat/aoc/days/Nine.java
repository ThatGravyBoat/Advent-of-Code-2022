package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.*;

import java.util.*;
import java.util.regex.Pattern;

public class Nine extends Template {

    private static final Pattern PATTERN = Pattern.compile("([DRUL]) (\\d+)");
    private static final Map<String, Vec2i> DIRECTIONS = Map.of(
            "U", Vec2i.of(0, 1),
            "D", Vec2i.of(0, -1),
            "L", Vec2i.of(-1, 0),
            "R", Vec2i.of(1, 0)
    );

    public static void main(String[] args) {
        new Nine().load(9);
    }

    private final List<Pair<Vec2i, Integer>> moves = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        moves.addAll(Util.findInt(PATTERN, getInput(), matcher -> new Pair<>(DIRECTIONS.get(matcher.getMatcher().group(1)), matcher.group(2))));
    }

    @Override
    public String partOne() {
        return Integer.toString(solve(1));
    }

    @Override
    public String partTwo() {
        return Integer.toString(solve(9));
    }

    /**
     * Make a list of the whole rope.
     * <br>
     * Loop through the moves.
     * <br>
     * Move the first knot in the rope.
     * <br>
     * Loop through the rest of the rope taking the n - 1 knot as head and the n knot as tail.
     * <br>
     * if head is equal to tail then skip.
     * <br>
     * if the difference of x and y is not equal to 2 then skip.
     * <br>
     * if head and tail arnt equal on x then move the tail to the right if head is greater than tail on the x-axis  and left if head is less than tail.
     * <br>
     * if head and tail arnt equal on y then move the tail up if head is greater than tail on the y-axis and down if head is less than tail.
     * <br>
     * After looping through the rope add the last tail to the visited set.
     */
    private int solve(int length) {
        Set<Vec2i> visited = new HashSet<>();

        List<Vec2i> rope = Util.listFill(length + 1, () -> new Vec2i.Mutable(0, 0));

        for (var move : moves) {
            for (int i = 0; i < move.right(); i++) {
                rope.get(0).add(move.left());

                for (int j = 1; j < rope.size(); j++) {
                    Vec2i last = rope.get(j - 1);
                    Vec2i current = rope.get(j);

                    if (last.equals(current)) continue;

                    final int xDiff = last.x() - current.x();
                    final int yDiff = last.y() - current.y();

                    if (Math.max(Math.abs(xDiff), Math.abs(yDiff)) == 2) {
                        //We do it like this because we still need it to move on a 1 diff if another axis is 2
                        current.add(Integer.signum(xDiff), Integer.signum(yDiff));
                    }
                }

                visited.add(rope.get(length).toImmutable());
            }
        }

        return visited.size();
    }
}
