package org.sdoaj.jimgus.util.sdf.primitives;

import org.junit.jupiter.api.Test;
import org.sdoaj.jimgus.util.math.Vec3f;

import static org.junit.jupiter.api.Assertions.*;

class SDFLineTest {
    @Test
    public void testDistanceInside() {
        SDFLine sdf = new SDFLine(new Vec3f(0, 0, 0), new Vec3f(0, 10, 0)).radius(4).disableCaps();

        assertEquals(0, sdf.distance(new Vec3f(0, 0, 0)), Vec3f.EPSILON);
        assertEquals(0, sdf.distance(new Vec3f(0, 10, 0)), Vec3f.EPSILON);

        assertEquals(0, sdf.distance(new Vec3f(4, 5, 0)), Vec3f.EPSILON);

        assertEquals(-4, sdf.distance(new Vec3f(0, 5, 0)), Vec3f.EPSILON);
        assertEquals(-2, sdf.distance(new Vec3f(0, 2, 0)), Vec3f.EPSILON);
    }

    @Test
    public void testDistanceOutside() {
        SDFLine sdf = new SDFLine(new Vec3f(0, 0, 0), new Vec3f(0, 10, 0)).radius(4).disableCaps();

        assertEquals(1, sdf.distance(new Vec3f(0, -1, 0)), Vec3f.EPSILON);
        assertEquals(1, sdf.distance(new Vec3f(0, 11, 0)), Vec3f.EPSILON);

        assertEquals(1, sdf.distance(new Vec3f(5, 0, 0)), Vec3f.EPSILON);
        assertEquals(1, sdf.distance(new Vec3f(5, 5, 0)), Vec3f.EPSILON);
        assertEquals(1, sdf.distance(new Vec3f(5, 10, 0)), Vec3f.EPSILON);
    }

    @Test
    public void testDistanceOutsideFar() {
        SDFLine sdf1 = new SDFLine(new Vec3f(0, 0, 0), new Vec3f(0, 1000, 0)).radius(4).disableCaps();

        assertEquals(100, sdf1.distance(new Vec3f(104, 500, 0)), Vec3f.EPSILON);

        SDFLine sdf2 = new SDFLine(new Vec3f(0, 0, 0), new Vec3f(0, 2, 0)).radius(4).disableCaps();

        assertEquals(100, sdf2.distance(new Vec3f(104, 1, 0)), Vec3f.EPSILON);
    }

    @Test
    public void testDistanceOutsideDiagonal() {
        SDFLine sdf = new SDFLine(new Vec3f(0, 0, 0), new Vec3f(0, 10, 0)).radius(4).disableCaps();

        assertEquals(Math.sqrt(2), sdf.distance(new Vec3f(5, 11, 0)), Vec3f.EPSILON);
    }
}