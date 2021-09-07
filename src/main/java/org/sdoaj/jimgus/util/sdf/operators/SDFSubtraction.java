package org.sdoaj.jimgus.util.sdf.operators;

import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFSubtraction extends SDFBinary {
    @Override
    public float distance(Vec3f pos) {
        float a = this.sourceA.distance(pos);
        float b = this.sourceB.distance(pos);
        this.selectValue(a, b);
        return Math.max(a, -b);
    }
}
