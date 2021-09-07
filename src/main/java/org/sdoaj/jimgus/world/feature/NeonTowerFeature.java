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
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFNgonPrism;

import java.util.List;
import java.util.Random;

public class NeonTowerFeature extends Feature<NoneFeatureConfiguration> {
    private static final int midsectionLength = 32;

    public NeonTowerFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        if (!world.isEmptyBlock(pos) || !world.getBlockState(pos.below()).is(Blocks.BLACK_CONCRETE)) {
            return false;
        }

        int height = MathHelper.nextInt(random, 4, 7) * 16;
        float diff = (((float) midsectionLength) / height) / 2;
        SDF tower = new SDFNgonPrism(height).sides(4).radius(delta -> Math.abs(delta - 0.5) < diff ? 0.8f : 1f)
                .radiusMultiplier(MathHelper.nextInt(random, 4, 7) * 2).setBlock(Blocks.PURPLE_CONCRETE);
        tower = new SDFTransform().rotate(0, 1, 0, (float) (Math.PI / 4))
                .translate(0.5f, 0.5f, 0.5f).setSource(tower);

        tower.fill(world, pos);
        return true;
    }
}
