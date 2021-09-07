package org.sdoaj.jimgus.util.sdf.operators;

import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFSubtraction extends SDFBinary {
    private boolean isBoolean = false;

    @Override
    public float distance(Vec3f pos) {
        float a = this.sourceA.distance(pos);
        float b = this.sourceB.distance(pos);

        if (isBoolean) {
            this.firstValue = true;
            return b < 0 ? 0 : a;
        }

        this.selectValue(a, b);
        return Math.max(a, -b);
    }

    public SDFSubtraction setBoolean() {
        this.isBoolean = true;
        return this;
    }
}
