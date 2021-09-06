package org.sdoaj.jimgus.world.biome;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.sdoaj.jimgus.core.init.FeatureInit;

public class ModBiomes {
    private static final int defaultWaterColor = 0x3F76E4;
    private static final int defaultWaterFogColor = 0x050533;

    public static Biome tentacleBiome() {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettingsBuilder);

        BiomeGenerationSettings.Builder generationSettingsBuilder
                = (new BiomeGenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.STONE);
        addDefaultFeatures(generationSettingsBuilder);

        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.TENTACLE_FEATURE.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(2));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.EXTREME_HILLS)
                .depth(0.4f)
                .scale(0.8f)
                .temperature(0.2f)
                .downfall(0.3f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0x526066)
                        .waterColor(defaultWaterColor)
                        .waterFogColor(defaultWaterFogColor)
                        .skyColor(0x94ABB5)
                        .build())
                .mobSpawnSettings(spawnSettingsBuilder.build())
                .generationSettings(generationSettingsBuilder.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }

    public static Biome mushroomBiome() {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettingsBuilder);

        BiomeGenerationSettings.Builder generationSettingsBuilder
                = (new BiomeGenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.MYCELIUM);
        addDefaultFeatures(generationSettingsBuilder);

        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.MUSHROOM_FEATURE.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(1));
        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.MINISHROOM_FEATURE.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(5));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.MUSHROOM)
                .depth(0.2f)
                .scale(0.3f)
                .temperature(0.9f)
                .downfall(0.3f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(12638463)
                        .waterColor(defaultWaterColor)
                        .waterFogColor(defaultWaterFogColor)
                        .skyColor(0xA37FA3)
                        .build())
                .mobSpawnSettings(spawnSettingsBuilder.build())
                .generationSettings(generationSettingsBuilder.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }

    public static Biome crystalBiome() {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettingsBuilder);

        BiomeGenerationSettings.Builder generationSettingsBuilder
                = (new BiomeGenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.STONE);
        addDefaultFeatures(generationSettingsBuilder);

        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.CRYSTAL_FEATURE.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(1));
        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.MINISHROOM_FEATURE.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(2));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.EXTREME_HILLS)
                .depth(0.3f)
                .scale(0.5f)
                .temperature(0.2f)
                .downfall(0.3f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0x526066)
                        .waterColor(defaultWaterColor)
                        .waterFogColor(defaultWaterFogColor)
                        .skyColor(0x94ABB5)
                        .build())
                .mobSpawnSettings(spawnSettingsBuilder.build())
                .generationSettings(generationSettingsBuilder.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }

    public static void addDefaultFeatures(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarvers(builder);
        BiomeDefaultFeatures.addDefaultLakes(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
    }
}
