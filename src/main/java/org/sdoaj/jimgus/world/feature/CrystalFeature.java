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

import java.util.List;
import java.util.Random;

public class CrystalFeature extends Feature<NoneFeatureConfiguration> {
    private static final Block[] blocks = {Blocks.EMERALD_BLOCK, Blocks.DIAMOND_BLOCK};

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

        SDF box = new SDFBox(4, 12, 8).setBlock(Util.pickRandom(random, blocks));

        box.fill(world, pos);
        return true;
    }
}
