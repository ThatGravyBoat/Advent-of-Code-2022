package tech.thatgravyboat.aoc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class IntArea {

    private final int[][][] cube;

    public IntArea(int width, int height, int depth) {
        this.cube = new int[width][height][depth];
    }

    public int get(int x, int y, int z) {
        return cube[x][y][z];
    }

    public OptionalInt safeGet(int x, int y, int z) {
        if (!isWithinBounds(x, y, z)) return OptionalInt.empty();
        return OptionalInt.of(get(x, y, z));
    }

    public int get(Vec3i vec3i) {
        return get(vec3i.x(), vec3i.y(), vec3i.z());
    }

    public OptionalInt safeGet(Vec3i vec3i) {
        return safeGet(vec3i.x(), vec3i.y(), vec3i.z());
    }

    public void set(int x, int y, int z, int value) {
        cube[x][y][z] = value;
    }

    public void set(Vec3i vec3i, int value) {
        set(vec3i.x(), vec3i.y(), vec3i.z(), value);
    }

    public boolean isWithinBounds(int x, int y, int z) {
        return x >= 0 && x < cube.length && y >= 0 && y < cube[0].length && z >= 0 && z < cube[0][0].length;
    }

    public void forEach(IntAreaConsumer consumer) {
        for (int x = 0; x < cube.length; x++) {
            int[][] yzList = cube[x];
            for (int y = 0; y < yzList.length; y++) {
                int[] zList = yzList[y];
                for (int z = 0; z < zList.length; z++) {
                    consumer.accept(x, y, z, zList[z]);
                }
            }
        }
    }

    public Stream<AreaEntry> stream() {
        List<AreaEntry> entries = new ArrayList<>();
        forEach((x, y, z, value) -> entries.add(new AreaEntry(x, y, z, value)));
        return entries.stream();
    }

    @FunctionalInterface
    public interface IntAreaConsumer {
        void accept(int x, int y, int z, int value);
    }

    public record AreaEntry(int x, int y, int z, int value) {

        public Vec3i toVec3i() {
            return new Vec3i(x, y, z);
        }
    }
}
