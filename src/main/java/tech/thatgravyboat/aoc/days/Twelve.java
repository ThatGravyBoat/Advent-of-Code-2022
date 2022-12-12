package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.Direction;
import tech.thatgravyboat.aoc.utils.IntGrid;
import tech.thatgravyboat.aoc.utils.Vec2i;

import java.util.*;

public class Twelve extends Template {

    public static void main(String[] args) {
        new Twelve().load(12);
    }

    private final IntGrid grid = new IntGrid(41, 95);
    private Vec2i start = null;
    private Vec2i end = null;

    @Override
    protected void onInputLoaded() {
        for (int i = 0; i < getInput().size(); i++) {
            String line = getInput().get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = switch (line.charAt(j)) {
                    case 'S' -> {
                        start = new Vec2i(j, i);
                        yield 'a';
                    }
                    case 'E' -> {
                        end = new Vec2i(j, i);
                        yield 'z';
                    }
                    default -> line.charAt(j);
                };
                grid.set(j, i, 1 + c - 'a');
            }
        }
    }

    @Override
    public String partOne() {
        return Integer.toString(search(this.start));
    }

    @Override
    public String partTwo() {
        Set<Vec2i> possibleStarts = new HashSet<>();
        grid.forEach((x, z, value) -> {
            if (value == 1) {
                possibleStarts.add(new Vec2i(x, z));
            }
        });
        int min = Integer.MAX_VALUE;
        for (Vec2i possibleStart : possibleStarts) {
            int search = search(possibleStart);
            if (search != -1 && search < min) {
                min = search;
            }
        }
        return Integer.toString(min);
    }

    /**
     * Start BSF with extra logic for checking if it bounds of grid and if the next id is less than or equal to n + 1
     * <br>
     * And a check for if the next position is the end and if the current one is valid to go to the end and if so escape and return the distance.
     */
    private int search(Vec2i start) {
        Set<Vec2i> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start, grid.get(start.x(), start.y()), 0));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int current = node.currentValue();

            for (Vec2i value : node.directions()) {

                if (visited.contains(value)) continue;

                if (!grid.isWithinBounds(value.x(), value.y())) {
                    continue;
                }

                int id = grid.get(value.x(), value.y());
                if (value.equals(end)) {
                    if ((current + 'a') >= 'y') {
                        return node.steps() + 1;
                    }
                } else if (id <= current + 1) {
                    visited.add(value);
                    queue.add(new Node(value, id, node.steps() + 1));
                }
            }
        }

        return -1;
    }

    public record Node(Vec2i pos, int currentValue, int steps) {

        public List<Vec2i> directions() {
            return List.of(pos.relative(Direction.LEFT), pos.relative(Direction.RIGHT), pos.relative(Direction.UP), pos.relative(Direction.DOWN));
        }
    }
}
