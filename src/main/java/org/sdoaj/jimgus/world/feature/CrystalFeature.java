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
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFNgonPrism;

import java.util.Random;
import java.util.function.UnaryOperator;

public class CrystalFeature extends Feature<NoneFeatureConfiguration> {
    private static final Block[] blocks = {Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS};

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

        Block crystalBlock = Util.pickRandom(random, blocks);
        SDF crystal = new SDFNgonPrism(MathHelper.nextInt(random, 20, 25)).sides(6).rotate(random.nextFloat() * 2 * MathHelper.PI)
                .radius(crystalRadius).radiusMultiplier(4.5f).setBlock(crystalBlock);
        crystal = new SDFTransform().rotate(MathHelper.nextFloatOne(random), 0, MathHelper.nextFloatOne(random),
                MathHelper.nextFloat(random, 0, MathHelper.toRadians(25))).setSource(crystal);

        int extraCrystals = MathHelper.nextInt(random, 3, 6);
        for (int i = 0; i < extraCrystals; i++) {
            SDF extraCrystal = new SDFNgonPrism(MathHelper.nextFloat(random, 10, 15)).sides(6)
                    .radius(crystalRadius).radiusMultiplier(2.5f).setBlock(crystalBlock);
            extraCrystal = new SDFTransform().rotate(MathHelper.nextFloatOne(random), 0, MathHelper.nextFloatOne(random),
                    MathHelper.nextFloat(random, MathHelper.toRadians(30), MathHelper.toRadians(65)))
                    .setSource(extraCrystal);
            crystal = new SDFUnion().setSourceA(crystal).setSourceB(extraCrystal);
        }

        crystal.fill(world, pos);

        return true;
    }
}
