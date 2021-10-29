package org.sdoaj.jimgus.util.sdf;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.sdoaj.jimgus.util.BlockHelper;
import org.sdoaj.jimgus.util.math.Vec3f;
import org.sdoaj.jimgus.world.structure.StructureWorld;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

// based largely on https://github.com/paulevsGitch/BCLib/blob/main/src/main/java/ru/bclib/sdf/SDF.java
public abstract class SDF {
    private Predicate<BlockState> canReplace = state -> state.getMaterial().isReplaceable();

    public SDF setCanReplace(Predicate<BlockState> canReplace) {
        this.canReplace = canReplace;
        return this;
    }

    public SDF addCanReplace(Predicate<BlockState> canReplace) {
        this.canReplace = this.canReplace.or(canReplace);
        return this;
    }

    public final float distance(BlockPos pos) {
        return distance(new Vec3f(pos));
    }

    public final float distance(float x, float y, float z) {
        return distance(new Vec3f(x, y, z));
    }

    // local pos
    // negative inside, positive outside
    public abstract float distance(Vec3f pos);

    public final BlockState getBlockState(BlockPos pos) {
        return getBlockState(new Vec3f(pos));
    }

    // local pos
    public abstract BlockState getBlockState(Vec3f pos);

    public final void fill(LevelAccessor world, BlockPos start) {
        this.fill(world, start, false);
    }

    public void fill(LevelAccessor world, BlockPos start, boolean ignoreCanReplace) {
        this.fill(world, start, ignoreCanReplace, (pos, state) -> BlockHelper.setWithoutUpdate(world, pos, state));
    }

    // assumes the origin is always within the SDF
    private void fill(LevelAccessor world, BlockPos start, boolean ignoreCanReplace, BiConsumer<BlockPos, BlockState> placeFunction) {
        Map<BlockPos, BlockState> blocks = new HashMap<>();
        Set<BlockPos> done = new HashSet<>();
        Set<BlockPos> ends = new HashSet<>();
        Set<BlockPos> add = new HashSet<>();
        BlockPos origin = new BlockPos(0, 0, 0);
        ends.add(origin);
        done.add(origin);
        if (ignoreCanReplace || canReplace.test(world.getBlockState(start))) {
            blocks.put(start, getBlockState(BlockPos.ZERO));
        }

        while (!ends.isEmpty()) {
            for (BlockPos center : ends) {
                for (Direction direction : Direction.values()) {
                    BlockPos posLocal = center.relative(direction);
                    BlockPos posWorld = posLocal.offset(start);

                    if (!done.contains(posLocal) && this.distance(posLocal) <= 0) {
                        if (ignoreCanReplace || canReplace.test(world.getBlockState(posWorld))) {
                            blocks.put(posWorld, getBlockState(posLocal));
                            add.add(posLocal);
                        }
                    }
                }
            }

            done.addAll(ends);
            ends.clear();
            ends.addAll(add);
            add.clear();
        }

        for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
            placeFunction.accept(entry.getKey(), entry.getValue());
        }
    }

    // setting world to null is kinda bad but world will never be used since ignoreCanReplace is true
    public void fill(StructureWorld world, BlockPos start) {
        this.fill(null, start, true, world::setBlock);
    }
}
