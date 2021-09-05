package org.sdoaj.jimgus.util.math;

public class MathHelper {
    public static float length(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static float length(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public static float clamp(float x, float min, float max) {
        return Math.min(Math.max(x, min), max);
    }

    public static boolean isInRange(float x, float min, float max) {
        return isInRange(x, min, max, false);
    }

    public static boolean isInRange(float x, float min, float max, boolean inclusive) {
        return inclusive ? (x >= min && x <= max) : (x > min && x < max);
    }

    public static float lerp(float x, float min, float max) {
        return (max - min) * x + min;
    }
}
