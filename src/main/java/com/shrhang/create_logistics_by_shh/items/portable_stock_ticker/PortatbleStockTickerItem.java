package com.shrhang.create_logistics_by_shh.items.portable_stock_ticker;

import com.shrhang.create_logistics_by_shh.registries.ComponentRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import net.minecraft.world.phys.BlockHitResult;

public class PortatbleStockTickerItem extends Item {
    public PortatbleStockTickerItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide())
            return InteractionResultHolder.pass(stack);

        if (player.isShiftKeyDown()) {
            BlockHitResult blockHitResult = (BlockHitResult) player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE), 0.0f, false);
            BlockPos blockPos = blockHitResult.getBlockPos();
            InteractionResult result = InteractionResult.PASS;
            if (linkTo(stack, level, blockPos)) {
                player.sendSystemMessage(Component.translatable("text.create_logistics_by_shh.portable_stock_ticker.link_success"));
                result = InteractionResult.SUCCESS;
            }
            return new InteractionResultHolder<>(result, stack);
        }

        InteractionResult result = tryToOpenMenu(level, player, stack) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        return new InteractionResultHolder<>(result, stack);

    }

    public static boolean linkTo(ItemStack stack, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof StockTickerBlockEntity) {
            LvPosRecord newRecord = new LvPosRecord(level.dimension(), pos);
            stack.set(ComponentRegister.LV_POS, newRecord);
            return true;
        }
        return false;
    }

    public static boolean tryToOpenMenu(Level level, Player player, ItemStack stack) {
        LvPosRecord record = stack.get(ComponentRegister.LV_POS);
        if (record == LvPosRecord.EMPTY || record == null) {
            player.sendSystemMessage(Component.translatable("text.create_logistics_by_shh.portable_stock_ticker.no_data"));
            return false;
        }

        ResourceKey<Level> targetDimension = record.dimension();
        BlockPos pos = record.pos();

        if (level.dimension() != targetDimension) {
            player.sendSystemMessage(Component.translatable("text.create_logistics_by_shh.portable_stock_ticker.different_dimension"));
            return false;
        }

        BlockEntity be = Minecraft.getInstance().level.getBlockEntity(pos);
        if (be instanceof StockTickerBlockEntity) {
            player.openMenu(new RemoteStockKeeperRequestMenuProvider((StockTickerBlockEntity) be), buf ->
                    buf.writeBoolean(true).writeBoolean(false).writeBlockPos(pos));
            return true;
        }

        player.sendSystemMessage(Component.translatable("text.create_logistics_by_shh.portable_stock_ticker.no_block"));
        return false;
    }
}
