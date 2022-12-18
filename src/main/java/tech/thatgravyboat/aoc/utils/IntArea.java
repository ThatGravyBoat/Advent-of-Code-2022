package tech.thatgravyboat.aoc.utils;

import java.util.OptionalInt;

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

    public int getWidth() {
        return cube.length;
    }

    public int getHeight() {
        return cube[0].length;
    }

    public int getDepth() {
        return cube[0][0].length;
    }

    public int[][][] getCube() {
        return cube;
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

    @FunctionalInterface
    public interface IntAreaConsumer {
        void accept(int x, int y, int z, int value);
    }
}
