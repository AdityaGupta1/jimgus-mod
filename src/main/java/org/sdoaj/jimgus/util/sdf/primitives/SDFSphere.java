package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.MathHelper;

public class SDFSphere extends SDFPrimitive {
    private final float radius;

    public SDFSphere(float radius) {
        this.radius = radius;
    }

    @Override
    public float distance(float x, float y, float z) {
        return MathHelper.length(x, y, z) - radius;
    }
}
