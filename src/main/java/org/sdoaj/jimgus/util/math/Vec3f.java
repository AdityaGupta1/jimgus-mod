package org.sdoaj.jimgus.util.math;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.CallbackI;

public class Vec3f {
    public final float x, y, z;

    public static Vec3f UP = new Vec3f(0, 1, 0);

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

    public float angleTo(Vec3f other) {
        return (float) Math.acos(this.dot(other) / (this.length() * other.length()));
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f)", x, y, z);
    }
}
