package org.sdoaj.jimgus.util.sdf.operators;

import org.sdoaj.jimgus.util.math.Vec3f;

import java.util.function.Function;

// doesn't really work with some booleans and any SDFs that aren't linear
public class SDFDisplacement extends SDFUnary {
    // negative values expand the shape, positive values shrink the shape
    private Function<Vec3f, Float> displacement;

    public SDFDisplacement setDisplacement(float displacement) {
        return setDisplacement(pos -> displacement);
    }

    public SDFDisplacement setDisplacement(Function<Vec3f, Float> displacement) {
        this.displacement = displacement;
        return this;
    }

    @Override
    public float distance(Vec3f pos) {
        return this.source.distance(pos) + displacement.apply(pos);
    }
}
