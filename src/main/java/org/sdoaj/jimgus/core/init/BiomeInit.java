package org.sdoaj.jimgus.core.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.biome.ModBiomes;

@Mod.EventBusSubscriber(modid = Jimgus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Jimgus.MODID);

    public static final RegistryObject<Biome> TENTACLE_BIOME = BIOMES.register("tentacle_biome", ModBiomes::tentacleBiome);
    public static final RegistryObject<Biome> MUSHROOM_BIOME = BIOMES.register("mushroom_biome", ModBiomes::mushroomBiome);
    public static final RegistryObject<Biome> CRYSTAL_BIOME = BIOMES.register("crystal_biome", ModBiomes::crystalBiome);

    @SubscribeEvent
    public static void onRegisterBiomes(final RegistryEvent.Register<Biome> event) {
        registerBiome(TENTACLE_BIOME.get(), BiomeManager.BiomeType.COOL, BiomeDictionary.Type.MOUNTAIN);
        registerBiome(MUSHROOM_BIOME.get(), BiomeManager.BiomeType.WARM, BiomeDictionary.Type.MUSHROOM);
        registerBiome(CRYSTAL_BIOME.get(), BiomeManager.BiomeType.COOL, BiomeDictionary.Type.MOUNTAIN);
    }

    public static void registerBiome(Biome biome, BiomeManager.BiomeType type, BiomeDictionary.Type... types) {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, biome.getRegistryName());
        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type,new BiomeManager.BiomeEntry(key, 10));
    }
}
