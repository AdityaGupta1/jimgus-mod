package org.sdoaj.jimgus.util.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vec3fTest {
    @Test
    public void testCross() {
        assertTrue(Vec3f.XP.equalsEpsilon(Vec3f.YP.cross(Vec3f.ZP)));
    }
}