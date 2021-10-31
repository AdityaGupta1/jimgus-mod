package org.sdoaj.jimgus.util.sdf.primitives;

import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;

import java.util.function.UnaryOperator;

public class SDFLine extends SDFPrimitive {
    private UnaryOperator<Float> radius;
    private float radiusMultiplier = 1f;
    protected final Vec3f pos1;
    protected final Vec3f pos2;

    private float sides = 0;
    private UnaryOperator<Float> rotation = null;

    protected float rStart;
    protected float rEnd;
    protected boolean capStart = true;
    protected boolean capEnd = true;
    protected final Vec3f vecLine;
    protected final Vec3f vecLinePerpendicular;
    protected final float length;

    public SDFLine(float x1, float y1, float z1, float x2, float y2, float z2) {
        this(new Vec3f(x1, y1, z1), new Vec3f(x2, y2, z2));
    }

    public SDFLine(Vec3f pos1, Vec3f pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;

        this.vecLine = pos2.subtract(pos1);
        Vec3f perpendicular = vecLine.cross(Vec3f.ZP).normalize();
        // NOTE: might be kind of bad
        this.vecLinePerpendicular = perpendicular.length() == 0 ? Vec3f.XP : perpendicular;

        this.length = vecLine.length();
    }

    public SDFLine radius(float radius) {
        return this.radius(delta -> radius);
    }

    public SDFLine radius(float r1, float r2) {
        return this.radius(delta -> MathHelper.lerp(delta, r1, r2));
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

    public SDFLine radiusMultiplier(float multiplier) {
        this.radiusMultiplier = multiplier;
        return this;
    }

    public SDFLine sides(int sides) {
        this.sides = sides;
        return this;
    }

    public SDFLine rotation(float rotation) {
        return this.rotation(delta -> rotation);
    }

    public SDFLine rotation(float rotation1, float rotation2) {
        return this.rotation(delta -> MathHelper.lerp(delta, rotation1, rotation2));
    }

    public SDFLine rotation(UnaryOperator<Float> rotation) {
        this.rotation = rotation;
        return this;
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

    public final float getRadius(float delta) {
        return this.radius.apply(delta) * this.radiusMultiplier;
    }

    protected float getDistanceRadius(Vec3f vecPointPos, float ratio) {
        float length = vecPointPos.length();

        if (sides == 0) {
            return length - this.getRadius(MathHelper.clamp(ratio, 0, 1));
        } else {
            float angle;
            if (length == 0) {
                angle = 0;
            } else {
                angle = vecPointPos.angleTo(vecLinePerpendicular) + MathHelper.PI2;
                if (rotation != null) {
                    angle -= rotation.apply(ratio);
                }
            }

            float r = this.getRadius(ratio);
            float p = (float) (Math.PI / sides);
            r *= (Math.cos(p) / Math.cos(p - (angle % (2 * p)))); // https://www.youtube.com/watch?v=OG9olLlKB8Q (simplified)
            return length - r;
        }
    }

    /*
     NOTE: This is probably somewhat incorrect when compared to the actual solution but it allows
           for the various customization options that I need.

           For example, it won't work properly if the radius changes significantly over a small distance
           since it doesn't check for diagonal distance when checking distance from the radius.
    */
    @Override
    public float distance(Vec3f pos) {
        Vec3f pointPos = pos.subtract(pos1);
        float proj = pointPos.dot(vecLine) / length;
        float ratio = proj / length;

        Vec3f pointLine = vecLine.normalize().multiply(proj); // point on the line
        Vec3f vecPointPos = pointPos.subtract(pointLine); // vector from pointLine to pointPos
        float distanceRadius = getDistanceRadius(vecPointPos, ratio);
        float distanceStart = -ratio * length;
        float distanceEnd = (ratio - 1) * length;

        if (distanceStart <= 0 && distanceEnd <= 0) { // within planes
            if (distanceRadius <= 0) { // entirely inside
                return Math.max(distanceRadius, Math.max(distanceStart, distanceEnd));
            } else { // outside but within planes
                return distanceRadius;
            }
        } else if (distanceStart > 0) { // outside start plane
            if (capStart) {
                return pos.distance(pos1) - this.rStart;
            } else {
                return new Vec3f(distanceStart, Math.max(distanceRadius, 0), 0).length();
            }
        } else if (distanceEnd > 0) { // outside end plane
            if (capEnd) {
                return pos.distance(pos2) - this.rEnd;
            } else {
                return new Vec3f(distanceEnd, Math.max(distanceRadius, 0), 0).length();
            }
        } else { // outside both planes (impossible)
            throw new IllegalStateException();
        }
    }
}
