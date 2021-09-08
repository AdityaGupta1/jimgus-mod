package org.sdoaj.jimgus.util.math;

import java.util.Random;

public class MathHelper {
    public static float PI = (float) Math.PI;

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
        return isInRange(v, min, max, true);
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

    public static int nextInt(Random random, int min, int max) {
        return random.nextInt(max - min) + min;
    }

    // abs(value) <= max
    public static int nextIntAbs(Random random, int max) {
        return nextInt(random, -max, max + 1);
    }

    public static float nextFloat(Random random, float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static float nextFloatOne(Random random) {
        return nextFloat(random, -1f, 1f);
    }

    public static float nextFloatAbs(Random random, float max) {
        return nextFloatOne(random) * max;
    }

    public static float nextFloatAbs(Random random, float min, float max) {
        return nextFloatOne(random) * (max - min) + min;
    }

    public static boolean chance(Random random, float chance) {
        return random.nextFloat() < chance;
    }

    public static boolean chance(Random random, float min, float max) {
        float c = random.nextFloat();
        return min <= c && c < max;
    }

    public static float toRadians(float degrees) {
        return degrees / 180 * PI;
    }

    public static float toDegrees(float radians) {
        return radians / PI * 180;
    }
}
