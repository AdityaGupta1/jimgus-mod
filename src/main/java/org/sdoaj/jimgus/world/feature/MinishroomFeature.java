package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.Util;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFBox;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;

import java.util.List;
import java.util.Random;

public class MinishroomFeature extends Feature<NoneFeatureConfiguration> {
    private static final Float[] capRadii = {1.5f, 2.5f};

    public MinishroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        BlockState below = world.getBlockState(pos.below());
        if (!world.isEmptyBlock(pos) || (!below.is(Blocks.MYCELIUM) && !below.is(Blocks.STONE))) {
            return false;
        }

        float height = MathHelper.nextFloat(random, 2, 4);
        SDF stem = new SDFCylinder(height).radius(0.5f).setBlock(BlockInit.TEST_BLOCK.get());

        SDF cap = new SDFCylinder(0.5f).radius(Util.pickRandom(random, capRadii)).setBlock(Blocks.AMETHYST_BLOCK)
                .addCanReplace(state -> state.is(Blocks.STONE));

        cap.fill(world, pos.above((int) height + 1));
        stem.fill(world, pos);
        return true;
    }
}
