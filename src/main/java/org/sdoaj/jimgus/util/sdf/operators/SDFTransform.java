package org.sdoaj.jimgus.util.sdf.operators;

import org.sdoaj.jimgus.util.math.Vec3f;

// kinda bad because if the translated SDF doesn't cover the origin, it won't be filled
public class SDFTransform extends SDFUnary {
    private Vec3f translate, axis, scale;
    private float sin, cos;

    public SDFTransform translate(float x, float y, float z) {
        return translate(new Vec3f(x, y, z));
    }

    public SDFTransform translate(Vec3f translate) {
        this.translate = translate;
        return this;
    }

    // theta is in degrees
    public SDFTransform rotate(float x, float y, float z, float theta) {
        return rotate(new Vec3f(x, y, z), theta);
    }

    public SDFTransform rotate(Vec3f axis, float theta) {
        theta = -theta;
        this.axis = axis.normalize();
        this.sin = (float) Math.sin(theta);
        this.cos = (float) Math.cos(theta);
        return this;
    }

    public SDFTransform scale(float scale) {
        return scale(scale, scale, scale);
    }

    public SDFTransform scale(float x, float y, float z) {
        return scale(new Vec3f(x, y, z));
    }

    public SDFTransform scale(Vec3f scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public float distance(float x, float y, float z) {
        Vec3f pos = new Vec3f(x, y, z);

        if (scale != null) {
            pos = pos.divide(scale);
        }

        // https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
        if (axis != null) {
            pos = pos.multiply(cos) // vcos(theta)
                    .add(axis.cross(pos).multiply(sin)) // (k x v)sin(theta)
                    .add(axis.multiply(axis.dot(pos) * (1 - cos))); // k(k * v)(1 - cos(theta))
        }

        if (translate != null) {
            pos = pos.subtract(translate);
        }

        return source.distance(pos);
    }
}
