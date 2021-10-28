package org.sdoaj.jimgus.world.structure.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;
import org.sdoaj.jimgus.world.feature.MushroomFeature;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FallenMushroomLogStructureFeature extends AbstractStructureFeature {
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":fallen_mushroom_log_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        float radius = MathHelper.nextFloat(random, 3, 5);
        float length = MathHelper.nextFloat(random, 32, 48);

        SDF log = new SDFCylinder(length, true).radius(radius)
                .setBlock(Blocks.OAK_WOOD.defaultBlockState());
        log = new SDFTransform().rotate(1, 0, 0, MathHelper.toRadians(90))
                .setSource(log);
        log = new SDFTransform().rotate(0, 1, 0, MathHelper.toRadians(MathHelper.nextFloat(random, 360)))
                .setSource(log);

        BlockPos logCenter = pos.above(Math.round(radius) - 2);
        log.fill(world, logCenter);
    }
}
