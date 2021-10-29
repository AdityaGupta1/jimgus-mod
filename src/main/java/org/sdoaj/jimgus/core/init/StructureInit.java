package org.sdoaj.jimgus.core.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.structure.feature.BigMushroomStructureFeature;
import org.sdoaj.jimgus.world.structure.feature.FallenMushroomLogStructureFeature;
import org.sdoaj.jimgus.world.structure.feature.IceFeatherStructureFeature;
import org.sdoaj.jimgus.world.structure.feature.LeafSphereTreeStructureFeature;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Jimgus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StructureInit {
    public static final StructureFeature<NoneFeatureConfiguration> BIG_MUSHROOM = new BigMushroomStructureFeature();
    public static final StructureFeature<NoneFeatureConfiguration> ICE_FEATHER = new IceFeatherStructureFeature();
    public static final StructureFeature<NoneFeatureConfiguration> FALLEN_MUSHROOM_LOG = new FallenMushroomLogStructureFeature();
    public static final StructureFeature<NoneFeatureConfiguration> LEAF_SPHERE_TREE = new LeafSphereTreeStructureFeature();

    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BIG_MUSHROOM = BIG_MUSHROOM.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ICE_FEATHER = ICE_FEATHER.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_FALLEN_MUSHROOM_LOG = FALLEN_MUSHROOM_LOG.configured(FeatureConfiguration.NONE);
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_LEAF_SPHERE_TREE = LEAF_SPHERE_TREE.configured(FeatureConfiguration.NONE);

    @SubscribeEvent
    public static void onRegisterStructures(RegistryEvent.Register<StructureFeature<?>> event) {
        IForgeRegistry<StructureFeature<?>> registry = event.getRegistry();

        Jimgus.register(registry, BIG_MUSHROOM, "big_mushroom_structure");
        Jimgus.register(registry, ICE_FEATHER, "ice_feather_structure");
        Jimgus.register(registry, FALLEN_MUSHROOM_LOG, "fallen_mushroom_log_structure");
        Jimgus.register(registry, LEAF_SPHERE_TREE, "leaf_sphere_tree_structure");

        // https://www.minecraftforum.net/forums/minecraft-java-edition/discussion/3042476-whats-the-best-way-to-customize-my-world-settings#c14
        setupStructure(BIG_MUSHROOM, CONFIGURED_BIG_MUSHROOM,
                new StructureFeatureConfiguration(12, 4, 4093123), false);
        setupStructure(ICE_FEATHER, CONFIGURED_ICE_FEATHER,
                new StructureFeatureConfiguration(2, 0, 13201203), false);
        setupStructure(FALLEN_MUSHROOM_LOG, CONFIGURED_FALLEN_MUSHROOM_LOG,
                new StructureFeatureConfiguration(4, 2, 32312931), false);
        setupStructure(LEAF_SPHERE_TREE, CONFIGURED_LEAF_SPHERE_TREE,
                new StructureFeatureConfiguration(4, 2, 129301239), false);

        StructurePieceInit.registerPieces();
    }

    private static <F extends StructureFeature<?>> void setupStructure(F structure, ConfiguredStructureFeature<?, ?> configuredStructure,
                                                                       StructureFeatureConfiguration settings, boolean transformGround) {
        final ResourceLocation registryName = structure.getRegistryName();
        StructureFeature.STRUCTURES_REGISTRY.put(registryName.toString(), structure);

        if (transformGround) {
            StructureFeature.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<StructureFeature<?>>builder()
                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, settings)
                        .build();

        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, registryName, configuredStructure);

        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(genSettings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = genSettings.getValue().structureSettings().structureConfig();
            if (structureMap instanceof ImmutableMap) {
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, settings);
                genSettings.getValue().structureSettings().structureConfig = tempMap;
            } else {
                structureMap.put(structure, settings);
            }
        });
    }
}
