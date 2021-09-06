package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;

import java.util.List;
import java.util.Random;

public class MushroomFeature extends Feature<NoneFeatureConfiguration> {
    private static final float heightMin = 30;
    private static final float heightMax = 50;

    public MushroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        if (!world.isEmptyBlock(pos) || !world.getBlockState(pos.below()).is(Blocks.MYCELIUM)) {
            return false;
        }

        float height = MathHelper.nextFloat(random, heightMin, heightMax);
        List<Vec3f> splineStem = SplineHelper.makeSpline(0, 0, 0,
                0, height, 0, 3);
        SplineHelper.offsetPoints(splineStem, () -> MathHelper.nextFloatOne(random), 6, 0, 6, false, true);
        splineStem = SplineHelper.bezier(splineStem, 8);
        SDF stem = SplineHelper.SplineSDFBuilder.from(splineStem)
                .radius(delta -> {
                    float x = delta - 0.5f;
                    return 4 * x * x + 1.5f;
                }).build().setBlock(BlockInit.TEST_BLOCK.get());

        float capRadius = MathHelper.nextFloat(random, 10, 14);
        capRadius *= MathHelper.mapRange(height, heightMin, heightMax, 0.8f, 1.2f);
        SDF cap = new SDFCylinder(2).radius(capRadius).setBlock(Blocks.AMETHYST_BLOCK);
        Vec3f splineDirection = SplineHelper.getEndpoint(splineStem).subtract(SplineHelper.getPointFromEnd(splineStem, 1));
        Vec3f axis = splineDirection.cross(Vec3f.YP);
        float angle = splineDirection.angleTo(Vec3f.YP);
        cap = new SDFTransform().rotate(axis, angle).setSource(cap);

        cap.fill(world, new Vec3f(pos).add(SplineHelper.getEndpoint(splineStem)).toBlockPos());
        stem.fill(world, pos);
        return true;
    }
}
