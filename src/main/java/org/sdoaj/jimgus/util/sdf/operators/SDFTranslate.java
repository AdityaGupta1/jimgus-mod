package org.sdoaj.jimgus.util.sdf.operators;

public class SDFTranslate extends SDFUnary {
    protected float x, y, z;

    public SDFTranslate setTranslate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public float distance(float x, float y, float z) {
        return source.distance(x - this.x, y - this.y, z - this.z);
    }
}
