package org.sdoaj.jimgus.world.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;

public class CrystalFeature extends Feature<CountConfiguration> {
    public CrystalFeature() {
        super(CountConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<CountConfiguration> context) {
        for (int y = 0; y < 256; y++) {
            context.level().setBlock(context.origin().atY(y), BlockInit.TEST_BLOCK.get().defaultBlockState(), 19);
        }

        return true;
    }
}
