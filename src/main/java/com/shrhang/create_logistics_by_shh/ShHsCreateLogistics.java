package com.shrhang.create_logistics_by_shh;

import com.shrhang.create_logistics_by_shh.registries.ComponentRegister;
import com.shrhang.create_logistics_by_shh.registries.CreativeTabRegister;
import com.shrhang.create_logistics_by_shh.registries.ItemRegister;
import com.shrhang.create_logistics_by_shh.registries.MenuTypeRegister;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ShHsCreateLogistics.MODID)
public class ShHsCreateLogistics {
    public static final String MODID = "create_logistics_by_shh";

    public ShHsCreateLogistics(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(this);

        ComponentRegister.register(modEventBus);
        CreativeTabRegister.register(modEventBus);
        ItemRegister.register(modEventBus);
        MenuTypeRegister.register(modEventBus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
