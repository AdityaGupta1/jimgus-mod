package org.sdoaj.jimgus.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.sdoaj.jimgus.util.math.MathHelper;

import java.util.Random;

public abstract class AbstractStructureFeature extends StructureFeature<NoneFeatureConfiguration> {
    public AbstractStructureFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    protected abstract void fillStructureWorld(StructureWorld world, BlockPos pos, Random random);

    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return AbstractStructureStart::new;
    }

    public static class AbstractStructureStart extends StructureStart<NoneFeatureConfiguration> {
        public AbstractStructureStart(StructureFeature<NoneFeatureConfiguration> p_163595_, ChunkPos p_163596_, int p_163597_, long p_163598_) {
            super(p_163595_, p_163596_, p_163597_, p_163598_);
        }

        @Override
        public void generatePieces(RegistryAccess registryAccess, ChunkGenerator chunkGenerator,
                                   StructureManager structureManager, ChunkPos chunkPos, Biome biome,
                                   NoneFeatureConfiguration config, LevelHeightAccessor heightAccessor) {
            int x = (chunkPos.x << 4) | MathHelper.nextInt(this.random, 4, 12);
            int z = (chunkPos.z << 4) | MathHelper.nextInt(this.random, 4, 12);
            int y = chunkGenerator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, heightAccessor);
            BlockPos start = new BlockPos(x, y, z);

            this.pieces.add(new VoxelPiece(world ->
                    ((AbstractStructureFeature) this.getFeature()).fillStructureWorld(world, start, this.random),
                    this.random.nextInt()));
            this.createBoundingBox();
        }
    }
}
