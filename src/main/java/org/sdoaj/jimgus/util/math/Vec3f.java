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

    public Vec3f divide(float v) {
        return new Vec3f(this.x / v, this.y / v, this.z / v);
    }

    public float dot(Vec3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vec3f normalize() {
        final float length = this.length();
        return new Vec3f(this.x / length, this.y / length, this.z / length);
    }

    public float distance(Vec3f other) {
        return this.subtract(other).length();
    }
}
