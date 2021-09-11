package org.sdoaj.jimgus.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.HashMap;
import java.util.Map;

// https://github.com/Beethoven92/BetterEndForge/blob/master/src/main/java/mod/beethoven92/betterendforge/common/world/structure/StructureWorld.java
public class StructureWorld {
    private final Map<ChunkPos, Part> parts = new HashMap<>();
    private ChunkPos lastPos;
    private Part lastPart;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int minZ = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int maxZ = Integer.MIN_VALUE;

    public StructureWorld() {}

    public StructureWorld(CompoundTag tag) {
        minX = tag.getInt("minX");
        minY = tag.getInt("minY");
        minZ = tag.getInt("minZ");
        maxX = tag.getInt("maxX");
        maxY = tag.getInt("maxY");
        maxZ = tag.getInt("maxZ");

        ListTag map = tag.getList("parts", 10); // no idea what the 10 means here
        map.forEach(element -> {
            CompoundTag partTag = (CompoundTag) element;
            Part part = new Part(partTag);
            parts.put(new ChunkPos(partTag.getInt("x"), partTag.getInt("z")), part);
        });
    }

    // covers all chunks the world has blocks in, as well as the exact minY and maxY
    public BoundingBox getBounds() {
        if (minX == Integer.MAX_VALUE || maxX == Integer.MIN_VALUE || minZ == Integer.MAX_VALUE || maxZ == Integer.MIN_VALUE) {
            return new BoundingBox(0, 0, 0, 0, 0, 0);
        }

        return new BoundingBox(minX << 4, minY, minZ << 4, (maxX << 4) | 15, maxY, (maxZ << 4) | 15);
    }

    public void setBlock(BlockPos pos, Block block) {
        this.setBlock(pos, block.defaultBlockState());
    }

    public void setBlock(BlockPos blockPos, BlockState state) {
        ChunkPos pos = new ChunkPos(blockPos);

        if (pos.equals(lastPos)) {
            lastPart.setBlock(blockPos, state);
            return;
        }

        Part part = parts.get(pos);

        if (part == null) {
            part = new Part();
            parts.put(pos, part);

            minX = Math.min(minX, pos.x);
            maxX = Math.max(maxX, pos.x);
            minZ = Math.min(minZ, pos.z);
            maxZ = Math.max(maxZ, pos.z);
        }

        minY = Math.min(minY, blockPos.getY());
        maxY = Math.max(maxY, blockPos.getY());

        part.setBlock(blockPos, state);

        lastPos = pos;
        lastPart = part;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("minX", this.minX);
        tag.putInt("minY", this.minY);
        tag.putInt("minZ", this.minZ);
        tag.putInt("maxX", this.maxX);
        tag.putInt("maxY", this.maxY);
        tag.putInt("maxZ", this.maxZ);

        ListTag map = new ListTag();
        parts.forEach((pos, part) -> map.add(part.toNBT(pos.x, pos.z)));
        tag.put("parts", map);

        return tag;
    }

    public void placeChunk(WorldGenLevel world, ChunkPos chunkPos) {
        Part part = parts.get(chunkPos);

        if (part != null) {
            part.placeChunk(world.getChunk(chunkPos.x, chunkPos.z));
        }
    }

    private static class Part {
        private final Map<BlockPos, BlockState> blocks = new HashMap<>();

        public Part() {}

        public Part(CompoundTag tag) {
            ListTag blocksMap = tag.getList("blocks", 10);
            ListTag statesMap = tag.getList("states", 10);
            BlockState[] states = new BlockState[statesMap.size()];
            for (int i = 0; i < states.length; i++) {
                states[i] = NbtUtils.readBlockState((CompoundTag) statesMap.get(i));
            }

            blocksMap.forEach((element) -> {
                CompoundTag block = (CompoundTag) element;
                BlockPos pos = NbtUtils.readBlockPos(block.getCompound("pos"));
                int stateID = block.getInt("state");
                BlockState state = stateID < states.length ? states[stateID] : Block.stateById(stateID);
                blocks.put(pos, state);
            });
        }

        public void setBlock(BlockPos pos, BlockState state) {
            blocks.put(new BlockPos(pos.getX() & 15, pos.getY(), pos.getZ() & 15), state);
        }

        public void placeChunk(ChunkAccess chunk) {
            blocks.forEach((pos, state) -> chunk.setBlockState(pos, state, false));
        }

        public CompoundTag toNBT(int x, int z) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("x", x);
            tag.putInt("z", z);

            ListTag blocksMap = new ListTag();
            ListTag statesMap = new ListTag();

            int id = 0;
            Map<BlockState, Integer> states = new HashMap<>();

            for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
                BlockPos pos = entry.getKey();
                BlockState state = entry.getValue();

                int stateID = states.getOrDefault(state, -1);
                if (stateID < 0) {
                    stateID = id++;
                    states.put(state, stateID);
                    statesMap.add(NbtUtils.writeBlockState(state));
                }

                CompoundTag block = new CompoundTag();
                block.put("pos", NbtUtils.writeBlockPos(pos));
                block.putInt("state", stateID);
                blocksMap.add(block);
            }

            tag.put("blocks", blocksMap);
            tag.put("states", statesMap);
            return tag;
        }
    }
}
