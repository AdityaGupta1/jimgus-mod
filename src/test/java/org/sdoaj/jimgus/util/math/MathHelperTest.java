package org.sdoaj.jimgus.util.math;

import org.junit.jupiter.api.Test;

import static org.sdoaj.jimgus.util.math.MathHelper.*;
import static org.junit.jupiter.api.Assertions.*;

class MathHelperTest {
    @Test
    public void testAngleBetweenSame() {
        assertEquals(0, angleBetween(0, 0), Vec3f.EPSILON);
        assertEquals(0, angleBetween(PI, PI), Vec3f.EPSILON);
        assertEquals(0, angleBetween(PI2, PI2), Vec3f.EPSILON);
    }

    @Test
    public void testAngleBetweenLessThan180() {
        assertEquals(PI / 4, angleBetween(0, PI / 4), Vec3f.EPSILON);
        assertEquals(PI / 2, angleBetween(PI / 2, PI), Vec3f.EPSILON);
        assertEquals(PI / 2, angleBetween(PI / 4, -PI / 4), Vec3f.EPSILON);
        assertEquals(PI / 2, angleBetween(PI2 - PI / 4, PI2 + PI / 4), Vec3f.EPSILON);
    }

    @Test
    public void testAngleBetweenMoreThan180() {
        assertEquals(0, angleBetween(0, PI2), Vec3f.EPSILON);
        assertEquals(PI / 2, angleBetween(0, 3 * PI / 2), Vec3f.EPSILON);
        assertEquals(PI / 4, angleBetween(PI, 99 * PI + PI / 4), Vec3f.EPSILON);
        assertEquals(PI, angleBetween(PI, -98 * PI), Vec3f.EPSILON);
        assertEquals(PI / 4, angleBetween(PI, -99 * PI - PI / 4), Vec3f.EPSILON);
    }
}