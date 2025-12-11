package com.shrhang.create_logistics_by_shh.items.portable_stock_ticker;

import com.shrhang.create_logistics_by_shh.registries.MenuTypeRegister;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class RemoteStockKeeperRequestMenuProvider implements MenuProvider {

    private final StockTickerBlockEntity stockTickerBE;

    public RemoteStockKeeperRequestMenuProvider(StockTickerBlockEntity stockTickerBE) {
        this.stockTickerBE = stockTickerBE;
    }

    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RemoteStockKeeperRequestMenu(MenuTypeRegister.REMOTE_STOCK_KEEPER_REQUEST.get(), pContainerId, pPlayerInventory, stockTickerBE);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

}
