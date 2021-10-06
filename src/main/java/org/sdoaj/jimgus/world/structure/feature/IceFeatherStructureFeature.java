package org.sdoaj.jimgus.world.structure.feature;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.phys.Vec3;
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

            float i = random.nextFloat();
            char direction;

            if (i < 0.35) {
                direction = 'x';
            } else {
                direction = switch (random.nextInt(3)) {
                    case 0 -> 'X';
                    case 1 -> 'z';
                    case 2 -> 'Z';
                    default -> throw new IllegalStateException();
                };
            }

            return "d[" + direction + "ad]a";
        }
    };

    private static final float lineLength = 12f;
    private static final float turnAngle = MathHelper.toRadians(10f);
    private static final float lineRadius = 2.0f;

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
        Vec3f vec = new Vec3f(pos);
        BlockHelper.fillTriangle(world, vec.offset(-10, 10, -10),
                vec.offset(-12, 15, -3),
                vec.offset(6, 5, 9),
                3.0f, Blocks.BLUE_ICE);

        // pos = pos.below(2);
        //
        // Vec3f currentPos = Vec3f.ZERO;
        // Vec3f direction = Vec3f.YP;
        // Deque<Vec3f> savedPositions = new ArrayDeque<>();
        //
        // String lSystemString = lSystem.run(5, random);
        // SDF sdf = null;
        //
        // for (char c : lSystemString.toCharArray()) {
        //     switch (c) {
        //         case '[':
        //             savedPositions.addFirst(currentPos);
        //             break;
        //         case ']':
        //             currentPos = savedPositions.removeFirst();
        //             break;
        //         case 'a':
        //             Vec3f newPos = currentPos.add(direction.multiply(lineLength));
        //             SDF line = new SDFLine(currentPos, newPos).radius(lineRadius);
        //             sdf = (sdf == null) ? line : new SDFUnion().setSources(sdf, line);
        //             currentPos = newPos;
        //             break;
        //         case 'x':
        //             direction = direction.rotate(quatXP);
        //             break;
        //         case 'X':
        //             direction = direction.rotate(quatXN);
        //             break;
        //         case 'z':
        //             direction = direction.rotate(quatZP);
        //             break;
        //         case 'Z':
        //             direction = direction.rotate(quatZN);
        //             break;
        //         default:
        //             break;
        //     }
        // }
        //
        // sdf = new SDFAbstractShape().setSource(sdf).setBlock(Blocks.BLUE_ICE);
        // sdf.fill(world, pos);
    }
}
