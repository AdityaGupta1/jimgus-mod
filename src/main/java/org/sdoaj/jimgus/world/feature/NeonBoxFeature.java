package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFBox;

import java.util.Random;

public class NeonBoxFeature extends Feature<NoneFeatureConfiguration> {
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final int z1;
    private final int z2;
    private final BlockState block;

    public NeonBoxFeature(int x1, int x2, int y1, int y2, int z1, int z2, BlockState block) {
        super(NoneFeatureConfiguration.CODEC);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
        this.block = block;
    }

    public NeonBoxFeature(int x1, int x2, int y1, int y2, int z1, int z2, Block block) {
        this(x1, x2, y1, y2, z1, z2, block.defaultBlockState());
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        if (!world.isEmptyBlock(pos) || !world.getBlockState(pos.below()).is(Blocks.BLACK_CONCRETE)) {
            return false;
        }

        int dx = MathHelper.nextInt(random, x1, x2) * 2;
        int dy = MathHelper.nextInt(random, y1, y2) * 2;
        int dz = MathHelper.nextInt(random, z1, z2) * 2;

        SDF box = new SDFBox(dx, dy, dz).setBlock(block);
        box = new SDFTransform().translate(0.5f, 0.5f, 0.5f).setSource(box);

        box.fill(world, pos);
        return true;
    }
}
