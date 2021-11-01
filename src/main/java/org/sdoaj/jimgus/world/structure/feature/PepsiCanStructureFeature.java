package org.sdoaj.jimgus.world.structure.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.mesh.Meshes;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.awt.*;
import java.util.Random;
import java.util.function.Function;

public class PepsiCanStructureFeature extends AbstractStructureFeature {
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":pepsi_can_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        Meshes.PEPSI_CAN.fill(world, pos, 2f, PepsimanStructureFeature.pepsiStateFunction);
    }
}
