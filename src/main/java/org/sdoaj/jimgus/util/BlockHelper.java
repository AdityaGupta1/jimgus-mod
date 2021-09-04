package org.sdoaj.jimgus.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
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
}
