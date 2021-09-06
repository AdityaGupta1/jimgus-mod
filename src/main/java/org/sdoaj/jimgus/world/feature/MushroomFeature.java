package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.Util;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;
import org.sdoaj.jimgus.util.sdf.primitives.SDFLine;
import org.sdoaj.jimgus.util.sdf.primitives.SDFSphere;

import java.util.List;
import java.util.Random;

public class MushroomFeature extends Feature<NoneFeatureConfiguration> {
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

        List<Vec3f> splineStem = SplineHelper.makeSpline(0, 0, 0,
                0, MathHelper.nextIntInRange(random, 30, 50), 0, 3);
        SplineHelper.offsetPoints(splineStem, () -> MathHelper.nextFloatInRangeOne(random), 6, 0, 6, false, true);
        splineStem = SplineHelper.bezier(splineStem, 8);
        SDF stem = SplineHelper.SplineSDFBuilder.from(splineStem)
                .radius(delta -> {
                    float x = delta - 0.5f;
                    return 4 * x * x + 1.5f;
                }).build().setBlock(BlockInit.TEST_BLOCK.get());

        SDF cap = new SDFCylinder(10, 2).setBlock(Blocks.AMETHYST_BLOCK);

        cap.fill(world, new Vec3f(pos).add(SplineHelper.getEndpoint(splineStem)).toBlockPos());
        stem.fill(world, pos);
        return true;
    }
}
