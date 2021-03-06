package org.sdoaj.jimgus.util.sdf.operators;

import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;

public abstract class SDFBinary extends SDF {
    protected SDF sourceA;
    protected SDF sourceB;
    protected boolean firstValue;

    public SDFBinary setSourceA(SDF sourceA) {
        this.sourceA = sourceA;
        return this;
    }

    public SDFBinary setSourceB(SDF sourceB) {
        this.sourceB = sourceB;
        return this;
    }

    public SDFBinary setSources(SDF sourceA, SDF sourceB) {
        this.sourceA = sourceA;
        this.sourceB = sourceB;
        return this;
    }

    protected void selectValue(float a, float b) {
        firstValue = a < b;
    }

    @Override
    public BlockState getBlockState(Vec3f pos) {
        return (firstValue ? sourceA : sourceB).getBlockState(pos);
    }
}