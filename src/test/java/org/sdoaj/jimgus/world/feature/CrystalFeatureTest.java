package org.sdoaj.jimgus.world.feature;

import org.junit.jupiter.api.Test;
import org.sdoaj.jimgus.util.math.Vec3f;

import static org.junit.jupiter.api.Assertions.*;

class CrystalFeatureTest {
//    @Test
    public void testCrystalRadius() {
        assertEquals(1, CrystalFeature.crystalRadius.apply(0f), Vec3f.EPSILON);
        assertEquals(1, CrystalFeature.crystalRadius.apply(CrystalFeature.coneStart), Vec3f.EPSILON);
        assertEquals(0, CrystalFeature.crystalRadius.apply(1f), Vec3f.EPSILON);
    }
}