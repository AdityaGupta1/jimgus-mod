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

import static net.minecraftforge.common.BiomeManager.BiomeType;
import static net.minecraftforge.common.BiomeDictionary.Type;

@Mod.EventBusSubscriber(modid = Jimgus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Jimgus.MODID);

    public static final RegistryObject<Biome> TENTACLE_BIOME = BIOMES.register("tentacle_biome", ModBiomes::tentacleBiome);
    public static final RegistryObject<Biome> MUSHROOM_BIOME = BIOMES.register("mushroom_biome", ModBiomes::mushroomBiome);
    public static final RegistryObject<Biome> CRYSTAL_BIOME = BIOMES.register("crystal_biome", ModBiomes::crystalBiome);
    // public static final RegistryObject<Biome> NEON_GENESIS_BIOME = BIOMES.register("neon_genesis_biome", ModBiomes::neonGenesisBiome);
    public static final RegistryObject<Biome> BEANSTALK_BIOME = BIOMES.register("beanstalk_biome", ModBiomes::beanstalkBiome);
    public static final RegistryObject<Biome> ICE_FEATHER_BIOME = BIOMES.register("ice_feather_biome", ModBiomes::iceFeatherBiome);
    public static final RegistryObject<Biome> MUSHROOM_LOG_BIOME = BIOMES.register("mushroom_log_biome", ModBiomes::mushroomLogBiome);
    public static final RegistryObject<Biome> PEPSI_BIOME = BIOMES.register("pepsi_biome", ModBiomes::pepsiBiome);

    @SubscribeEvent
    public static void onRegisterBiomes(final RegistryEvent.Register<Biome> event) {
        registerBiome(TENTACLE_BIOME.get(), BiomeType.COOL, Type.MOUNTAIN);
        registerBiome(MUSHROOM_BIOME.get(), BiomeType.WARM, Type.MUSHROOM);
        registerBiome(CRYSTAL_BIOME.get(), BiomeType.COOL, Type.MOUNTAIN, Type.MAGICAL);
        // registerBiome(NEON_GENESIS_BIOME.get(), BiomeType.COOL, Type.MAGICAL);
        registerBiome(BEANSTALK_BIOME.get(), BiomeType.COOL, Type.MAGICAL);
        registerBiome(ICE_FEATHER_BIOME.get(), BiomeType.ICY, Type.COLD, Type.SNOWY);
        registerBiome(MUSHROOM_LOG_BIOME.get(), BiomeType.WARM, Type.FOREST, Type.MUSHROOM, Type.MAGICAL);
        registerBiome(PEPSI_BIOME.get(), BiomeType.WARM, Type.MAGICAL);
    }

    private static void registerBiome(Biome biome, BiomeType type, Type... types) {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, biome.getRegistryName());
        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, 10));
    }
}
