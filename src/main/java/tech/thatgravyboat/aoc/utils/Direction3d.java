package tech.thatgravyboat.aoc.utils;

import java.util.stream.Stream;

public enum Direction3d {
    UP(new Vec3i(0, -1, 0)),
    DOWN(new Vec3i(0, 1, 0)),
    NORTH(new Vec3i(0, 0, -1)),
    SOUTH(new Vec3i(0, 0, 1)),
    EAST(new Vec3i(-1, 0, 0)),
    WEST(new Vec3i(1, 0, 0));

    private final Vec3i normal;

    Direction3d(Vec3i normal) {
        this.normal = normal;
    }

    public Vec3i getNormal() {
        return normal;
    }

    public Direction3d opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;

            case SOUTH -> NORTH;
            case NORTH -> SOUTH;

            case WEST -> EAST;
            case EAST -> WEST;
        };
    }

    public static Stream<Direction3d> stream() {
        return Stream.of(values());
    }
}