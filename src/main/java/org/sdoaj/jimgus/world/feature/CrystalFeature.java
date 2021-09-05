package org.sdoaj.jimgus.world.feature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTranslate;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFSphere;

public class CrystalFeature extends Feature<NoneFeatureConfiguration> {
    public CrystalFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
//        BlockPos pos = context.origin();
//        int y = pos.getY();
//
//        for (int dy = 0; dy < 5; dy++) {
//            context.level().setBlock(context.origin().atY(y + dy), BlockInit.TEST_BLOCK.get().defaultBlockState(), 19);
//        }

        SDF sphere1 = new SDFSphere(4).setBlock(BlockInit.TEST_BLOCK.get());
        SDF sphere2 = new SDFSphere(4).setBlock(Blocks.GLOWSTONE);
        SDF sdf = new SDFUnion().setSourceA(sphere1).setSourceB(new SDFTranslate().setTranslate(0, 4, 0).setSource(sphere2));
        sdf.fill(context.level(), context.origin());

//        SDF sdf = new SDFBox(3, 10, 3).setBlock(BlockInit.TEST_BLOCK.get());
//        sdf.fill(context.level(), context.origin());

        return true;
    }
}
