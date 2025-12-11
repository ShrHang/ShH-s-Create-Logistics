package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import com.shrhang.create_logistics_by_shh.items.portable_stock_ticker.RemoteStockKeeperRequestMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuTypeRegister {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ShHsCreateLogistics.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<RemoteStockKeeperRequestMenu>> REMOTE_STOCK_KEEPER_REQUEST =
            MENUS.register(
                    "remote_stock_keeper_request",
                    () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                        return new RemoteStockKeeperRequestMenu(MenuTypeRegister.REMOTE_STOCK_KEEPER_REQUEST.get(), windowId, inv, data);
                    })
            );

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
