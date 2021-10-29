package org.sdoaj.jimgus.util;

import org.junit.jupiter.api.Test;
import org.sdoaj.jimgus.util.math.Vec3f;

import static org.junit.jupiter.api.Assertions.*;

class BlockHelperTest {
    @Test
    public void testPlane() {
        BlockHelper.Plane plane = new BlockHelper.Plane(new Vec3f(0, 0, 0), new Vec3f(0, 1, 0));

        assertTrue(plane.isPointInFront(new Vec3f(0, 2, 0)));
        assertEquals(2, plane.distanceToPoint(new Vec3f(0, 2, 0)), Vec3f.EPSILON);
    }
}