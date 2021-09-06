package org.sdoaj.jimgus.util.math;

import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFAbstractShape;
import org.sdoaj.jimgus.util.sdf.primitives.SDFLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

// based partly on https://github.com/paulevsGitch/BCLib/blob/main/src/main/java/ru/bclib/util/SplineHelper.java
public class SplineHelper {
    public static List<Vec3f> makeSpline(float x1, float y1, float z1, float x2, float y2, float z2, int points) {
        Vec3f pos1 = new Vec3f(x1, y1, z1);
        Vec3f pos2 = new Vec3f(x2, y2, z2);

        List<Vec3f> spline = new ArrayList<>();
        spline.add(pos1);

        int midpoints = points - 1;
        for (int i = 1; i < midpoints; i++) {
            float delta = ((float) i) / midpoints;
            spline.add(Vec3f.lerp(delta, pos1, pos2));
        }

        spline.add(pos2);
        return spline;
    }

    // supplier allows the caller to choose between uniform, normal, or any other distribution
    public static void offsetPoints(List<Vec3f> spline, Supplier<Float> random, float dx, float dy, float dz) {
        offsetPoints(spline, random, dx, dy, dz, false, false);
    }

    public static void offsetPoints(List<Vec3f> spline, Supplier<Float> random, float dx, float dy, float dz, boolean offsetStart, boolean offsetEnd) {
        int start = offsetStart ? 0 : 1;
        int end = offsetEnd ? spline.size() : spline.size() - 1;
        for (int i = start; i < end; i++) {
            Vec3f point = spline.get(i);
            spline.set(i, point.add(new Vec3f(random.get() * dx, random.get() * dy, random.get() * dz)));
        }
    }

    public static List<Vec3f> bezier(List<Vec3f> spline, int points) {
        List<Vec3f> newSpline = new ArrayList<>();
        newSpline.add(spline.get(0));

        for (int i = 0; i < points - 2; i++) {
            newSpline.add(getBezierPoint(spline, ((float) i + 1) / (points - 1)));
        }

        newSpline.add(spline.get(spline.size() - 1));
        return newSpline;
    }

    private static Vec3f getBezierPoint(List<Vec3f> spline, float t) {
        List<Vec3f> oldPoints = new ArrayList<>(spline);
        List<Vec3f> newPoints = new ArrayList<>();
        int points = spline.size();

        while (points > 1) {
            for (int i = 0; i < points - 1; i++) {
                newPoints.add(Vec3f.lerp(t, oldPoints.get(i), oldPoints.get(i + 1)));
            }

            oldPoints.clear();
            oldPoints.addAll(newPoints);
            newPoints.clear();
            points--;
        }

        return oldPoints.get(0);
    }

    public static Vec3f getEndpoint(List<Vec3f> spline) {
        return spline.get(spline.size() - 1);
    }

    public static class SplineSDFBuilder {
        private final List<Vec3f> spline;
        private UnaryOperator<Float> radius;
        private boolean capStart = true;
        private boolean capEnd = true;

        private SplineSDFBuilder(List<Vec3f> spline) {
            this.spline = spline;
        }

        public static SplineSDFBuilder from(List<Vec3f> spline) {
            return new SplineSDFBuilder(spline);
        }

        public SDFAbstractShape build() {
//            float r1;
//            float r2 = this.radius.apply(0f);
            SDF sdf = null;

            int points = spline.size();
            final int count = points - 1;
            for (int i = 0; i < count; i++) {
//                r1 = r2; // radius at this point
//                r2 = this.radius.apply((float) (i + 1) / count); // radius at next point
//                SDFLine line = new SDFLine(spline.get(i), spline.get(i + 1)).radius(r1, r2);
                SDFLine line = new SDFLine(spline.get(i), spline.get(i + 1)).radius(this.radius,
                        ((float) i) / count, ((float) (i + 1)) / count);

                if (i == 0 && !capStart) {
                    line.disableCapStart();
                }

                if (i == points - 2 && !capEnd) {
                    line.disableCapEnd();
                }

                sdf = (sdf == null) ? line : new SDFUnion().setSourceA(sdf).setSourceB(line);
            }

            return new SDFAbstractShape(sdf);
        }

        public SplineSDFBuilder radius(float radius) {
            this.radius = delta -> radius;
            return this;
        }

        public SplineSDFBuilder radius(float r1, float r2) {
            this.radius = delta -> MathHelper.lerp(delta, r1, r2);
            return this;
        }

        public SplineSDFBuilder radius(UnaryOperator<Float> radius) {
            this.radius = radius;
            return this;
        }

        public SplineSDFBuilder disableCapStart() {
            this.capStart = false;
            return this;
        }

        public SplineSDFBuilder disableCapEnd() {
            this.capEnd = false;
            return this;
        }

        public SplineSDFBuilder disableCaps() {
            this.capStart = this.capEnd = false;
            return this;
        }
    }
}
