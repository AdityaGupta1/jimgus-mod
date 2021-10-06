package org.sdoaj.jimgus.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.math.MathHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

// based partially on https://github.com/paulevsGitch/BCLib/blob/7d9b56b6e72bd4b9c9752eb0a82f908ca23ec9f7/src/main/java/ru/bclib/util/BlocksHelper.java
public class BlockHelper {
    public static final int FLAG_UPDATE_BLOCK = 1;
    public static final int FLAG_SEND_CLIENT_CHANGES = 2;
    public static final int FLAG_NO_RERENDER = 4;
    public static final int FORCE_RERENDER = 8;
    public static final int FLAG_IGNORE_OBSERVERS = 16;

    public static final int SET_SILENT = FLAG_UPDATE_BLOCK | FLAG_SEND_CLIENT_CHANGES | FLAG_IGNORE_OBSERVERS;

    public static void setWithoutUpdate(LevelAccessor world, BlockPos pos, BlockState state) {
        world.setBlock(pos, state, SET_SILENT);
    }

    public static void fillBox(LevelAccessor world, BlockPos pos1, BlockPos pos2, Block block) {
        fillBox(world, pos1, pos2, block.defaultBlockState());
    }

    // because SDFBox is bad
    public static void fillBox(LevelAccessor world, BlockPos pos1, BlockPos pos2, BlockState state) {
        int x = Math.min(pos1.getX(), pos2.getX());
        int y = Math.min(pos1.getY(), pos2.getY());
        int z = Math.min(pos1.getZ(), pos2.getZ());
        int dx = Math.abs(pos2.getX() - pos1.getX());
        int dy = Math.abs(pos2.getY() - pos1.getY());
        int dz = Math.abs(pos2.getZ() - pos1.getZ());

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int i = 0; i <= dx; i++) {
            for (int j = 0; j <= dy; j++) {
                for (int k = 0; k <= dz; k++) {
                    pos.set(x + i, y + j, z + k);
                    setWithoutUpdate(world, pos, state);
                }
            }
        }
    }

    public static void fillTriangle(LevelAccessor world, Vec3f pos1, Vec3f pos2, Vec3f pos3, float thickness, Block block) {
        fillTriangle(world, pos1, pos2, pos3, thickness, block.defaultBlockState());
    }

    public static void fillTriangle(LevelAccessor world, Vec3f pos1, Vec3f pos2, Vec3f pos3, float thickness, BlockState state) {
        fillTriangle((pos) -> setWithoutUpdate(world, pos, state), pos1, pos2, pos3, thickness);
    }

    public static void fillTriangle(StructureWorld world, Vec3f pos1, Vec3f pos2, Vec3f pos3, float thickness, Block block) {
        fillTriangle(world, pos1, pos2, pos3, thickness, block.defaultBlockState());
    }

    public static void fillTriangle(StructureWorld world, Vec3f pos1, Vec3f pos2, Vec3f pos3, float thickness, BlockState state) {
        fillTriangle((pos) -> world.setBlock(pos, state), pos1, pos2, pos3, thickness);
    }

    // very bad implementation that checks every box in bounding volume
    private static void fillTriangle(Consumer<BlockPos> placeFunction, Vec3f pos1, Vec3f pos2, Vec3f pos3, float thickness) {
        final float thicknessRadius = thickness / 2;

        Vec3f min = Vec3f.min(pos1, Vec3f.min(pos2, pos3));
        Vec3f max = Vec3f.max(pos1, Vec3f.max(pos2, pos3));

        BlockPos minPos = min.toBlockPos(f -> (int) Math.floor(f));
        BlockPos maxPos = max.toBlockPos(f -> (int) Math.ceil(f));

        final Vec3f edge12 = pos2.subtract(pos1);
        final Vec3f edge23 = pos3.subtract(pos2);
        final Vec3f edge31 = pos1.subtract(pos3);

        final Vec3f normal = edge12.cross(edge23);
        final float area = normal.length(); // area / 2

        for (int x = minPos.getX(); x < maxPos.getX(); x++) {
            for (int y = minPos.getY(); y < maxPos.getY(); y++) {
                for (int z = minPos.getZ(); z < maxPos.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Vec3f posF = new Vec3f(x + 0.5f, y + 0.5f, z + 0.5f);

                    // project point onto plane
                    Vec3f planeToPoint = posF.subtract(pos1);
                    Vec3f projOntoNormal = planeToPoint.proj(normal);
                    Vec3f planePoint = pos1.add(planeToPoint.subtract(projOntoNormal));

                    // distance check
                    float distanceToPlane = posF.subtract(planePoint).length();
                    if (Math.abs(distanceToPlane) > thicknessRadius) {
                        continue;
                    }

                    // barycentric check
                    float A1 = edge12.cross(posF.subtract(pos2)).length();
                    float A2 = edge23.cross(posF.subtract(pos3)).length();
                    float A3 = edge31.cross(posF.subtract(pos1)).length();
                    if (A1 + A2 + A3 > area) {
                        continue;
                    }

                    // place block
                    placeFunction.accept(pos);
                }
            }
        }

        // // form orthonormal basis [xAxis, yAxis]
        // Vec3f xAxis = pos2.subtract(pos1).normalize();
        // Vec3f side = pos3.subtract(pos2);
        // Vec3f yAxis = side.subtract(side.proj(xAxis));
        //
        // float height =
    }
}
