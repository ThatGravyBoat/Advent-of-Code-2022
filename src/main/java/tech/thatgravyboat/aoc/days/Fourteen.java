package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.Direction;
import tech.thatgravyboat.aoc.utils.IntMatcher;
import tech.thatgravyboat.aoc.utils.Vec2i;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Fourteen extends Template {

    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+)(?: -> )?");

    public static void main(String[] args) {
        new Fourteen().load(14);
    }

    @Override
    public String partOne() {
        Map<Vec2i, Type> rocks = getCave();
        int floor = rocks.keySet().stream().mapToInt(Vec2i::y).max().orElse(0);

        solve(rocks, vec -> vec.y() > floor, vec -> false);

        return Integer.toString(rocks.values().stream().mapToInt(type -> type == Type.SAND ? 1 : 0).sum());
    }

    @Override
    public String partTwo() {
        Map<Vec2i, Type> rocks = getCave();
        int floor = rocks.keySet().stream().mapToInt(Vec2i::y).max().orElse(0);
        int minX = rocks.keySet().stream().mapToInt(Vec2i::x).min().orElse(0);
        int maxX = rocks.keySet().stream().mapToInt(Vec2i::x).max().orElse(0);

        for (int i = minX - 1000; i <= maxX + 1000; i++) {
            rocks.put(new Vec2i(i, floor + 2), Type.ROCK);
        }

        solve(rocks, vec -> false, vec -> vec.x() == 500 && vec.y() == 0);

        return Integer.toString(rocks.values().stream().mapToInt(type -> type == Type.SAND ? 1 : 0).sum());
    }

    private void solve(Map<Vec2i, Type> cave, Function<Vec2i, Boolean> atStart, Function<Vec2i, Boolean> atEnd) {
        while (true) {
            Vec2i.Mutable start = new Vec2i.Mutable(500, 0);
            while (true) {
                if (atStart.apply(start)) {
                    return;
                }
                Vec2i pos = start.toImmutable();

                if (!cave.containsKey(pos.relative(Direction.DOWN))) {
                    start.relative(Direction.DOWN);
                    continue;
                }

                if (!cave.containsKey(pos.relative(Direction.DOWN).relative(Direction.LEFT))) {
                    start.relative(Direction.DOWN).relative(Direction.LEFT);
                    continue;
                }

                if (!cave.containsKey(pos.relative(Direction.DOWN).relative(Direction.RIGHT))) {
                    start.relative(Direction.DOWN).relative(Direction.RIGHT);
                    continue;
                }

                cave.put(start.toImmutable(), Type.SAND);
                if (atEnd.apply(start)) {
                    return;
                }
                break;
            }
        }
    }

    private Map<Vec2i, Type> getCave() {
        Map<Vec2i, Type> cave = new HashMap<>();
        for (String s : getInput()) {
            IntMatcher matcher = IntMatcher.find(PATTERN, s);
            //noinspection ResultOfMethodCallIgnored
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
        return cave;
    }

    public enum Type {
        ROCK,
        SAND
    }

}
