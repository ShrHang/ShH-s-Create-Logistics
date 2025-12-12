package com.shrhang.create_logistics_by_shh.items.portable_stock_ticker;

import com.shrhang.create_logistics_by_shh.registries.ComponentRegister;
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

//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        Level level = context.getLevel();
//        Player player = context.getPlayer();
//        ItemStack stack = context.getItemInHand();
//
//        if (level.isClientSide() || player == null)
//            return InteractionResult.PASS;
//
//        if (player.isShiftKeyDown())
//            return linkTo(stack, level, context.getClickedPos()) ? InteractionResult.SUCCESS : InteractionResult.PASS;
//
//        return tryToOpenMenu(level, player, stack);
//    }

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
                player.sendSystemMessage(Component.literal("已连接"));
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
            player.sendSystemMessage(Component.literal("物品未链接"));
            return false;
        }

        ResourceKey<Level> targetDimension = record.dimension();
        BlockPos pos = record.pos();

//        Level targetLevel = player.level();
        if (level.dimension() != targetDimension) {
            player.sendSystemMessage(Component.literal("必须在链接所在的维度才能打开"));
            return false;
        }

        BlockEntity be = level.getBlockEntity(pos);

        if (level.getBlockEntity(pos) instanceof StockTickerBlockEntity) {
            player.openMenu(new RemoteStockKeeperRequestMenuProvider((StockTickerBlockEntity) be), buf ->
                    buf.writeBoolean(true).writeBoolean(false).writeBlockPos(pos));
            return true;
        }
        player.sendSystemMessage(Component.literal("在链接位置未找到方块"));

        return false;
    }
}
