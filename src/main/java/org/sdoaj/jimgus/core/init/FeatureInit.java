package org.sdoaj.jimgus.core.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.feature.*;

@Mod.EventBusSubscriber(modid = Jimgus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureInit {
    public static final Feature<NoneFeatureConfiguration> TENTACLE = new TentacleFeature();
    public static final Feature<NoneFeatureConfiguration> MUSHROOM = new MushroomFeature();
    public static final Feature<NoneFeatureConfiguration> MINISHROOM = new MinishroomFeature();
    public static final Feature<NoneFeatureConfiguration> CRYSTAL = new CrystalFeature();
    public static final Feature<NoneFeatureConfiguration> NEON_BOX_BIG =
            new NeonBoxFeature(2, 4, 3, 6, 2, 4, Blocks.BLACK_CONCRETE);
    public static final Feature<NoneFeatureConfiguration> NEON_BOX_SMALL =
            new NeonBoxFeature(1, 3, 1, 3, 1, 3, Blocks.LIME_CONCRETE);
    public static final Feature<NoneFeatureConfiguration> NEON_TOWER = new NeonTowerFeature();
    public static final Feature<NoneFeatureConfiguration> CLOUD_ISLAND = new CloudIslandFeature();
    public static final Feature<NoneFeatureConfiguration> ICE_FEATHER_FEATURE = new IceFeatherFeature();
    public static final Feature<NoneFeatureConfiguration> ICE_WIREFRAME_FEATURE = new IceWireframeFeature();

    @SubscribeEvent
    public static void onRegisterFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        Jimgus.register(registry, TENTACLE, "tentacle_feature");
        Jimgus.register(registry, MUSHROOM, "mushroom_feature");
        Jimgus.register(registry, MINISHROOM, "minishroom_feature");
        Jimgus.register(registry, CRYSTAL, "crystal_feature");
        Jimgus.register(registry, NEON_BOX_BIG, "neon_box_big_feature");
        Jimgus.register(registry, NEON_BOX_SMALL, "neon_box_small_feature");
        Jimgus.register(registry, NEON_TOWER, "neon_tower_feature");
        Jimgus.register(registry, CLOUD_ISLAND, "cloud_island_feature");
        Jimgus.register(registry, ICE_FEATHER_FEATURE, "ice_feather_feature");
        Jimgus.register(registry, ICE_WIREFRAME_FEATURE, "ice_wireframe_feature");
    }
}
