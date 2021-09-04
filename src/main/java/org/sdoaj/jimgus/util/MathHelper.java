package org.sdoaj.jimgus.util;

public class MathHelper {
    public static float length(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static double length(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static float length(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
