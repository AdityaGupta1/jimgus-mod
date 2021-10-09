package org.sdoaj.jimgus.world.structure.feature;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.util.BlockHelper;
import org.sdoaj.jimgus.util.LSystem;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFAbstractShape;
import org.sdoaj.jimgus.util.sdf.primitives.SDFLine;
import org.sdoaj.jimgus.world.structure.AbstractStructureFeature;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.*;

public class IceFeatherStructureFeature extends AbstractStructureFeature {
    private static final LSystem lSystem = new LSystem() {
        @Override
        protected String getSeed() {
            return "d";
        }

        @Override
        protected String produce(char c, Random random) {
            if (c != 'd') {
                return Character.toString(c);
            }

            char direction = switch (random.nextInt(4)) {
                case 0 -> 'x';
                case 1 -> 'X';
                case 2 -> 'z';
                case 3 -> 'Z';
                default -> throw new IllegalStateException();
            };

            return "d[t" + direction + "ad]a";
        }
    };

    private static final float lineLengthMin = 15f;
    private static final float lineLengthMax = 22f;
    private static final float turnAngle = MathHelper.toRadians(22f);
    private static final float lineRadius = 1.3f;
    private static final float triangleThickness = 1.8f;

    private static final Quaternion quatXP = new Quaternion(Vector3f.ZP, turnAngle, false);
    private static final Quaternion quatXN = new Quaternion(Vector3f.ZP, -turnAngle, false);
    private static final Quaternion quatZP = new Quaternion(Vector3f.XP, turnAngle, false);
    private static final Quaternion quatZN = new Quaternion(Vector3f.XP, -turnAngle, false);

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return Jimgus.MODID + ":ice_feather_structure";
    }

    @Override
    protected void fillStructureWorld(StructureWorld world, BlockPos pos, Random random) {
        pos = pos.below(2);

        Vec3f currentPos = Vec3f.ZERO;
        Vec3f direction = Vec3f.YP;
        Deque<Vec3f> savedPositions = new ArrayDeque<>();
        Map<Vec3f, Vec3f> triangleCandidates = new HashMap<>();

        String lSystemString = lSystem.run(3, random);
        SDF sdf = null;

        for (char c : lSystemString.toCharArray()) {
            switch (c) {
                case '[' -> savedPositions.addFirst(currentPos);
                case ']' -> currentPos = savedPositions.removeFirst();
                case 't' -> triangleCandidates.put(currentPos, null);
                case 'a' -> {
                    float lineLength = MathHelper.nextFloat(random, lineLengthMin, lineLengthMax);
                    Vec3f newPos = currentPos.add(direction.multiply(lineLength));
                    SDF line = new SDFLine(currentPos, newPos).radius(lineRadius);
                    sdf = (sdf == null) ? line : new SDFUnion().setSources(sdf, line);

                    if (triangleCandidates.containsKey(currentPos)) {
                        Vec3f trianglePoint = triangleCandidates.get(currentPos);
                        if (trianglePoint == null) {
                            triangleCandidates.put(currentPos, newPos);
                        } else {
                            BlockHelper.fillTriangle(world, currentPos.offset(pos), trianglePoint.offset(pos),
                                    newPos.offset(pos), triangleThickness, Blocks.ICE);
                            triangleCandidates.remove(currentPos);
                        }
                    }

                    currentPos = newPos;
                }
                case 'x' -> direction = direction.rotate(quatXP);
                case 'X' -> direction = direction.rotate(quatXN);
                case 'z' -> direction = direction.rotate(quatZP);
                case 'Z' -> direction = direction.rotate(quatZN);
            }
        }

        sdf = new SDFAbstractShape().setSource(sdf).setBlock(Blocks.BLUE_ICE);
        sdf.fill(world, pos);
    }
}
