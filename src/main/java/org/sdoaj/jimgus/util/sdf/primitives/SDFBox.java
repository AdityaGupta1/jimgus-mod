package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.Vec3f;

// https://www.youtube.com/watch?v=62-pRVZuS5c
public class SDFBox extends SDFPrimitive {
    private final Vec3f r; // radius x, y, z

    public SDFBox(float r) {
        this(r, r, r);
    }

    public SDFBox(float rx, float ry, float rz) {
        this.r = new Vec3f(rx, ry, rz);
    }

    @Override
    public float distance(Vec3f pos) {
        Vec3f q = pos.abs().subtract(this.r);
        return q.max(0).length() + Math.min(q.maxComp(), 0);
    }
}
