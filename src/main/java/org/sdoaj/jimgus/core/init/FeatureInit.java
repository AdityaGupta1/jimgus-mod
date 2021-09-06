package org.sdoaj.jimgus.core.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.feature.*;

@Mod.EventBusSubscriber(modid = Jimgus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureInit {
    public static final Feature<NoneFeatureConfiguration> TENTACLE_FEATURE = new TentacleFeature();
    public static final Feature<NoneFeatureConfiguration> MUSHROOM_FEATURE = new MushroomFeature();
    public static final Feature<NoneFeatureConfiguration> MINISHROOM_FEATURE = new MinishroomFeature();
    public static final Feature<NoneFeatureConfiguration> CRYSTAL_FEATURE = new CrystalFeature();
    public static final Feature<NoneFeatureConfiguration> NEON_BOX_BIG_FEATURE =
            new NeonBoxFeature(2, 4, 3, 6, 2, 4, Blocks.BLACK_CONCRETE);
    public static final Feature<NoneFeatureConfiguration> NEON_BOX_SMALL_FEATURE =
            new NeonBoxFeature(1, 3, 1, 3, 1, 3, Blocks.LIME_CONCRETE);
    public static final Feature<NoneFeatureConfiguration> NEON_TOWER_FEATURE = new NeonTowerFeature();

    @SubscribeEvent
    public static void onRegisterFeatures(RegistryEvent.Register<Feature<?>> event) {
        Jimgus.register(event.getRegistry(), TENTACLE_FEATURE, "tentacle_feature");
        Jimgus.register(event.getRegistry(), MUSHROOM_FEATURE, "mushroom_feature");
        Jimgus.register(event.getRegistry(), MINISHROOM_FEATURE, "minishroom_feature");
        Jimgus.register(event.getRegistry(), CRYSTAL_FEATURE, "crystal_feature");
        Jimgus.register(event.getRegistry(), NEON_BOX_BIG_FEATURE, "neon_box_big_feature");
        Jimgus.register(event.getRegistry(), NEON_BOX_SMALL_FEATURE, "neon_box_small_feature");
        Jimgus.register(event.getRegistry(), NEON_TOWER_FEATURE, "neon_tower_feature");
    }
}
