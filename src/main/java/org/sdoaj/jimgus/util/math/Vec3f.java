package org.sdoaj.jimgus.util.math;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;

public class Vec3f {
    public final float x, y, z;

    public static final float epsilon = 0.0001f;

    public static Vec3f XP = new Vec3f(1, 0, 0);
    public static Vec3f XN = new Vec3f(-1, 0, 0);
    public static Vec3f YP = new Vec3f(0, 1, 0);
    public static Vec3f YN = new Vec3f(0, -1, 0);
    public static Vec3f ZP = new Vec3f(0, 0, 1);
    public static Vec3f ZN = new Vec3f(0, 0, -1);

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public Vec3f(Vector3f v) {
        this(v.x(), v.y(), v.z());
    }

    public BlockPos toBlockPos() {
        return new BlockPos(this.x, this.y, this.z);
    }

    public Vector3f toVector3f() {
        return new Vector3f(this.x, this.y, this.z);
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

    public Vec3f multiply(float c) {
        return new Vec3f(this.x * c, this.y * c, this.z * c);
    }

    public Vec3f multiply(Vec3f other) {
        return new Vec3f(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vec3f divide(float c) {
        return new Vec3f(this.x / c, this.y / c, this.z / c);
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
        float length = this.length();
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

    public float angleTo(Vec3f other) {
        return (float) Math.acos(this.dot(other) / (this.length() * other.length()));
    }

    public Vec3f abs() {
        return new Vec3f(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    public Vec3f max(int c) {
        return new Vec3f(Math.max(this.x, c), Math.max(this.y, c), Math.max(this.z, c));
    }

    public Vec3f min(int c) {
        return new Vec3f(Math.min(this.x, c), Math.min(this.y, c), Math.min(this.z, c));
    }

    public float maxComp() {
        return Math.max(this.x, Math.max(this.y, this.z));
    }

    public float minComp() {
        return Math.min(this.x, Math.min(this.y, this.z));
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }

    // generated by IntelliJ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3f vec3f = (Vec3f) o;

        if (Float.compare(vec3f.x, x) != 0) return false;
        if (Float.compare(vec3f.y, y) != 0) return false;
        return Float.compare(vec3f.z, z) == 0;
    }

    public boolean equalsEpsilon(Vec3f other) {
        return equalsEpsilon(other, epsilon);
    }

    public boolean equalsEpsilon(Vec3f other, float epsilon) {
        return (Math.abs(this.x - other.x) < epsilon)
                && (Math.abs(this.y - other.y) < epsilon)
                && (Math.abs(this.z - other.z) < epsilon);
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }
}
