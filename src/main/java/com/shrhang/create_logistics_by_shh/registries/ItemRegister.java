package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import com.shrhang.create_logistics_by_shh.items.portable_stock_ticker.LvPosRecord;
import com.shrhang.create_logistics_by_shh.items.portable_stock_ticker.PortatbleStockTickerItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, ShHsCreateLogistics.MODID);

    public static final DeferredHolder<Item, PortatbleStockTickerItem> PORTABLE_STOCK_TICKER =
            ITEMS.register("portable_stock_ticker",
                    () -> new PortatbleStockTickerItem(
                            new Item.Properties()
                                    .component(ComponentRegister.LV_POS, LvPosRecord.EMPTY)
                                    .stacksTo(1)
                    )
            );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
