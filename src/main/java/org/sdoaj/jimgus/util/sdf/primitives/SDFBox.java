package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.MathHelper;

// https://www.youtube.com/watch?v=62-pRVZuS5c
// TODO doesn't work
public class SDFBox extends SDFPrimitive {
    private final float rx, ry, rz; // radius x, y, z

    public SDFBox(float rx, float ry, float rz) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
    }

    @Override
    public float distance(float x, float y, float z) {
        float dx = Math.abs(x - rx);
        float dy = Math.abs(y - ry);
        float dz = Math.abs(z - rz);

        // outside distance + inside distance
        return MathHelper.length(Math.max(dx, 0), Math.max(dy, 0), Math.max(dz, 0))
                + Math.min(Math.max(Math.max(dx, dy), dz), 0);
    }
}
