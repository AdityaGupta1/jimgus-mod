package org.sdoaj.jimgus.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;

import java.util.List;
import java.util.Random;

public class MushroomFeature extends Feature<NoneFeatureConfiguration> {
    private static final float heightMin = 30;
    private static final float heightMax = 50;

    public MushroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        if (!world.isEmptyBlock(pos) || !world.getBlockState(pos.below()).is(Blocks.MYCELIUM)) {
            return false;
        }

        float height = MathHelper.nextFloat(random, heightMin, heightMax);
        List<Vec3f> stemSpline = SplineHelper.makeSpline(0, 0, 0,
                0, height, 0, 4);
        SplineHelper.offsetPoints(stemSpline, () -> MathHelper.nextFloatOne(random), 6, 0, 6, false, true);
        stemSpline = SplineHelper.bezier(stemSpline, 8);
        SDF stem = SplineHelper.SplineSDFBuilder.from(stemSpline)
                .radius(delta -> {
                    float x = delta - 0.5f;
                    return 4 * x * x + 1.5f;
                }).build().setBlock(BlockInit.TEST_BLOCK.get());

        float capRadius = MathHelper.nextFloat(random, 10, 14);
        capRadius *= MathHelper.mapRange(height, heightMin, heightMax, 0.8f, 1.2f);
        SDF cap = cap(stemSpline, capRadius, 2f);
//        SDF cap = new SDFCylinder(2).radius(capRadius).setBlock(Blocks.AMETHYST_BLOCK);
//        Vec3f stemSplineDirection = SplineHelper.getEndpoint(stemSpline).subtract(SplineHelper.getPointFromEnd(stemSpline, 1));
//        Vec3f capAxis = stemSplineDirection.cross(Vec3f.YP);
//        float capAngle = stemSplineDirection.angleTo(Vec3f.YP);
//        cap = new SDFTransform().rotate(capAxis, capAngle).setSource(cap);

        cap.fill(world, pos.offset(SplineHelper.getEndpoint(stemSpline).toBlockPos()));
        stem.fill(world, pos);

        return true;
    }

    public static SDF cap(List<Vec3f> spline, float radius, float height) {
        SDF cap = new SDFCylinder(height).radius(radius).setBlock(Blocks.AMETHYST_BLOCK);
        Vec3f stemSplineDirection = SplineHelper.getEndpoint(spline).subtract(SplineHelper.getPointFromEnd(spline, 1));
        Vec3f capAxis = stemSplineDirection.cross(Vec3f.YP);
        float capAngle = stemSplineDirection.angleTo(Vec3f.YP);
        cap = new SDFTransform().rotate(capAxis, capAngle).setSource(cap);
        return cap;
    }
}
