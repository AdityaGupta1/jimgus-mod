package org.sdoaj.jimgus.core.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.antlr.v4.parse.ResyncToEndOfRuleBlock;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.biomes.ModBiomes;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Jimgus.MODID);

    public static final RegistryObject<Biome> CRYSTAL_BIOME = BIOMES.register("crystal_biome", ModBiomes::crystalBiome);

    public static void registerBiomes() {
        registerBiome(CRYSTAL_BIOME.get(), BiomeManager.BiomeType.COOL, BiomeDictionary.Type.MOUNTAIN);
    }

    public static void registerBiome(Biome biome, BiomeManager.BiomeType type, BiomeDictionary.Type... types) {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, biome.getRegistryName());
        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type,new BiomeManager.BiomeEntry(key, 10));
    }
}
