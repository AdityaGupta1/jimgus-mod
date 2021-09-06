package org.sdoaj.jimgus.util.math;

import java.util.Random;

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

    public static boolean isInRange(float v, float min, float max) {
        return isInRange(v, min, max, false);
    }

    public static boolean isInRange(float v, float min, float max, boolean inclusive) {
        return inclusive ? (v >= min && v <= max) : (v > min && v < max);
    }

    public static float lerp(float delta, float min, float max) {
        return (max - min) * delta + min;
    }

    public static float mapRange(float delta, float min1, float max1, float min2, float max2) {
        return lerp((delta - min1) / (max1 - min1), min2, max2);
    }

    public static int nextIntInRange(Random random, int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static float nextFloatInRange(Random random, float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static float nextFloatInRangeOne(Random random) {
        return nextFloatInRange(random, -1, 1);
    }
}
