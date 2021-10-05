package org.sdoaj.jimgus.world.biome;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import org.sdoaj.jimgus.core.init.FeatureInit;
import org.sdoaj.jimgus.core.init.StructureInit;

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
                FeatureInit.TENTACLE.configured(FeatureConfiguration.NONE)
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
                FeatureInit.MUSHROOM.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(1));
        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.MINISHROOM.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(5));

        generationSettingsBuilder.addStructureStart(StructureInit.BIG_MUSHROOM
                .configured(FeatureConfiguration.NONE));

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
                FeatureInit.CRYSTAL.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(1));
        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.MINISHROOM.configured(FeatureConfiguration.NONE)
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

    public static Biome neonGenesisBiome() {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettingsBuilder);

        BiomeGenerationSettings.Builder generationSettingsBuilder
                = (new BiomeGenerationSettings.Builder()).surfaceBuilder(basicBuilder(Blocks.BLACK_CONCRETE));
        addDefaultFeatures(generationSettingsBuilder);

        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.NEON_TOWER.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).rarity(12));
        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.NEON_BOX_SMALL.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(2));
        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.NEON_BOX_BIG.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(1));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .depth(0.1f)
                .scale(0.1f)
                .temperature(0.2f)
                .downfall(0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0x30753F)
                        .waterColor(0x40E340)
                        .waterFogColor(0x053314)
                        .skyColor(0x000000)
                        .build())
                .mobSpawnSettings(spawnSettingsBuilder.build())
                .generationSettings(generationSettingsBuilder.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }

    public static Biome cloudIslandBiome() {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnSettingsBuilder);

        BiomeGenerationSettings.Builder generationSettingsBuilder
                = (new BiomeGenerationSettings.Builder()).surfaceBuilder(basicBuilder(Blocks.WHITE_WOOL));
        addDefaultFeatures(generationSettingsBuilder);

        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.CLOUD_ISLAND.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).rarity(3));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .depth(0.05f)
                .scale(0.2f)
                .temperature(0.3f)
                .downfall(0.5f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0x768E99)
                        .waterColor(defaultWaterColor)
                        .waterFogColor(defaultWaterFogColor)
                        .skyColor(0x98C3D6)
                        .build())
                .mobSpawnSettings(spawnSettingsBuilder.build())
                .generationSettings(generationSettingsBuilder.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }

    public static Biome iceFeatherBiome() {
        MobSpawnSettings.Builder spawnSettingsBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.snowySpawns(spawnSettingsBuilder);

        BiomeGenerationSettings.Builder generationSettingsBuilder
                = (new BiomeGenerationSettings.Builder()).surfaceBuilder(SurfaceBuilders.ICE_SPIKES);
        addDefaultFeatures(generationSettingsBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettingsBuilder);

        generationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                FeatureInit.ICE_WIREFRAME_FEATURE.configured(FeatureConfiguration.NONE)
                        .decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(3));

        generationSettingsBuilder.addStructureStart(StructureInit.ICE_FEATHER
                .configured(FeatureConfiguration.NONE));

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.SNOW)
                .biomeCategory(Biome.BiomeCategory.ICY)
                .depth(0.15f)
                .scale(0.2f)
                .temperature(0.3f)
                .downfall(0.7f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0x5D6873)
                        .waterColor(defaultWaterColor)
                        .waterFogColor(defaultWaterFogColor)
                        .skyColor(0x94A2B5)
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

    public static ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> basicBuilder(Block block) {
        return basicBuilder(block.defaultBlockState());
    }

    public static ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> basicBuilder(BlockState block) {
        return SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderBaseConfiguration(block, block, block));
    }
}
