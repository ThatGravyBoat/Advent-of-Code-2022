package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.IntMatcher;
import tech.thatgravyboat.aoc.utils.Range;
import tech.thatgravyboat.aoc.utils.Util;
import tech.thatgravyboat.aoc.utils.Vec2i;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class Fifteen extends Template {

    private static final Pattern PATTERN = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

    public static void main(String[] args) {
        new Fifteen().load(15);
    }

    private final Map<Vec2i, Vec2i> positions = new HashMap<>();
    private final Map<Vec2i, Integer> distances = new HashMap<>();

    @Override
    protected void onInputLoaded() {
        for (IntMatcher match : Util.find(PATTERN, getInput())) {
            positions.put(
                new Vec2i(match.group(1), match.group(2)),
                new Vec2i(match.group(3), match.group(4))
            );
        }
        positions.forEach((pos, beacon) -> distances.put(pos, pos.distManhattan(beacon)));
    }

    @Override
    public String partOne() {
        final int y = 2000000;

        List<Range> ranges = getRange(y);

        HashSet<Vec2i> set = new HashSet<>();
        ranges.forEach(range -> range.forEach(i -> set.add(new Vec2i(i, y))));
        set.removeAll(positions.values());
        return Integer.toString(set.size());
    }

    @Override
    public String partTwo() {
        final BigInteger maxRange = BigInteger.valueOf(4000000);

        for (int i = 0; i <= 4000000; i++) {
            int max = -1;
            for (Range range : getRange(i)) {
                if (max == -1) {
                    max = range.max();
                } else {
                    if (max + 1 < range.min()) {
                        return maxRange
                            .multiply(BigInteger.valueOf(max + 1))
                            .add(BigInteger.valueOf(i))
                            .toString();
                    } else {
                        max = Math.max(max, range.max());
                    }
                }
            }
        }

        return "No solution found";
    }

    private List<Range> getRange(int y) {
        List<Range> ranges = new ArrayList<>();
        distances.forEach((pos, dist) -> {
            int range = dist - Math.abs(y - pos.y());
            if (range >= 0) {
                ranges.add(new Range(pos.x() - range, pos.x() + range));
            }
        });
        ranges.sort(Range::compareTo);
        return ranges;
    }

}
