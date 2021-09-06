package org.sdoaj.jimgus.util.sdf.operators;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import org.sdoaj.jimgus.util.math.Vec3f;

// kinda bad because if the translated SDF doesn't cover the origin, it won't be filled
public class SDFTransform extends SDFUnary {
//    private Vec3f translate, axis, scale;
//    private float sin, cos;

    private Vec3f translation, scale;
    private Quaternion rotation;

    public SDFTransform translate(float x, float y, float z) {
        return translate(new Vec3f(x, y, z));
    }

    public SDFTransform translate(Vec3f translation) {
        this.translation = translation;
        return this;
    }

    // angle is in radians
    public SDFTransform rotate(float x, float y, float z, float angle) {
        return rotate(new Vec3f(x, y, z), angle);
    }

    public SDFTransform rotate(Vec3f axis, float angle) {
//        angle = -angle;
//        this.axis = axis.normalize();
//        this.sin = (float) Math.sin(angle);
//        this.cos = (float) Math.cos(angle);
//        return this;

        this.rotation = new Quaternion(axis.toVector3f(), angle, false);
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

//        // https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
//        if (axis != null) {
//            pos = pos.multiply(cos) // vcos(theta)
//                    .add(axis.cross(pos).multiply(sin)) // (k x v)sin(theta)
//                    .add(axis.multiply(axis.dot(pos) * (1 - cos))); // k(k * v)(1 - cos(theta))
//        }

        if (rotation != null) {
            Vector3f v = pos.toVector3f();
            v.transform(rotation);
            pos = new Vec3f(v);
        }

        if (translation != null) {
            pos = pos.subtract(translation);
        }

        return source.distance(pos);
    }
}
