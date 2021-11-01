package org.sdoaj.jimgus.util.sdf.operators;

import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.junit.jupiter.api.Test;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.primitives.SDFSphere;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SDFTransformTest {
    @Test
    public void testRotate() {
        PerlinNoise noise = new PerlinNoise(new WorldgenRandom(1203123L), IntStream.range(-2, 0));

        SDF sphere = new SDFSphere(5f);
        sphere = new SDFDisplacement().setDisplacement(vec -> (float) noise.getValue(vec.x, vec.y, vec.z))
                .setSource(sphere);

        SDF transform = new SDFTransform().rotate(Vec3f.YP, 0f).setSource(sphere);
        assertEquals(transform.distance(new Vec3f(1, 0, 0)),
                sphere.distance(Vec3f.fromAngleXZ(0, 1)));

        transform = new SDFTransform().rotate(Vec3f.YP, MathHelper.radians(15f)).setSource(sphere);
        assertEquals(transform.distance(new Vec3f(1, 0, 0)),
                sphere.distance(Vec3f.fromAngleXZ(MathHelper.radians(-15f), 1)));
    }
}