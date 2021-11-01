package org.sdoaj.jimgus.world.structure.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.mesh.Meshes;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.Random;

public class PepsimanStructureFeature extends AbstractStructureFeature {
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":pepsiman_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        Meshes.PEPSIMAN.fill(world, pos, 2f, color -> {
            boolean red = color.getRed() > 10;
            boolean blue = color.getBlue() > 10;

            if (red && blue) {
                return Blocks.IRON_BLOCK.defaultBlockState();
            } else if (red) {
                return Blocks.RED_WOOL.defaultBlockState();
            } else if (blue) {
                return Blocks.BLUE_WOOL.defaultBlockState();
            } else {
                throw new IllegalStateException("bad color: " + color);
            }
        });
    }
}
