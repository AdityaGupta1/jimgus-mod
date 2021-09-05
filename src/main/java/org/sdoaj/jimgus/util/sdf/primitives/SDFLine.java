package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFLine extends SDFPrimitive {
    private final float r1, r2;
    private boolean capStart = true;
    private boolean capEnd = true;

    private final Vec3f pos1;
    private final Vec3f pos2;
    private final Vec3f vecLine;
    private final float length;

    public SDFLine(float x1, float y1, float z1, float x2, float y2, float z2, float r1, float r2) {
        this(new Vec3f(x1, y1, z1), new Vec3f(x2, y2, z2), r1, r2);
    }

    public SDFLine(Vec3f pos1, Vec3f pos2, float r1, float r2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.r1 = r1;
        this.r2 = r2;

        this.vecLine = pos2.subtract(pos1);
        this.length = vecLine.length();
    }

    public SDFLine disableCapStart() {
        this.capStart = false;
        return this;
    }

    public SDFLine disableCapEnd() {
        this.capEnd = false;
        return this;
    }

    public SDFLine disableCaps() {
        this.capStart = this.capEnd = false;
        return this;
    }

    @Override
    public float distance(float x, float y, float z) {
        Vec3f pos = new Vec3f(x ,y, z);
        Vec3f vecPoint = pos.subtract(pos1);
        float proj = vecPoint.dot(vecLine) / length;
        float ratio = proj / length;

        if (ratio < 0) {
            return capStart ? pos.distance(pos1) - r1 : 0; // 0 is not technically correct but it works
        } else if (ratio > 1) {
            return capEnd ? pos.distance(pos2) - r2 : 0;
        }

        float r = MathHelper.lerp(MathHelper.clamp(ratio, 0, 1), r1, r2);
        float distance = vecLine.normalize().multiply(proj).distance(vecPoint);
        return distance - r;
    }
}
