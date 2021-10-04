package org.sdoaj.jimgus.world.feature;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.sdoaj.jimgus.util.LSystem;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.util.sdf.SDF;
import org.sdoaj.jimgus.util.sdf.operators.SDFUnion;
import org.sdoaj.jimgus.util.sdf.primitives.SDFAbstractShape;
import org.sdoaj.jimgus.util.sdf.primitives.SDFLine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class IceFeatherFeature extends Feature<NoneFeatureConfiguration> {
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

            if (i < 0.5) {
                direction = 'x';
            } else {
                direction = switch (random.nextInt(3)) {
                    case 0 -> 'X';
                    case 1 -> 'y';
                    case 2 -> 'Y';
                    default -> throw new IllegalStateException();
                };
            }

            return "d[" + direction + "ad]a";
        }
    };

    public IceFeatherFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private static final float lineLength = 10f;
    private static final float turnAngle = MathHelper.toRadians(15f);
    private static final float lineRadius = 2.0f;

    private static final Quaternion quatXP = new Quaternion(Vector3f.YP, turnAngle, false);
    private static final Quaternion quatXN = new Quaternion(Vector3f.YP, -turnAngle, false);
    private static final Quaternion quatYP = new Quaternion(Vector3f.XP, turnAngle, false);
    private static final Quaternion quatYN = new Quaternion(Vector3f.XP, -turnAngle, false);

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();

        Vec3f currentPos = Vec3f.ZERO;
        Vec3f direction = Vec3f.ZP;
        Deque<Vec3f> savedPositions = new ArrayDeque<>();

        SDF sdf = null;

        for (char c : lSystem.run(3, random).toCharArray()) {
            switch (c) {
                case '[':
                    savedPositions.addFirst(currentPos);
                    break;
                case ']':
                    currentPos = savedPositions.removeFirst();
                    break;
                case 'a':
                    Vec3f newPos = currentPos.add(direction.multiply(lineLength));
                    SDF line = new SDFLine(currentPos, newPos).radius(lineRadius);
                    sdf = (sdf == null) ? line : new SDFUnion().setSources(sdf, line);
                    currentPos = newPos;
                    break;
                case 'x':
                    direction = direction.rotate(quatXP);
                    break;
                case 'X':
                    direction = direction.rotate(quatXN);
                    break;
                case 'y':
                    direction = direction.rotate(quatYP);
                    break;
                case 'Y':
                    direction = direction.rotate(quatYN);
                    break;
                default:
                    break;
            }
        }

        sdf = new SDFAbstractShape().setSource(sdf).setBlock(Blocks.BLUE_ICE);
        sdf.fill(world, pos);

        return true;
    }
}
