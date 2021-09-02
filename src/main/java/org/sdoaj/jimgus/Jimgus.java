package org.sdoaj.jimgus;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdoaj.jimgus.core.init.BiomeInit;
import org.sdoaj.jimgus.core.init.BlockInit;
import org.sdoaj.jimgus.core.init.ItemInit;

@Mod(Jimgus.MODID)
public class Jimgus {
    public static final String MODID = "jimgus";

    private static final Logger LOGGER = LogManager.getLogger();

    public Jimgus() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BiomeInit.BIOMES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("jimgus setup");
    }
}
