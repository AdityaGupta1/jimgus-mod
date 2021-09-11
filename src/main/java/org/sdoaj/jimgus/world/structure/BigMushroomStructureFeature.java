package org.sdoaj.jimgus.world.structure;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;

import java.util.List;
import java.util.Random;

public class BigMushroomStructureFeature extends SDFStructureFeature {
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":big_mushroom_structure";
    }

    @Override
    protected List<SDF> getSDFs(Random random) {
        SDF cylinderBase = new SDFCylinder(12).radius(32).setBlock(Blocks.SMOOTH_QUARTZ);
        SDF cylinderTop = new SDFCylinder(24).radius(24).setBlock(Blocks.IRON_BLOCK);
        cylinderTop = new SDFTransform().translate(0, 12, 0).setSource(cylinderTop);
        return List.of(cylinderBase, cylinderTop);
    }
}
