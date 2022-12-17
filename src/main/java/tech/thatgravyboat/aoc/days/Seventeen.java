package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.CyclicalList;
import tech.thatgravyboat.aoc.utils.Direction;
import tech.thatgravyboat.aoc.utils.Range;
import tech.thatgravyboat.aoc.utils.Vec2i;

import java.util.*;
import java.util.stream.Stream;

public class Seventeen extends Template {

    private static final List<Shape> SHAPES = List.of(
        Shape.of(new Vec2i(0,0), new Vec2i(1,0), new Vec2i(2,0), new Vec2i(3,0)),
        Shape.of(new Vec2i(0,1), new Vec2i(1,1), new Vec2i(2,1), new Vec2i(1,2), new Vec2i(1,0)),
        Shape.of(new Vec2i(0,0), new Vec2i(1,0), new Vec2i(2,0), new Vec2i(2,1), new Vec2i(2,2)),
        Shape.of(new Vec2i(0,0), new Vec2i(0,1), new Vec2i(0,2), new Vec2i(0,3)),
        Shape.of(new Vec2i(0,0), new Vec2i(0,1), new Vec2i(1,1), new Vec2i(1,0))
    );

    public static void main(String[] args) {
        new Seventeen().load(17);
    }

    private final List<Direction> moves = new ArrayList<>();

    @Override
    protected void onInputLoaded() {
        moves.addAll(getInput().get(0).chars().mapToObj(i -> i == '>' ? Direction.RIGHT : Direction.LEFT).toList());
    }

    @Override
    public String partOne() {
        final Set<Vec2i> tower = new LinkedHashSet<>();

        final CyclicalList<Direction> moves = new CyclicalList<>(this.moves);
        final CyclicalList<Shape> shapes = new CyclicalList<>(SHAPES);
        int top = 0;

        for (int i = 0; i < 2022; i++) {
            final Shape shape = shapes.next();
            Vec2i pos = new Vec2i(3, top + 4);

            while (true) {

                switch (moves.next()) {
                    case LEFT -> {
                        if (!shape.touching(tower, pos.relative(Direction.LEFT)) && shape.x().offset(pos.x()).min() > 1) {
                            pos = pos.relative(Direction.LEFT);
                        }
                    }
                    case RIGHT -> {
                        if (!shape.touching(tower, pos.relative(Direction.RIGHT)) && shape.x().offset(pos.x()).max() < 7) {
                            pos = pos.relative(Direction.RIGHT);
                        }
                    }
                }

                final Vec2i down = pos.opposite(Direction.DOWN);
                final boolean hitFloor = shape.adjustedPoints(down).mapToInt(Vec2i::y).anyMatch(vec -> vec == 0);

                if (hitFloor || shape.touching(tower, down)) {
                    shape.adjustedPoints(pos).forEach(tower::add);
                    break;
                }

                pos = down;
            }
            top = tower.stream().mapToInt(Vec2i::y).max().orElse(0);
        }

        return Integer.toString(top);
    }

    @Override
    public String partTwo() {
        // I give up on part 2, because it requires more thinking than I want.
        // All I know is that since both lists repeat I would likely have to
        // find a pattern and then calculate the final value using that pattern.
        return null;
    }

    public record Shape(List<Vec2i> points, Range x, Range y) {

        public static Shape of(Vec2i... points) {
            var list = Arrays.stream(points).map(Vec2i::toImmutable).toList();
            return new Shape(
                list,
                new Range(list.stream().mapToInt(Vec2i::x).min().orElse(0), list.stream().mapToInt(Vec2i::x).max().orElse(0)),
                new Range(list.stream().mapToInt(Vec2i::y).min().orElse(0), list.stream().mapToInt(Vec2i::y).max().orElse(0))
            );
        }

        public Stream<Vec2i> adjustedPoints(Vec2i pos) {
            return points.stream().map(pos::add);
        }

        public boolean touching(Set<Vec2i> tower, Vec2i offset) {
            return adjustedPoints(offset).anyMatch(tower::contains);
        }
    }
}
