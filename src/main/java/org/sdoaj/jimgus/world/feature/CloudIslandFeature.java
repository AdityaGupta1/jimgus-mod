package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.sdoaj.jimgus.util.BlockHelper;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFDisplacement;
import org.sdoaj.jimgus.util.sdf.operators.SDFSubtraction;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFBox;
import org.sdoaj.jimgus.util.sdf.primitives.SDFSphere;

import java.util.Random;
import java.util.stream.IntStream;

public class CloudIslandFeature extends Feature<NoneFeatureConfiguration> {
    private static final SimpleWeightedRandomList<Block> blocks = SimpleWeightedRandomList.<Block>builder()
            .add(Blocks.WHITE_WOOL, 6)
            .add(Blocks.LIGHT_GRAY_WOOL, 1)
            .build();

    private static final PerlinNoise cloudsNoise = new PerlinNoise(new WorldgenRandom(23091293L), IntStream.range(-2, 0));

    public CloudIslandFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        BlockPos cloudPos = pos.above(MathHelper.nextInt(random, 16, 128));

        if (!world.isEmptyBlock(cloudPos)) {
            return false;
        }

        float radius = MathHelper.nextFloat(random, 8, 14);
        SDF island = new SDFSphere(radius).setBlock(blocks.getRandomValue(random).get());
        island = new SDFTransform().scale(MathHelper.nextFloat(random, 0.8f, 1.2f),
                MathHelper.nextFloat(random, 0.45f, 0.55f),
                MathHelper.nextFloat(random, 0.8f, 1.2f))
                .setSource(island);

        int extraClouds = MathHelper.nextInt(random, 4, 8);
        for (int i = 0; i < extraClouds; i++) {
            SDF extraCloud = new SDFSphere(MathHelper.nextFloat(random, 4, 6))
                    .setBlock(blocks.getRandomValue(random).get());
            extraCloud = new SDFTransform().translate(MathHelper.nextFloatAbs(random, radius * 0.4f, radius * 0.8f),
                    MathHelper.nextFloat(random, 0, -radius * 0.3f),
                    MathHelper.nextFloatAbs(random, radius * 0.4f, radius * 0.8f))
                    .scale(1f, 1f, 0.5f).setSource(extraCloud);
            island = new SDFUnion().setSourceA(island).setSourceB(extraCloud);
        }

        island = new SDFTransform().scale(1f, 0.7f, MathHelper.nextFloat(random, 0.5f, 0.8f)).setSource(island);

        island = new SDFDisplacement().setDisplacement(vec -> {
            vec = vec.divide(3f);
            return (float) cloudsNoise.getValue(vec.x, vec.y, vec.z);
        })
                .setDisplacementMultiplier(10f)
                .setSource(island);

        island.fill(world, cloudPos);

        return true;
    }
}
