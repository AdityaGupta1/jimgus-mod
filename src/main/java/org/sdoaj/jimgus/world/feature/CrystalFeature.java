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
import org.sdoaj.jimgus.util.sdf.primitives.SDFBox;
import org.sdoaj.jimgus.util.sdf.primitives.SDFNgonPrism;

import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class CrystalFeature extends Feature<NoneFeatureConfiguration> {
    private static final Block[] blocks = {Blocks.EMERALD_BLOCK, Blocks.DIAMOND_BLOCK};

    private static final float coneStart = 0.8f;
    private static final float coneN = 1 / (1 - coneStart);
    private static final UnaryOperator<Float> crystalRadius = delta -> {
        if (delta <= coneStart) {
            return 1f;
        } else {
            return -coneN * delta + coneN;
        }
    };

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

        SDF sdf = new SDFNgonPrism(50, false).sides(6).rotate((float) (Math.PI / 6))
                .radius(crystalRadius).radiusMultiplier(8f).setBlock(Util.pickRandom(random, blocks));

        sdf.fill(world, pos);
        return true;
    }
}
