package org.sdoaj.jimgus.util.sdf.operators;

import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFUnion extends SDFBinary {
    @Override
    public float distance(Vec3f pos) {
        float a = this.sourceA.distance(pos);
        float b = this.sourceB.distance(pos);
        this.selectValue(a, b);
        return Math.min(a, b);
    }
}
