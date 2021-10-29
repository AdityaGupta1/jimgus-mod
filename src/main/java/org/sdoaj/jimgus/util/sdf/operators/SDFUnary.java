package org.sdoaj.jimgus.util.sdf.operators;

import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;

public abstract class SDFUnary extends SDF {
    protected SDF source;

    public SDFUnary setSource(SDF source) {
        this.source = source;
        return this;
    }

    @Override
    public BlockState getBlockState(Vec3f pos) {
        return source.getBlockState(pos);
    }
}
