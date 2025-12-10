package com.shrhang.create_logistics_by_shh.items.portable_stock_ticker;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;

public class PortatbleStockTickerItem extends Item {
    public PortatbleStockTickerItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (level.isClientSide() || player == null)
            return InteractionResult.PASS;

        if (player.isShiftKeyDown()) {
            BlockPos pos = context.getClickedPos();
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof StockTickerBlockEntity) {
                setData(stack, level, pos);
                player.sendSystemMessage(Component.literal("Portable Stock Ticker linked to position: " + pos.toShortString() + " in " + level.dimension().location()));
                return InteractionResult.SUCCESS;
            }
        } else {
            BlockPos pos = stack.get(LvPosComponent.LV_POS_COMPONENT_TYPE).pos();
            ResourceKey<Level> dimension = stack.get(LvPosComponent.LV_POS_COMPONENT_TYPE).dimension();
            Level targetLevel = level.getServer().getLevel(dimension);
            if (targetLevel != null) {
                BlockEntity be = targetLevel.getBlockEntity(pos);
                if (be instanceof StockTickerBlockEntity) {
                    player.sendSystemMessage(Component.literal("Opening Stock Ticker at linked position: " + pos.toShortString() + " in " + dimension.location()));
                    return InteractionResult.SUCCESS;
                } else {
                    player.sendSystemMessage(Component.literal("No Stock Ticker found at linked position: " + pos.toShortString() + " in " + dimension.location()));
                }
            }
        }
        return InteractionResult.PASS;
    }

    public static void setData(ItemStack stack, Level level, BlockPos pos) {
        LvPosRecord newRecord = new LvPosRecord(level.dimension(), pos);
        stack.set(LvPosComponent.LV_POS_COMPONENT_TYPE, newRecord);
    }
}
