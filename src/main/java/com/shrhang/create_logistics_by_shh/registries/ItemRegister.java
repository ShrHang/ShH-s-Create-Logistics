package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import com.shrhang.create_logistics_by_shh.items.portable_stock_ticker.LvPosRecord;
import com.shrhang.create_logistics_by_shh.items.portable_stock_ticker.PortatbleStockTickerItem;
import com.tterrag.registrate.util.entry.ItemEntry;

public class ItemRegister {

    public static final ItemEntry<PortatbleStockTickerItem> PORTABLE_STOCK_TICKER =
            ShHsCreateLogistics.REGISTRATE.item("portable_stock_ticker", PortatbleStockTickerItem::new)
                    .properties(p -> p
                            .component(ComponentRegister.LV_POS, LvPosRecord.EMPTY)
                            .stacksTo(1))
                    .register();

    public static void register() {
    }
}
