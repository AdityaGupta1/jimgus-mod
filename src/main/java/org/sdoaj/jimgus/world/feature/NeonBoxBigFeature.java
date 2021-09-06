package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFBox;

import java.util.Random;

public class NeonBoxBigFeature extends Feature<NoneFeatureConfiguration> {
    public NeonBoxBigFeature() {
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

        int dx = MathHelper.nextInt(random, 2, 4) * 2;
        int dy = MathHelper.nextInt(random, 3, 6) * 2;
        int dz = MathHelper.nextInt(random, 2, 4) * 2;

        SDF box = new SDFBox(dx, dy, dz).setBlock(Blocks.BLACK_CONCRETE);
        box = new SDFTransform().translate(0.5f, 0.5f, 0.5f).setSource(box);

        box.fill(world, pos);
        return true;
    }
}
