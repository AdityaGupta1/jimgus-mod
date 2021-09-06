package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;

import java.util.function.UnaryOperator;

public class SDFLine extends SDFPrimitive {
    protected UnaryOperator<Float> radius;
    protected final Vec3f pos1;
    protected final Vec3f pos2;

    protected float rStart;
    protected float rEnd;
    protected boolean capStart = true;
    protected boolean capEnd = true;
    protected final Vec3f vecLine;
    protected final float length;

    public SDFLine(float x1, float y1, float z1, float x2, float y2, float z2) {
        this(new Vec3f(x1, y1, z1), new Vec3f(x2, y2, z2));
    }

    public SDFLine(Vec3f pos1, Vec3f pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;

        this.vecLine = pos2.subtract(pos1);
        this.length = vecLine.length();
    }

    public SDFLine radius(float radius) {
        this.radius = delta -> radius;
        this.rStart = this.rEnd = radius;
        return this;
    }

    public SDFLine radius(float r1, float r2) {
        this.radius = delta -> MathHelper.lerp(delta, r1, r2);
        this.rStart = r1;
        this.rEnd = r2;
        return this;
    }

    public SDFLine radius(UnaryOperator<Float> radius) {
        this.radius = radius;
        this.rStart = radius.apply(0f);
        this.rEnd = radius.apply(1f);
        return this;
    }

    // for use with splines
    public SDFLine radius(UnaryOperator<Float> radius, float min, float max) {
        return radius(delta -> radius.apply(MathHelper.lerp(delta, min, max)));
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
    public float distance(Vec3f pos) {
        Vec3f pointPos = pos.subtract(pos1);
        float proj = pointPos.dot(vecLine) / length;
        float ratio = proj / length;

        if (ratio < 0) {
            return capStart ? pos.distance(pos1) - rStart : 0; // 0 is not technically correct but it works
        } else if (ratio > 1) {
            return capEnd ? pos.distance(pos2) - rEnd : 0;
        }

        ratio = MathHelper.clamp(ratio, 0, 1);
        Vec3f pointLine = vecLine.normalize().multiply(proj); // point on the line
        Vec3f vecPointPos = pointPos.subtract(pointLine); // vector from pointLine to pointPos
        float distance = vecPointPos.length();
        float r = radius.apply(ratio);
        return distance - r;
    }
}
