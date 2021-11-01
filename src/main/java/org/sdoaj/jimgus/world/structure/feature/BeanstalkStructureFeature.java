package org.sdoaj.jimgus.world.structure.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFSubtraction;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFAbstractShape;
import org.sdoaj.jimgus.util.sdf.primitives.SDFBox;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

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

        SplineHelper.SplineSDFBuilder splineBuilder = SplineHelper.SplineSDFBuilder.from(spline)
                .radius(MathHelper.nextFloat(random, 3.5f, 4.5f), 1.5f);
        UnaryOperator<Float> beanstalkRadius = splineBuilder.getRadiusFunction();

        SDF beanstalk = splineBuilder.build().setBlock(Blocks.LIME_WOOL);

        beanstalk.fill(world, pos);

        float leafHeightRatio = MathHelper.nextFloat(random, 0.10f, 0.13f);
        while (leafHeightRatio < 0.98f) {
            float leafRadius = MathHelper.lerp(leafHeightRatio + MathHelper.nextFloatAbs(random, 0.05f), 8f, 5f);
            float leafAngle = MathHelper.nextFloat(random, MathHelper.PI2);

            SDF leaf = new SDFCylinder(1.3f).radius(leafRadius);
            leaf = new SDFTransform().translate(2f, 0, 0).setSource(leaf);

            SDF box = new SDFBox(leafRadius + 1f);
            box = new SDFTransform().translate(leafRadius + 1.2f, 0, 0).setSource(box);
            leaf = new SDFSubtraction().setBoolean().setSources(leaf, box);

            leaf = new SDFUnion().setSources(leaf,
                    new SDFTransform().rotate(0, 1, 0, MathHelper.PI).setSource(leaf));
            leaf = new SDFAbstractShape().setSource(leaf)
                    .setBlock(vec -> (Math.abs(vec.x) > 0.9f ? Blocks.GREEN_WOOL : Blocks.GREEN_CONCRETE).defaultBlockState());

            leaf = new SDFTransform().translate(0, 0, leafRadius - 1.5f).setSource(leaf);
            leaf = new SDFTransform().rotate(0, 1, 0, leafAngle).setSource(leaf);
            Vec3f leafLocalStartPos = Vec3f.fromAngleXZ(leafAngle + MathHelper.PI / 2f, beanstalkRadius.apply(leafHeightRatio))
                    .add(SplineHelper.getPointFromParameter(spline, leafHeightRatio));

            leaf = new SDFTransform().rotate(random.nextFloat(), random.nextFloat(), random.nextFloat(),
                    MathHelper.nextFloatAbs(random, MathHelper.radians(20))).setSource(leaf);

            leaf.fill(world, pos.offset(leafLocalStartPos.toBlockPos()));
            leafHeightRatio += MathHelper.nextFloat(random, 0.03f, 0.05f);
        }
    }
}
