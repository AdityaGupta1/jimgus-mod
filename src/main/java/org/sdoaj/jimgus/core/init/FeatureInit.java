package org.sdoaj.jimgus.core.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sdoaj.jimgus.Jimgus;
import org.sdoaj.jimgus.world.feature.CrystalFeature;

@Mod.EventBusSubscriber(modid = Jimgus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureInit {
    public static final Feature<NoneFeatureConfiguration> CRYSTAL_FEATURE = new CrystalFeature();

    @SubscribeEvent
    public static void onRegisterFeatures(RegistryEvent.Register<Feature<?>> event) {
        Jimgus.register(event.getRegistry(), CRYSTAL_FEATURE, "crystal_feature");
    }
}
