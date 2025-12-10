package com.shrhang.create_logistics_by_shh;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ShHsCreateLogistics.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ShHsCreateLogistics.MODID, value = Dist.CLIENT)
public class ShHsCreateLogisticsClient {
    public ShHsCreateLogisticsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ShHsCreateLogistics.LOGGER.info("HELLO FROM CLIENT SETUP");
        ShHsCreateLogistics.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
