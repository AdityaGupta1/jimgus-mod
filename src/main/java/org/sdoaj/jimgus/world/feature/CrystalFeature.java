package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;

public class CrystalFeature extends Feature<NoneFeatureConfiguration> {
    public CrystalFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        int y = pos.getY();

        for (int dy = 0; dy < 5; dy++) {
            context.level().setBlock(context.origin().atY(y + dy), BlockInit.TEST_BLOCK.get().defaultBlockState(), 19);
        }

        return true;
    }
}
