package com.shrhang.create_logistics_by_shh.items.portable_stock_ticker;

import com.simibubi.create.content.logistics.stockTicker.StockKeeperRequestMenu;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

public class RemoteStockKeeperRequestMenu extends StockKeeperRequestMenu {
    public RemoteStockKeeperRequestMenu(MenuType<?> type, int id, Inventory playerInventory, StockTickerBlockEntity be) {
        super(type, id, playerInventory, be);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

}
