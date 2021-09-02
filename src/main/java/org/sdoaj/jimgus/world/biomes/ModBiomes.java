package org.sdoaj.jimgus.world.biomes;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class ModBiomes {
    public static Biome crystalBiome() {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettingsBuilder);

        BiomeGenerationSettings.Builder generationSettingsBuilder
                = (new BiomeGenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.STONE);
        BiomeDefaultFeatures.addDefaultCarvers(generationSettingsBuilder);
        BiomeDefaultFeatures.addDefaultLakes(generationSettingsBuilder);

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.EXTREME_HILLS)
                .depth(0.12f)
                .scale(1.2f)
                .temperature(0.2f)
                .downfall(0.3f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(10518688)
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .skyColor(0)
                        .build())
                .mobSpawnSettings(spawnSettingsBuilder.build())
                .generationSettings(generationSettingsBuilder.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }
}
