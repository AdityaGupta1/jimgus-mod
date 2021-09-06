package org.sdoaj.jimgus.util.sdf.primitives;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.sdf.SDF;

import java.util.function.Function;

public abstract class SDFPrimitive extends SDF {
    protected Function<BlockPos, BlockState> placerFunction;

    public SDFPrimitive setBlock(Function<BlockPos, BlockState> placerFunction) {
        this.placerFunction = placerFunction;
        return this;
    }

    public SDFPrimitive setBlock(BlockState state) {
        return setBlock(pos -> state);
    }

    public SDFPrimitive setBlock(Block block) {
        return setBlock(block.defaultBlockState());
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return placerFunction.apply(pos);
    }
}
