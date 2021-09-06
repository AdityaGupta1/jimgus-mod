package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFSphere extends SDFPrimitive {
    private final float radius;

    public SDFSphere(float radius) {
        this.radius = radius;
    }

    @Override
    public float distance(Vec3f pos) {
        return pos.length() - radius;
    }
}
