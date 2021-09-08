package org.sdoaj.jimgus.util.sdf.operators;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.math.Vec3f;

public class SDFSubtraction extends SDFBinary {
    private boolean isBoolean = false;

    @Override
    public float distance(Vec3f pos) {
        float a = this.sourceA.distance(pos);
        float b = this.sourceB.distance(pos);
        this.selectValue(a, b);
        return Math.max(a, -b);
    }

    public SDFSubtraction setBoolean() {
        this.isBoolean = true;
        return this;
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return isBoolean ? this.sourceA.getBlockState(pos) : super.getBlockState(pos);
    }
}
