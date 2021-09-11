package org.sdoaj.jimgus.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import org.sdoaj.jimgus.core.init.StructurePieceInit;

import java.util.Random;
import java.util.function.Consumer;

public class VoxelPiece extends StructurePiece {
    private final StructureWorld world;

    // null BoundingBox in super constructor is slightly bad but it is set manually later
    public VoxelPiece(Consumer<StructureWorld> consumer) {
        super(StructurePieceInit.VOXEL_PIECE, 0, null);
        this.world = new StructureWorld();
        consumer.accept(world);
        this.boundingBox = world.getBounds();
    }

    public VoxelPiece(ServerLevel level, CompoundTag tag) {
        super(StructurePieceInit.VOXEL_PIECE, tag);
        this.world = new StructureWorld(tag);
        this.boundingBox = world.getBounds();
    }

    @Override
    protected void addAdditionalSaveData(ServerLevel level, CompoundTag tag) {
        tag.put("world", this.world.toNBT());
    }

    @Override
    public boolean postProcess(WorldGenLevel world, StructureFeatureManager structureManager,
                               ChunkGenerator chunkGenerator, Random random, BoundingBox bounds,
                               ChunkPos chunkPos, BlockPos blockPos) {
        this.world.placeChunk(world, chunkPos);
        return true;
    }
}
