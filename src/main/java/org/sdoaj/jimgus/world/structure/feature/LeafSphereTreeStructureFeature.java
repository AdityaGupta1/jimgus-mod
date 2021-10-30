package org.sdoaj.jimgus.world.structure.feature;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.SplineHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFAdd;
import org.sdoaj.jimgus.util.sdf.operators.SDFDisplacement;
import org.sdoaj.jimgus.util.sdf.operators.SDFTransform;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFCylinder;
import org.sdoaj.jimgus.util.sdf.primitives.SDFSphere;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public class LeafSphereTreeStructureFeature extends AbstractStructureFeature {
    private final PerlinNoise smallNoise = new PerlinNoise(new WorldgenRandom(579812031L), IntStream.range(-2, 0));

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":leaf_sphere_tree_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        // main log
        // ========================================================

        float radiusMultiplier = MathHelper.nextFloat(random, 3, 5);
        float height = MathHelper.nextFloat(random, 120, 160);

        UnaryOperator<Float> trunkRadius = x -> (float) ((0.7 / Math.pow(x + 0.1, 0.5) + (0.25 * x)));
        SDFCylinder logCylinder = new SDFCylinder(height, false);

        SDF log = logCylinder.radius(trunkRadius)
                .radiusMultiplier(radiusMultiplier)
                .setBlock(Blocks.OAK_WOOD.defaultBlockState());

        log = new SDFDisplacement().setDisplacement(vec -> (float) smallNoise.getValue(vec.x, vec.y, vec.z))
                .setSource(log);

        // mushrooms
        // ========================================================

        SDF mushrooms = null;
        int numMushroomGroups = MathHelper.nextInt(random, 2, 6);
        for (int i = 0; i < numMushroomGroups; i++) {
            float heightRatio = MathHelper.nextFloat(random, 0.1f, 0.75f);
            float angle = MathHelper.nextFloat(random, MathHelper.PI2);

            boolean directionBoolean = random.nextBoolean();
            int direction = directionBoolean ? 1 : -1;
            if (directionBoolean) {
                heightRatio = 1 - heightRatio;
            }

            Quaternion rotate = new Quaternion(Vector3f.YP, angle, false);
            float mushroomHeight = heightRatio * height;

            Block mushroomBlock = random.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK;

            int numMushrooms = MathHelper.nextInt(random, 4, 7);
            for (int j = 0; j < numMushrooms; j++) {
                Vec3f mushroomPos = new Vec3f(0, mushroomHeight,
                        logCylinder.getRadius(mushroomHeight / height) - 0.5f);
                mushroomPos = mushroomPos.rotate(rotate);

                float mushroomThickness = MathHelper.nextFloat(random, 0.7f, 1.0f);
                float mushroomRadius = MathHelper.nextFloat(random, 2.2f, 2.8f);
                SDF mushroom = new SDFCylinder(mushroomThickness, true).radius(mushroomRadius)
                        .setBlock(mushroomBlock);
                Vec3f randomMushroomPos = mushroomPos.offset(MathHelper.nextFloatAbs(random, 0.5f),
                        MathHelper.nextFloatAbs(random, 0.5f),
                        MathHelper.nextFloatAbs(random, 0.5f));
                mushroom = new SDFTransform().translate(randomMushroomPos).setSource(mushroom);

                mushrooms = (mushrooms == null) ? mushroom : new SDFUnion().setSources(mushrooms, mushroom);
                mushroomHeight += direction * MathHelper.nextFloat(random, 1.8f, 2.1f);
            }
        }

        if (mushrooms != null) {
            log = new SDFAdd().setSources(log, mushrooms);
        }

        log.fill(world, pos);

        // branches and leaf spheres
        // ========================================================

        int numBigBranches = MathHelper.nextInt(random, 7, 12);
        List<Float> bigBranchPositions = new ArrayList<>();
        float bigBranchPosition = 0;
        for (int i = 0; i < numBigBranches; i++) {
            bigBranchPositions.add(bigBranchPosition += MathHelper.nextFloat(random, 1.0f, 2.0f));
        }

        float lastNonNormalizedBigBranchPosition = bigBranchPositions.get(numBigBranches - 1);
        float bigBranchesHeightStart = MathHelper.nextFloat(random, 0.35f, 0.4f) * height;
        float bigBranchesDeltaY = MathHelper.nextFloat(random, 0.55f, 0.6f) * height;
        for (int i = 0; i < numBigBranches; i++) {
            float bigBranchLocalHeightRatio = bigBranchPositions.get(i) / lastNonNormalizedBigBranchPosition;
            float bigBranchHeight = (bigBranchLocalHeightRatio * bigBranchesDeltaY)
                    + bigBranchesHeightStart;

            int numActualBigBranches = MathHelper.nextInt(random, 2, 4);
            float angle = MathHelper.nextFloat(random, MathHelper.PI2);
            float angleOffset = MathHelper.PI2 / numActualBigBranches;
            float bigBranchSizeMultiplier = MathHelper.lerp(bigBranchLocalHeightRatio, 1.0f, 0.6f);
            for (int j = 0; j < numActualBigBranches; j++) {
                float angleRandom = angle + MathHelper.nextFloatAbs(random, MathHelper.radians(10));
                Vec3f angleVec = new Vec3f((float) Math.cos(angleRandom), 0, (float) Math.sin(angleRandom));

                Vec3f bigBranchWorldCenterPos = new Vec3f(pos.getX(),
                        pos.getY() + bigBranchHeight + MathHelper.nextFloatAbs(random, 2.5f),
                        pos.getZ());

                Vec3f bigBranchWorldStartPos = bigBranchWorldCenterPos
                        .add(angleVec.multiply(logCylinder.getRadius(bigBranchHeight / height)));
                Vec3f bigBranchLocalEndPos = angleVec.multiply(MathHelper.nextFloat(random, 40.0f, 48.0f) * bigBranchSizeMultiplier);

                List<Vec3f> bigBranchSpline = SplineHelper.makeSpline(Vec3f.ZERO, bigBranchLocalEndPos, 5);
                SplineHelper.offsetPoints(bigBranchSpline, random::nextFloat, 2f, 4f, 2f, false, true);

                SDF bigBranch = SplineHelper.SplineSDFBuilder.from(bigBranchSpline)
                        .radius(x -> (2 - x))
                        .radiusMultiplier(1.2f * bigBranchSizeMultiplier)
                        .build()
                        .setBlock(Blocks.OAK_WOOD.defaultBlockState());

                bigBranch.fill(world, bigBranchWorldStartPos.toBlockPos());
                angle += angleOffset;

                int numLeafSpheres = (int) (MathHelper.nextFloat(random, 5, 11) * bigBranchSizeMultiplier);
                for (int k = 0; k < numLeafSpheres; k++) {
                    float leafSphereRadius = MathHelper.nextFloat(random, 3.5f, 5.0f);
                    Vec3f leafSphereLocalPos = SplineHelper.getPointFromParameter(bigBranchSpline,
                            MathHelper.nextFloat(random, 0.2f, 1f));
                    leafSphereLocalPos = leafSphereLocalPos.offset(MathHelper.nextFloat(random, 4f),
                            MathHelper.nextFloat(random, 4f), MathHelper.nextFloat(random, 4f));

                    SDF leafSphere = new SDFSphere(leafSphereRadius).setBlock(Blocks.OAK_LEAVES);
                    leafSphere.fill(world, bigBranchWorldStartPos.add(leafSphereLocalPos).toBlockPos());
                }
            }
        }

        // TODO roots (probably using splines)
    }
}
