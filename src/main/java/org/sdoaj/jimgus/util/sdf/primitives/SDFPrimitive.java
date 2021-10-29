package org.sdoaj.jimgus.util.sdf.primitives;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;

import java.util.function.Function;

public abstract class SDFPrimitive extends SDF {
    protected Function<Vec3f, BlockState> placerFunction;

    public SDFPrimitive setBlock(Function<Vec3f, BlockState> placerFunction) {
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
    public BlockState getBlockState(Vec3f pos) {
        return placerFunction.apply(pos);
    }
}
