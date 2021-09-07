package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;

/**
 * not really a "primitive" but it's an easy way to override any SDF with a simple block placement
 * function
 */
public class SDFAbstractShape extends SDFPrimitive {
    private SDF sdf;

    public SDFAbstractShape setSource(SDF sdf) {
        this.sdf = sdf;
        return this;
    }

    @Override
    public float distance(Vec3f pos) {
        return sdf.distance(pos);
    }
}
