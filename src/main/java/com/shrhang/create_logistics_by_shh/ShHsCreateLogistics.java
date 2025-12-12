package com.shrhang.create_logistics_by_shh;

import com.shrhang.create_logistics_by_shh.registries.ComponentRegister;
import com.shrhang.create_logistics_by_shh.registries.CreativeTabRegister;
import com.shrhang.create_logistics_by_shh.registries.ItemRegister;
import com.shrhang.create_logistics_by_shh.registries.MenuTypeRegister;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ShHsCreateLogistics.MODID)
public class ShHsCreateLogistics {
    public static final String MODID = "create_logistics_by_shh";
    public static final Logger LOGGER = LogUtils.getLogger();

//    private static final Registrate REGISTRATE = Registrate.create(MODID);

    public ShHsCreateLogistics(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);



        ComponentRegister.register(modEventBus);
        CreativeTabRegister.register(modEventBus);
        ItemRegister.register(modEventBus);
        MenuTypeRegister.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
