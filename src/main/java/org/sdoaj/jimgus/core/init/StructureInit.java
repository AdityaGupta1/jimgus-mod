package org.sdoaj.jimgus.core.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.structure.BigMushroomStructureFeature;

@Mod.EventBusSubscriber(modid = Jimgus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StructureInit {
    public static final StructureFeature<NoneFeatureConfiguration> BIG_MUSHROOM = new BigMushroomStructureFeature();

    @SubscribeEvent
    public static void onRegisterStructures(RegistryEvent.Register<StructureFeature<?>> event) {
        IForgeRegistry<StructureFeature<?>> registry = event.getRegistry();

        Jimgus.register(registry, BIG_MUSHROOM, "big_mushroom_structure");

        setupStructure(BIG_MUSHROOM, new StructureFeatureConfiguration(16, 4, 5552345));

        StructurePieceInit.registerPieces();
    }

    private static void setupStructure(StructureFeature<?> structure, StructureFeatureConfiguration settings) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

//        StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
//                .putAll(StructureSettings.DEFAULTS)
//                .put(structure, settings)
//                .build();
    }
}
