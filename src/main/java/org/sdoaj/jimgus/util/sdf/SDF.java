package org.sdoaj.jimgus.util.sdf;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.BlockHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

// based largely on https://github.com/paulevsGitch/BCLib/blob/main/src/main/java/ru/bclib/sdf/SDF.java
public abstract class SDF {
    private Predicate<BlockState> canReplace = state -> state.getMaterial().isReplaceable();

    public SDF setCanReplace(Predicate<BlockState> canReplace) {
        this.canReplace = canReplace;
        return this;
    }

    // negative inside, positive outside
    public float distance(BlockPos pos) {
        return distance(pos.getX(), pos.getY(), pos.getZ());
    }

    public abstract float distance(float x, float y, float z);

    public abstract BlockState getBlockState(BlockPos pos);

    public void fill(LevelAccessor world, BlockPos start) {
        Map<BlockPos, BlockState> blocks = new HashMap<>();
        Set<BlockPos> done = new HashSet<>();
        Set<BlockPos> ends = new HashSet<>();
        Set<BlockPos> add = new HashSet<>();
        ends.add(new BlockPos(0, 0, 0));

        while (!ends.isEmpty()) {
            for (BlockPos center : ends) {
                for (Direction direction : Direction.values()) {
                    BlockPos posLocal = center.relative(direction);
                    BlockPos posWorld = posLocal.offset(start);

                    if (!done.contains(posLocal) && canReplace.test(world.getBlockState(posWorld))
                            && this.distance(posLocal) < 0) {
                        blocks.put(posWorld, getBlockState(posWorld));
                        add.add(posLocal);
                    }
                }
            }

            done.addAll(ends);
            ends.clear();
            ends.addAll(add);
            add.clear();
        }

        for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
            BlockHelper.setWithoutUpdate(world, entry.getKey(), entry.getValue());
        }
    }
}