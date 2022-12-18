package tech.thatgravyboat.aoc.utils;

public record Vec3i(int x, int y, int z) {

    public static final Vec3i ZERO = new Vec3i(0, 0, 0);

    public Vec3i add(int x, int y, int z) {
        return new Vec3i(this.x + x, this.y + y, this.z + z);
    }

    public Vec3i add(Vec3i vec3i) {
        return add(vec3i.x(), vec3i.y(), vec3i.z());
    }

    public Vec3i add(Direction3d direction) {
        return add(direction.getNormal());
    }
}
