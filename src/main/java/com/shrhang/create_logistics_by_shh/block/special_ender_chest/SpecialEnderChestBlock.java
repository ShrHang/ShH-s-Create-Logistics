package com.shrhang.create_logistics_by_shh.block.special_ender_chest;

import com.shrhang.create_logistics_by_shh.registries.BlockEntityTypeRegister;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpecialEnderChestBlock extends Block implements IWrenchable, IBE<SpecialEnderChestBlockEntity> {

    public SpecialEnderChestBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof SpecialEnderChestBlockEntity specialEnderChestBE) {

            BlockPos blockpos = pos.above();

            if (level.getBlockState(blockpos).isRedstoneConductor(level, blockpos)) {
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            Player targetPlayer = level.getPlayerByUUID((specialEnderChestBE).getTargetUUID());

            if (targetPlayer == null || !targetPlayer.getUUID().equals(player.getUUID())) {
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            player.openMenu(
                    new SimpleMenuProvider(
                            (id, inventory, pl) -> ChestMenu.threeRows(id, inventory, targetPlayer.getEnderChestInventory()),
                            Component.translatable("title.create_logistics_by_shh.container.endchest", targetPlayer.getName(), Component.translatable("container.enderchest"))
                    )
            );

            level.playSound(
                    null,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    SoundEvents.ENDER_CHEST_OPEN,
                    SoundSource.BLOCKS,
                    0.5F,
                    level.random.nextFloat() * 0.1F + 0.9F
            );

            player.awardStat(Stats.OPEN_ENDERCHEST);
            PiglinAi.angerNearbyPiglins(player, true);

            return InteractionResult.CONSUME;
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        super.playerWillDestroy(level, pos, state, player);
        if (!level.isClientSide()) {
            if (!player.isCreative()) {
                popResource(level, pos, new ItemStack(this));
            }
        }
        return state;
    }

    @Override
    public Class<SpecialEnderChestBlockEntity> getBlockEntityClass() {
        return SpecialEnderChestBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SpecialEnderChestBlockEntity> getBlockEntityType() {
        return BlockEntityTypeRegister.SPECIAL_ENDER_CHEST_BE.get();
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return Objects.requireNonNull(IBE.super.newBlockEntity(pos, state));
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide && placer instanceof Player player) {
            withBlockEntityDo(level, pos, be -> {
                be.setTargetUUID(player.getUUID());
                be.setChanged();
            });
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return IBE.super.getTicker(level, state, type);
    }
}
