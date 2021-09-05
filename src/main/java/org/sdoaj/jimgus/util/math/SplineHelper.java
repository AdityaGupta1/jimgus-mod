package org.sdoaj.jimgus.util.math;

import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFAbstractShape;
import org.sdoaj.jimgus.util.sdf.primitives.SDFLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static void offsetPoints(List<Vec3f> spline, Random random, float dx, float dy, float dz) {
        offsetPoints(spline, random, dx, dy, dz, false, false);
    }

    public static void offsetPoints(List<Vec3f> spline, Random random, float dx, float dy, float dz, boolean offsetStart, boolean offsetEnd) {
        int start = offsetStart ? 0 : 1;
        int end = offsetEnd ? spline.size() : spline.size() - 1;
        for (int i = start; i < end; i++) {
            Vec3f point = spline.get(i);
            spline.set(i, point.add(new Vec3f((float) random.nextGaussian() * dx,
                    (float) random.nextGaussian() * dy,
                    (float) random.nextGaussian() * dz)));
        }
    }

    public static class SplineSDFBuilder {
        private final List<Vec3f> spline;
        private float r1;
        private float r2;
        private boolean capStart = true;
        private boolean capEnd = true;

        private SplineSDFBuilder(List<Vec3f> spline) {
            this.spline = spline;
        }

        public static SplineSDFBuilder from(List<Vec3f> spline) {
            return new SplineSDFBuilder(spline);
        }

        public SDFAbstractShape build() {
            float r1;
            float r2 = this.r1;
            SDF sdf = null;

            int points = spline.size();
            for (int i = 0; i < points - 1; i++) {
                r1 = r2; // radius at this point
                r2 = MathHelper.lerp((float) (i + 1) / (points - 1), this.r1, this.r2); // radius at next point
                SDFLine line = new SDFLine(spline.get(i), spline.get(i + 1), r1, r2);

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
            this.r1 = this.r2 = radius;
            return this;
        }

        public SplineSDFBuilder radius(float r1, float r2) {
            this.r1 = r1;
            this.r2 = r2;
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
