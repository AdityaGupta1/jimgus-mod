package org.sdoaj.jimgus.util.sdf.operators;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import org.sdoaj.jimgus.util.math.Vec3f;

// kinda bad because if the translated SDF doesn't cover the origin, it won't be filled
public class SDFTransform extends SDFUnary {
    private Vec3f translation, axis, scale;
    private float sin, cos, cosm; // cosm = 1 - cos

//    private Vec3f translation, scale;
//    private Quaternion rotation;

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
        this.axis = axis.normalize();
        this.sin = (float) Math.sin(angle);
        this.cos = (float) Math.cos(angle);
        this.cosm = 1 - this.cos;
        return this;
    }

//    public SDFTransform rotate(Vec3f axis, float angle) {
//        this.rotation = new Quaternion(axis.toVector3f(), angle, false);
//        return this;
//    }

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

        // https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula (matrix multiplication expanded)
        if (axis != null) {
            float wx = axis.x;
            float wy = axis.y;
            float wz = axis.z;

            float px = (cos + (wx * wx) * cosm) * x + (-wz * sin + wx * wy * cosm) * y + (wy * sin + wx * wz * cosm) * z;
            float py = (wz * sin + wx * wy * cosm) * x + (cos + (wy * wy) * cosm) * y + (-wx * sin + wy * wz * cosm) * z;
            float pz = (-wy * sin + wx * wz * cosm) * x + (wx * sin + wy * wz * cosm) * y + (cos + (wz * wz) * cosm) * z;

            pos = new Vec3f(px, py, pz);
        }

        if (scale != null) {
            pos = pos.divide(scale);
        }

        if (translation != null) {
            pos = pos.subtract(translation);
        }

        return source.distance(pos);
    }
}
