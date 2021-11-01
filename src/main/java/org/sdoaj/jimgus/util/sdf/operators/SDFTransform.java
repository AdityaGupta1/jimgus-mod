package org.sdoaj.jimgus.util.sdf.operators;

import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.math.Vec3f;

// kinda bad because if the translated SDF doesn't cover the origin, it won't be filled
public class SDFTransform extends SDFUnary {
    // TODO when porting to C++, implement as a stack of matrices instead of just one of each transformation
    // i.e. methods like addTranslation(vec) that will set this.mat4 = glm::translate(vec) * this.mat4

    private Vec3f translation, scale;
     private Vec3f axis;
     private float sin, cos, cosm; // cosm = 1 - cos

    public SDFTransform translate(float x, float y, float z) {
        return translate(new Vec3f(x, y, z));
    }

    public SDFTransform translate(Vec3f translation) {
        this.translation = translation;
        return this;
    }

    public SDFTransform rotate(float x, float y, float z, float radians) {
        return rotate(new Vec3f(x, y, z), radians);
    }

     public SDFTransform rotate(Vec3f axis, float radians) {
         this.axis = axis.normalize();
         this.sin = (float) Math.sin(radians);
         this.cos = (float) Math.cos(radians);
         this.cosm = 1 - this.cos;
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

    private Vec3f transformPos(Vec3f pos) {
        // https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula (matrix multiplication expanded)
        if (axis != null) {
            float wx = axis.x;
            float wy = axis.y;
            float wz = axis.z;

            float px = (cos + (wx * wx) * cosm) * pos.x + (-wz * sin + wx * wy * cosm) * pos.y + (wy * sin + wx * wz * cosm) * pos.z;
            float py = (wz * sin + wx * wy * cosm) * pos.x + (cos + (wy * wy) * cosm) * pos.y + (-wx * sin + wy * wz * cosm) * pos.z;
            float pz = (-wy * sin + wx * wz * cosm) * pos.x + (wx * sin + wy * wz * cosm) * pos.y + (cos + (wz * wz) * cosm) * pos.z;

            pos = new Vec3f(px, py, pz);
        }

        if (scale != null) {
            pos = pos.divide(scale);
        }

        if (translation != null) {
            pos = pos.subtract(translation);
        }

        return pos;
    }

    @Override
    public float distance(Vec3f pos) {
        return source.distance(transformPos(pos));
    }

    @Override
    public BlockState getBlockState(Vec3f pos) {
        return source.getBlockState(transformPos(pos));
    }
}
