package tech.thatgravyboat.aoc.utils;

import java.util.List;

/**
 * A simple int grid.
 */
public class IntGrid {

    private final int width;
    private final int depth;
    private final int[][] grid;

    public IntGrid(int depth, int width) {
        this.width = width;
        this.depth = depth;
        this.grid = new int[depth][width];
    }

    public int depth() {
        return depth;
    }

    public int width() {
        return width;
    }

    public int get(int x, int z) {
        return grid[z][x];
    }

    public void set(int x, int z, int value) {
        grid[z][x] = value;
    }

    public void setRow(int z, int[] row) {
        System.arraycopy(row, 0, grid[z], 0, row.length);
    }

    public boolean isWithinBounds(int x, int z) {
        return x >= 0 && z >= 0 && x < width && z < depth;
    }

    public List<int[]> directionsLRTB(int x, int z) {
        return List.of(leftOf(x, z), rightOf(x, z), topOf(x, z), bottomOf(x, z));
    }

    public List<int[]> directionsFrom(int x, int z) {
        return List.of(Util.reverseIntArray(leftOf(x, z)), rightOf(x, z), Util.reverseIntArray(topOf(x, z)), bottomOf(x, z));
    }

    public int[] leftOf(int x, int z) {
        int[] left = new int[x];
        System.arraycopy(grid[z], 0, left, 0, x);
        return left;
    }

    public int[] rightOf(int x, int z) {
        int[] right = new int[grid[z].length - x - 1];
        System.arraycopy(grid[z], x + 1, right, 0, grid[z].length - x - 1);
        return right;
    }

    public int[] topOf(int x, int z) {
        int[] top = new int[z];
        for (int i = 0; i < z; i++) {
            top[i] = grid[i][x];
        }
        return top;
    }

    public int[] bottomOf(int x, int z) {
        int[] bottom = new int[grid.length - z - 1];
        for (int i = z + 1; i < grid.length; i++) {
            bottom[i - z - 1] = grid[i][x];
        }
        return bottom;
    }

    public void forEach(IntGridConsumer consumer) {
        for (int i = 0; i < depth(); i++) {
            for (int j = 0; j < width(); j++) {
                consumer.accept(j, i, get(j, i));
            }
        }
    }

    @FunctionalInterface
    public interface IntGridConsumer {
        void accept(int x, int y, int value);
    }

}
