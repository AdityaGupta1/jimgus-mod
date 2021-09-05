package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.primitives.SDFLine;

public class CrystalFeature extends Feature<NoneFeatureConfiguration> {
    public CrystalFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();

        if (!world.isEmptyBlock(pos) || !world.getBlockState(pos.below()).is(Blocks.STONE)) {
            return false;
        }

        SDF line = new SDFLine(0, 0, 0, 0, 50, 0, 5, 1).disableRoundCaps().setBlock(BlockInit.TEST_BLOCK.get());
        line.fill(context.level(), context.origin());

        return true;
    }
}
