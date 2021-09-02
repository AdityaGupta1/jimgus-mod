package org.sdoaj.jimgus.world.feature;

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
        for (int y = 0; y < 256; y++) {
            context.level().setBlock(context.origin().atY(y), BlockInit.TEST_BLOCK.get().defaultBlockState(), 19);
        }

        return true;
    }
}
