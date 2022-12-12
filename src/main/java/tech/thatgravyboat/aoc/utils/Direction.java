package tech.thatgravyboat.aoc.utils;

public enum Direction {
    UP(new Vec2i(0, -1)),
    DOWN(new Vec2i(0, 1)),
    LEFT(new Vec2i(-1, 0)),
    RIGHT(new Vec2i(1, 0));

    private final Vec2i normal;

    Direction(Vec2i normal) {
        this.normal = normal;
    }

    public Vec2i getNormal() {
        return normal;
    }
}