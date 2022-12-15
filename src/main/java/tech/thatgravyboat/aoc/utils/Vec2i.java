package tech.thatgravyboat.aoc.utils;

import java.util.Objects;

public class Vec2i {
    protected int x;
    protected int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i relative(Direction direction) {
        return add(direction.getNormal());
    }

    public Vec2i add(Vec2i vec) {
        return new Vec2i(x + vec.x, y + vec.y);
    }

    public Vec2i add(int x, int y) {
        return add(new Vec2i(x, y));
    }

    public Vec2i add(Axis axis, int value) {
        return add(new Vec2i(axis == Axis.X ? value : 0, axis == Axis.Y ? value : 0));
    }


    public int distSqr(Vec2i pos) {
        int x = pos.x - this.x;
        int y = pos.y - this.y;
        return x * x + y * y;
    }

    public int distSqrt(Vec2i pos) {
        return (int) Math.sqrt(distSqr(pos));
    }

    public int distManhattan(Vec2i pos) {
        return Math.abs(this.x - pos.x) + Math.abs(this.y - pos.y);
    }

    public int xDiff(Vec2i pos) {
        return Math.abs(pos.x - x);
    }

    public int yDiff(Vec2i pos) {
        return Math.abs(pos.y - y);
    }

    public Vec2i.Mutable mutable() {
        return new Vec2i.Mutable(x, y);
    }

    public Vec2i toImmutable() {
        return new Vec2i(x, y);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isEqual(Vec2i vec, Axis axis) {
        return switch (axis) {
            case X -> x == vec.x;
            case Y -> y == vec.y;
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vec2i) obj;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static class Mutable extends Vec2i {

        public Mutable(int x, int y) {
            super(x, y);
        }

        public void set(Vec2i vec) {
            this.x = vec.x;
            this.y = vec.y;
        }

        @Override
        public Vec2i add(Vec2i vec) {
            this.x += vec.x;
            this.y += vec.y;
            return this;
        }
    }
}