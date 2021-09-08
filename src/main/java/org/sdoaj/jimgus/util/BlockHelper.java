package org.sdoaj.jimgus.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

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
}
