package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.util.BlockHelper;
import org.sdoaj.jimgus.util.math.MathHelper;

import java.util.Random;

public class IceWireframeFeature extends Feature<NoneFeatureConfiguration> {
    public IceWireframeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private static final int[] indices = {0, 1, 1, 2, 2, 3, 3, 0, 0, 4, 1, 5, 2, 6, 3, 7, 4, 5, 5, 6, 6, 7, 7, 4};

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        if (world.getBlockState(pos.below()).getBlock() != Blocks.SNOW) {
            return false;
        }

        final int radius = MathHelper.nextInt(random, 1, 4);
        final BlockPos[] vertices = {
                pos.offset(radius, radius, radius),
                pos.offset(radius, -radius, radius),
                pos.offset(-radius, -radius, radius),
                pos.offset(-radius, radius, radius),
                pos.offset(radius, radius, -radius),
                pos.offset(radius, -radius, -radius),
                pos.offset(-radius, -radius, -radius),
                pos.offset(-radius, radius, -radius)
        };

        for (int i = 0; i < indices.length; i += 2) {
            BlockHelper.fillBox(world, vertices[indices[i]], vertices[indices[i + 1]],
                    Blocks.PACKED_ICE.defaultBlockState());
        }

        return true;
    }
}
