package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Nine extends Template {

    private static final Pattern PATTERN = Pattern.compile("([DRUL]) (\\d+)");

    public static void main(String[] args) {
        new Nine().load(9);
    }

    private final List<Pair<Direction, Integer>> moves = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        for (String s : getInput()) {
            IntMatcher matcher = IntMatcher.find(PATTERN, s);
            switch (matcher.getMatcher().group(1)) {
                case "L" -> moves.add(new Pair<>(Direction.LEFT, matcher.group(2)));
                case "R" -> moves.add(new Pair<>(Direction.RIGHT, matcher.group(2)));
                case "U" -> moves.add(new Pair<>(Direction.UP, matcher.group(2)));
                default -> moves.add(new Pair<>(Direction.DOWN, matcher.group(2)));
            }
        }
    }

    /**
     * Create a head and tail.
     * <br>
     * Loop through the moves.
     * <br>
     * For each move, repeat the move the amount of times.
     * <br>
     * Copy the head and move it in the direction of the move.
     * <br>
     * Check if distance sqrt is greater than 1, if so then set tail to head.
     * <br>
     * Set the head to the new head which is the copy.
     * <br>
     * Add the tail to the visited set.
     */
    @Override
    public String partOne() {
        Set<Vec2i> visited = new HashSet<>();

        Vec2i.Mutable head = new Vec2i.Mutable(0, 0);
        Vec2i.Mutable tail = new Vec2i.Mutable(0, 0);

        for (var move : moves) {
            Util.repeat(move.right(), () -> {
                Vec2i next = head.toImmutable().relative(move.left());
                if (next.distSqrt(tail) > 1) {
                    tail.set(head);
                }
                head.set(next);
                visited.add(tail.toImmutable());
            });
        }
        return Integer.toString(visited.size());
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
    @Override
    public String partTwo() {
        Set<Vec2i> visited = new HashSet<>();

        List<Vec2i> rope = Util.listFill(10, () -> new Vec2i.Mutable(0, 0));

        for (var move : moves) {
            for (int i = 0; i < move.right(); i++) {
                rope.get(0).relative(move.left());

                for (int j = 1; j < rope.size(); j++) {
                    Vec2i head = rope.get(j - 1);
                    Vec2i tail = rope.get(j);

                    if (head.equals(tail)) continue;

                    if(head.xDiff(tail) == 2 || head.yDiff(tail) == 2) {
                        //We do it like this because we still need it to move on a 1 diff if another axis is 2
                        if (!head.isEqual(tail, Axis.X)) {
                            tail.relative(head.x() > tail.x() ? Direction.RIGHT : Direction.LEFT);
                        }
                        if (!head.isEqual(tail, Axis.Y)) {
                            tail.relative(head.y() > tail.y() ? Direction.UP : Direction.DOWN);
                        }
                    }
                }

                visited.add(rope.get(9).toImmutable());
            }
        }

        return Integer.toString(visited.size());
    }
}
