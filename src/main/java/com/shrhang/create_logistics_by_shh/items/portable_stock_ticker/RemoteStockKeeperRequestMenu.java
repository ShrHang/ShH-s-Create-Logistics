package com.shrhang.create_logistics_by_shh.items.portable_stock_ticker;

import com.simibubi.create.content.logistics.stockTicker.StockKeeperRequestMenu;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RemoteStockKeeperRequestMenu extends StockKeeperRequestMenu {

    public RemoteStockKeeperRequestMenu(MenuType<?> type, int id, Inventory playerInventory, StockTickerBlockEntity be) {
        super(type, id, playerInventory, be);
    }

    public RemoteStockKeeperRequestMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        this(type, id, inv, getBlockEntity(inv, extraData));
    }

    private static StockTickerBlockEntity getBlockEntity(Inventory inv, FriendlyByteBuf extraData) {
        BlockEntity blockEntity = inv.player.level().getBlockEntity(extraData.readBlockPos());
        if (blockEntity instanceof StockTickerBlockEntity) {
            return (StockTickerBlockEntity) blockEntity;
        }
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
