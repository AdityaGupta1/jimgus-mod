package org.sdoaj.jimgus.core.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.structure.VoxelPiece;

public class StructurePieceInit {
    public static final StructurePieceType VOXEL_PIECE = VoxelPiece::new;

    public static void registerPieces() {
        registerPiece(VOXEL_PIECE, "voxel_piece");
    }

    private static void registerPiece(StructurePieceType structurePiece, String name) {
        Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(Jimgus.MODID, name), structurePiece);
    }
}
