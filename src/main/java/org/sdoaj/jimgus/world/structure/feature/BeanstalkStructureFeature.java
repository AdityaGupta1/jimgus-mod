package org.sdoaj.jimgus.world.structure.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.List;
import java.util.Random;

public class BeanstalkStructureFeature extends AbstractStructureFeature {
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":beanstalk_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        float height = MathHelper.nextFloat(random, 132, 164);

        Vec3f splineLocalEndPos = new Vec3f(0, height, 0);

        List<Vec3f> spline = SplineHelper.makeSpline(Vec3f.ZERO, splineLocalEndPos, 6);
        SplineHelper.offsetPoints(spline, () -> (float) random.nextGaussian(),
                12, 3, 12, false, true);

        spline = SplineHelper.bezier(spline, 24);

        SDF beanstalk = SplineHelper.SplineSDFBuilder.from(spline)
                .radius(MathHelper.nextFloat(random, 3.5f, 4.5f), 1.5f)
                .build()
                .setBlock(Blocks.LIME_WOOL);

        beanstalk.fill(world, pos);
    }
}
