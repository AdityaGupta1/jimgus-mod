package org.sdoaj.jimgus.world.structure.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFDisplacement;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.Random;
import java.util.stream.IntStream;

public class FallenMushroomLogStructureFeature extends AbstractStructureFeature {
    private final PerlinNoise smallNoise = new PerlinNoise(new WorldgenRandom(23192309L), IntStream.range(-2, 0));
    private final PerlinNoise capsNoise = new PerlinNoise(new WorldgenRandom(10923109310L), IntStream.range(-2, 0));

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":fallen_mushroom_log_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        float radius = MathHelper.nextFloat(random, 3, 5);
        float length = MathHelper.nextFloat(random, 32, 48);

        SDF log = new SDFCylinder(length, true).radius(radius).setBlock(vec -> {
            if (vec.multiply(new Vec3f(1, 0, 1)).length() < radius - 1.5f) {
                return Blocks.STRIPPED_OAK_WOOD.defaultBlockState();
            } else {
                return Blocks.OAK_WOOD.defaultBlockState();
            }
        });

        log = new SDFDisplacement().setDisplacement(vec -> (float) smallNoise.getValue(vec.x, vec.y, vec.z))
                .setSource(log);
        log = new SDFDisplacement().setDisplacement(vec -> {
            // too close to center of log along log axis
            if (Math.abs(vec.y) < (length / 2) - 1) {
                return 0f;
            }

            // outside radius of log
            if (new Vec3f(vec.x, 0, vec.z).length() > radius) {
                return 0f;
            }

            float noise = (float) capsNoise.getValue(vec.x, vec.y, vec.z);
            return noise * 9;
        }).setSource(log);

        log = new SDFTransform().rotate(1, 0, 0, MathHelper.toRadians(90))
                .setSource(log);
        log = new SDFTransform().rotate(0, 1, 0, MathHelper.toRadians(MathHelper.nextFloat(random, 360)))
                .setSource(log);

        BlockPos logCenter = pos.above(Math.round(radius) - 2);
        log.fill(world, logCenter);
    }
}
