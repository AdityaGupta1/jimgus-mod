package org.sdoaj.jimgus.world.structure.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.world.feature.MushroomFeature;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BigMushroomStructureFeature extends AbstractStructureFeature {
    private static final float heightMin = 120;
    private static final float heightMax = 160;

    private static final int maxTendrilTries = 50;

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":big_mushroom_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        pos = pos.below(10); // to prevent bottom of stem from being above ground

        float height = MathHelper.nextFloat(random, heightMin, heightMax);
        List<Vec3f> stemSpline = SplineHelper.makeSpline(0, 0, 0,
                0, height, 0, 6);
        SplineHelper.offsetPoints(stemSpline, random::nextFloat, 10, 0, 10, false, true);
        stemSpline = SplineHelper.bezier(stemSpline, 14);
        SDF stem = SplineHelper.SplineSDFBuilder.from(stemSpline)
                .radius(delta -> {
                    float x = delta - 0.5f;
                    return 4 * x * x + 1.5f;
                }).radiusMultiplier(4.5f).build(1.5f).setBlock(BlockInit.TEST_BLOCK.get());

        float capRadius = MathHelper.nextFloat(random, 36, 48);
        capRadius *= MathHelper.mapRange(height, heightMin, heightMax, 0.8f, 1.2f);
        SDF cap = MushroomFeature.cap(stemSpline, capRadius, 5f);

        cap.fill(world, pos.offset(SplineHelper.getEndpoint(stemSpline).toBlockPos()));
        stem.fill(world, pos);

        int tendrils = MathHelper.nextInt(random, 4, 9);
        Map<Float, Float> previousTendrils = new HashMap<>();
        for (int i = 0; i < tendrils; i++) {
            float t;
            float tendrilAngle;
            int tries = 0;

            while (true) {
                t = MathHelper.nextFloat(random, 0.3f, 0.75f);
                tendrilAngle = MathHelper.nextFloat(random, 2 * MathHelper.PI);

                boolean isTooClose = false;
                for (Map.Entry<Float, Float> entry : previousTendrils.entrySet()) {
                    float tPrev = entry.getKey();
                    float anglePrev = entry.getValue();

                    if (Math.abs(t - tPrev) < 0.15f && MathHelper.angleBetween(tendrilAngle, anglePrev) < MathHelper.PI / 4) {
                        isTooClose = true;
                        break;
                    }
                }

                if (!isTooClose) {
                    break;
                }

                tries++;
                if (tries == maxTendrilTries) {
                    Jimgus.LOGGER.error("reached maximum tendril tries: " + maxTendrilTries);
                    break;
                }
            }

            previousTendrils.put(t, tendrilAngle);

            Vec3f tendrilBase = SplineHelper.getPointFromParameter(stemSpline, t);
            Vec3f tendrilDirection = Vec3f.fromAngleXZ(tendrilAngle);
            float tendrilLength = MathHelper.nextFloat(random, 30, 38);

            List<Vec3f> tendrilSpline = SplineHelper.makeSpline(Vec3f.ZERO,
                    tendrilDirection.multiply(tendrilLength), 3);
            tendrilSpline.add(SplineHelper.getEndpoint(tendrilSpline)
                    .offset(0, MathHelper.nextFloat(random, 8, 12), 0)
                    .add(tendrilDirection.multiply(4.0f)));
            SplineHelper.offsetPoints(tendrilSpline, random::nextFloat, 4, 4, 4, false, true);
            tendrilSpline = SplineHelper.bezier(tendrilSpline, 8);

            SDF tendril = SplineHelper.SplineSDFBuilder.from(tendrilSpline).radius(5, 2).build().setBlock(BlockInit.TEST_BLOCK.get());
            BlockPos tendrilBaseWorldPos = tendrilBase.toBlockPos().offset(pos);
            tendril.fill(world, tendrilBaseWorldPos);

            if (!MathHelper.chance(random, 0.6f)) {
                continue;
            }

            float tendrilCapRadius = MathHelper.nextFloat(random, 16, 20);
            SDF tendrilCap = MushroomFeature.cap(tendrilSpline, tendrilCapRadius, 3);
            tendrilCap.fill(world, tendrilBaseWorldPos.offset(SplineHelper.getEndpoint(tendrilSpline).toBlockPos()));
        }
    }
}
