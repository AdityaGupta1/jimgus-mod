package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFNgonPrism extends SDFCylinder {
    private int sides;
    private float rotation; // rotation around y axis

    public SDFNgonPrism(float height) {
        super(height);
    }

    public SDFNgonPrism(float height, boolean centered) {
        super(height, centered);
    }

    public SDFNgonPrism sides(int sides) {
        this.sides = sides;
        return this;
    }

    // rotation is in radians
    public SDFNgonPrism rotate(float rotation) {
        this.rotation = rotation;
        return this;
    }

    @Override
    protected float getDistanceRadius(Vec3f vecPointPos, float ratio) {
        float distance = vecPointPos.length();
        float angle = (distance == 0) ? 0 : (vecPointPos.angleTo(Vec3f.XP) - rotation + (float) (2 * Math.PI));

        float r = this.getRadius(ratio);
        float p = (float) (Math.PI / sides);
        r *= (Math.cos(p) / Math.cos(p - (angle % (2 * p)))); // https://www.youtube.com/watch?v=OG9olLlKB8Q (simplified)
        return distance - r;
    }

//    @Override
//    public float distance(Vec3f pos) {
//        Vec3f pointPos = pos.subtract(pos1);
//        float ratio = pointPos.y / length;
//
//        Vec3f vecPointPos = pointPos.multiply(new Vec3f(1, 0, 1));
//        float distance = vecPointPos.length();
//        float angle = (distance == 0) ? 0 : (vecPointPos.angleTo(Vec3f.XP) - rotation + (float) (2 * Math.PI));
//
//        if (!MathHelper.isInRange(ratio, 0, 1)) {
//            return -1; // not technically correct but it works
//        }
//
//        float r = this.getRadius(ratio);
//        float p = (float) (Math.PI / sides);
//        r *= (Math.cos(p) / Math.cos(p - (angle % (2 * p)))); // https://www.youtube.com/watch?v=OG9olLlKB8Q (simplified)
//        return distance - r;
//    }
}
