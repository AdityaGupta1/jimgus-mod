package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.sdf.SDF;

/**
 * not really a "primitive" but it's an easy way to override any SDF with a simple block placement
 * function
 */
public class SDFAbstractShape extends SDFPrimitive {
    private final SDF sdf;

    public SDFAbstractShape(SDF sdf) {
        this.sdf = sdf;
    }

    @Override
    public float distance(float x, float y, float z) {
        return sdf.distance(x, y, z);
    }
}
