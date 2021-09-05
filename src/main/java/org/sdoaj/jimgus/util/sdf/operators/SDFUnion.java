package org.sdoaj.jimgus.util.sdf.operators;

public class SDFUnion extends SDFBinary {
    @Override
    public float distance(float x, float y, float z) {
        float a = this.sourceA.distance(x, y, z);
        float b = this.sourceB.distance(x, y, z);
        this.selectValue(a, b);
        return Math.min(a, b);
    }
}
