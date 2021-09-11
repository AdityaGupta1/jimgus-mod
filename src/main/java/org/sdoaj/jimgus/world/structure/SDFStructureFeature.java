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
import org.sdoaj.jimgus.util.sdf.SDF;

import java.util.List;
import java.util.Random;

public abstract class SDFStructureFeature extends StructureFeature<NoneFeatureConfiguration> {
    public SDFStructureFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    protected abstract List<SDF> getSDFs(Random random);

    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return SDFStructureStart::new;
    }

    public static class SDFStructureStart extends StructureStart<NoneFeatureConfiguration> {
        public SDFStructureStart(StructureFeature<NoneFeatureConfiguration> p_163595_, ChunkPos p_163596_, int p_163597_, long p_163598_) {
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

            VoxelPiece piece = new VoxelPiece(world -> {
                SDFStructureFeature structure = (SDFStructureFeature) this.getFeature();
                for (SDF sdf : structure.getSDFs(this.random)) {
                    sdf.fill(world, start);
                }
            });

            this.pieces.add(piece);
            this.createBoundingBox();
        }
    }
}
