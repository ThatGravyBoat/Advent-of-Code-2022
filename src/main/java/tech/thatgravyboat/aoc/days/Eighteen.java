package tech.thatgravyboat.aoc.days;

import tech.thatgravyboat.aoc.templates.Template;
import tech.thatgravyboat.aoc.utils.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Eighteen extends Template {

    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+),(\\d+)");
    private static final int DROPLET = 1;
    private static final int WATER = 0;
    private static final int EXTERIOR_WATER = 2;

    public static void main(String[] args) {
        new Eighteen().load(18);
    }

    private IntArea area;

    @Override
    protected void onInputLoaded() {
        List<Vec3i> droplets = Util.find(PATTERN, getInput(), matcher -> new Vec3i(matcher.group(1), matcher.group(2), matcher.group(3)));

        area = new IntArea(
            droplets.stream().mapToInt(Vec3i::x).max().orElseThrow() + 1,
            droplets.stream().mapToInt(Vec3i::y).max().orElseThrow() + 1,
            droplets.stream().mapToInt(Vec3i::z).max().orElseThrow() + 1
        );
        droplets.forEach(vec3i -> area.set(vec3i, DROPLET));
    }


    @Override
    public String partOne() {
        return Integer.toString(solve(WATER));
    }

    @Override
    public String partTwo() {
        //Basic BSF that will flood fill the exposed water with "exterior water"
        //Then when we solve instead of checking for water we only check for "exterior water"
        Util.bsf(Vec3i.ZERO, (vec, queue) -> {
            this.area.set(vec, EXTERIOR_WATER);
            Direction3d.stream()
                .map(vec::add)
                .filter(pos -> this.area.safeGet(pos).orElse(-1) == WATER)
                .forEach(queue::add);
        });

        return Integer.toString(solve(EXTERIOR_WATER));
    }

    public int solve(int target) {
        AtomicInteger surfaceArea = new AtomicInteger();

        area.forEach((x, y, z, value) -> {
            // Loop through everything and if it's a droplet then calculate the surface area
            if (value == DROPLET) {
                Vec3i pos = new Vec3i(x, y, z);
                // Loop through all directions and filter depending on the target
                // And then increment depending on how many filtered directions there are
                Direction3d.stream()
                    .map(pos::add)
                    .filter(pos2 -> area.safeGet(pos2).orElse(target) == target)
                    .forEach(pos2 -> surfaceArea.getAndIncrement());
            }
        });

        return surfaceArea.get();
    }
}
