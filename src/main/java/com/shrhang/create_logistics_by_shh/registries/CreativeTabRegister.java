package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ShHsCreateLogistics.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_LOGISTICS_BY_SHH_TAB =
            CREATIVE_MODE_TABS.register("create_logistics_by_shh_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup." + ShHsCreateLogistics.MODID + ".baseTab"))
                            .icon(() -> ItemRegister.PORTABLE_STOCK_TICKER.get().getDefaultInstance())
                            .displayItems((parameters, output) -> {
                                output.accept(ItemRegister.PORTABLE_STOCK_TICKER.get());
                            })
                            .build()
            );

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
