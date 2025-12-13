package com.shrhang.create_logistics_by_shh;

import com.shrhang.create_logistics_by_shh.registries.MenuTypeRegister;
import com.simibubi.create.content.logistics.stockTicker.StockKeeperRequestScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = ShHsCreateLogistics.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ShHsCreateLogistics.MODID, value = Dist.CLIENT)
public class ShHsCreateLogisticsClient {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuTypeRegister.REMOTE_STOCK_KEEPER_REQUEST.get(), StockKeeperRequestScreen::new);
    }
}
