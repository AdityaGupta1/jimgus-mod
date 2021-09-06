package org.sdoaj.jimgus.util.math;

public class Vec3f {
    public final float x, y, z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float length() {
        return MathHelper.length(this.x, this.y, this.z);
    }

    public Vec3f add(Vec3f other) {
        return new Vec3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec3f subtract(Vec3f other) {
        return new Vec3f(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vec3f multiply(float v) {
        return new Vec3f(this.x * v, this.y * v, this.z * v);
    }

    public Vec3f multiply(Vec3f other) {
        return new Vec3f(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vec3f divide(float v) {
        return new Vec3f(this.x / v, this.y / v, this.z / v);
    }

    public Vec3f divide(Vec3f other) {
        return new Vec3f(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public float dot(Vec3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vec3f cross(Vec3f other) {
        return new Vec3f(this.y * other.z - this.z * other.y,
                this.x * other.z - this.z * other.x,
                this.x * other.y - this.y * other.x);
    }

    public Vec3f normalize() {
        final float length = this.length();
        return new Vec3f(this.x / length, this.y / length, this.z / length);
    }

    public float distance(Vec3f other) {
        return this.subtract(other).length();
    }

    public static Vec3f lerp(float delta, Vec3f v1, Vec3f v2) {
        return new Vec3f(MathHelper.lerp(delta, v1.x, v2.x),
                MathHelper.lerp(delta, v1.y, v2.y),
                MathHelper.lerp(delta, v1.z, v2.z));
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }
}
