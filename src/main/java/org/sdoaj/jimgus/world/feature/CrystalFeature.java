package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.util.Util;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;

import java.util.List;
import java.util.Random;

public class CrystalFeature extends Feature<NoneFeatureConfiguration> {
    private static final Block[] blocks = {Blocks.OBSIDIAN, Blocks.SMOOTH_QUARTZ, Blocks.POLISHED_BASALT};

    public CrystalFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        if (!world.isEmptyBlock(pos) || !world.getBlockState(pos.below()).is(Blocks.STONE)) {
            return false;
        }

        List<Vec3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, 80, 0, 4);
//        SplineHelper.offsetPoints(spline, () -> MathHelper.lerp(random.nextFloat(), -1, 1), 3, 0, 3);
        SplineHelper.offsetPoints(spline, () -> (float) random.nextGaussian(), 12, 0, 12);
        SDF sdf = SplineHelper.SplineSDFBuilder.from(SplineHelper.bezier(spline, 8))
                .radius(10, 2).build().setBlock(Util.pickRandom(random, blocks));
        sdf.fill(world, pos);

        return true;
    }
}
