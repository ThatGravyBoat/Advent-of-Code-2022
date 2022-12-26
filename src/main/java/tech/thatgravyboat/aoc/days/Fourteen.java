package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.Direction;
import tech.thatgravyboat.aoc.utils.IntMatcher;
import tech.thatgravyboat.aoc.utils.Util;
import tech.thatgravyboat.aoc.utils.Vec2i;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Fourteen extends Template {

    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+)(?: -> )?");
    private static final List<List<Direction>> STEPS = List.of(
            List.of(Direction.DOWN),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.RIGHT)
    );

    public static void main(String[] args) {
        new Fourteen().load(14);
    }

    @Override
    public String partOne() {
        Cave cave = getCave();

        solve(cave, vec -> vec.y() > cave.floor(), vec -> false);

        return Integer.toString(cave.getSandCount());
    }

    @Override
    public String partTwo() {
        Cave cave = getCave();

        for (int i = cave.minX() - 1000; i <= cave.maxX() + 1000; i++) {
            cave.put(new Vec2i(i, cave.floor() + 2), Type.ROCK);
        }

        solve(cave, vec -> false, vec -> vec.x() == 500 && vec.y() == 0);

        return Integer.toString(cave.getSandCount());
    }

    private void solve(Cave cave, Function<Vec2i, Boolean> atStart, Function<Vec2i, Boolean> atEnd) {
        while (true) {
            Vec2i.Mutable start = new Vec2i.Mutable(500, 0);
            outer:
            while (true) {
                if (atStart.apply(start)) {
                    return;
                }
                Vec2i pos = start.toImmutable();

                for (List<Direction> step : STEPS) {
                    if (!cave.has(pos.relative(step))) {
                        start.relative(step);
                        continue outer;
                    }
                }

                cave.put(start.toImmutable(), Type.SAND);
                if (atEnd.apply(start)) {
                    return;
                }
                break;
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Cave getCave() {
        Map<Vec2i, Type> cave = new HashMap<>();
        for (IntMatcher matcher : Util.findInt(PATTERN, getInput())) {
            matcher.allResults()
                    .map(result -> new Vec2i(result.group(1), result.group(2)))
                    .reduce(null, (last, current) -> {
                        if (last == null) {
                            cave.put(current, Type.ROCK);
                            return current;
                        }
                        if (current.y() == last.y()) {
                            for (int i = Math.min(current.x(), last.x()); i <= Math.max(current.x(), last.x()); i++) {
                                cave.put(new Vec2i(i, current.y()), Type.ROCK);
                            }
                        } else {
                            for (int i = Math.min(current.y(), last.y()); i <= Math.max(current.y(), last.y()); i++) {
                                cave.put(new Vec2i(current.x(), i), Type.ROCK);
                            }
                        }
                        return current;
                    });
        }
        return new Cave(cave);
    }

    public enum Type {
        ROCK,
        SAND
    }

    public record Cave(Map<Vec2i, Type> rocks, int floor, int minX, int maxX) {

        public Cave(Map<Vec2i, Type> rocks) {
            this(
                    rocks,
                    rocks.keySet().stream().mapToInt(Vec2i::y).max().orElse(0),
                    rocks.keySet().stream().mapToInt(Vec2i::x).min().orElse(0),
                    rocks.keySet().stream().mapToInt(Vec2i::x).max().orElse(0)
            );
        }

        public void put(Vec2i pos, Type type) {
            rocks.put(pos, type);
        }

        public boolean has(Vec2i pos) {
            return rocks.containsKey(pos);
        }

        public int getSandCount() {
            return rocks().values().stream().mapToInt(type -> type == Type.SAND ? 1 : 0).sum();
        }
    }

}
