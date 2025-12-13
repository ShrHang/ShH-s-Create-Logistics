package com.shrhang.create_logistics_by_shh;

import com.shrhang.create_logistics_by_shh.registries.ComponentRegister;
import com.shrhang.create_logistics_by_shh.registries.CreativeTabRegister;
import com.shrhang.create_logistics_by_shh.registries.ItemRegister;
import com.shrhang.create_logistics_by_shh.registries.MenuTypeRegister;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(ShHsCreateLogistics.MODID)
public class ShHsCreateLogistics {
    public static final String MODID = "create_logistics_by_shh";
    public static final CreateRegistrate REGISTRATE =
            CreateRegistrate.create(MODID)
                    .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
                    .setTooltipModifierFactory(item -> new ItemDescription
                            .Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item))));

    public ShHsCreateLogistics(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(this);
        REGISTRATE.registerEventListeners(modEventBus);
        ComponentRegister.register(modEventBus);
        CreativeTabRegister.register(modEventBus);
        ItemRegister.register();
        MenuTypeRegister.register(modEventBus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
