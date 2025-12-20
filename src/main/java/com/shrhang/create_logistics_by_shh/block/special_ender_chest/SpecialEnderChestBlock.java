package com.shrhang.create_logistics_by_shh.block.special_ender_chest;

import com.mojang.serialization.MapCodec;
import com.shrhang.create_logistics_by_shh.registries.BlockEntityTypeRegister;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpecialEnderChestBlock extends HorizontalDirectionalBlock implements IWrenchable, IBE<SpecialEnderChestBlockEntity> {
    private static final VoxelShape SHAPE_HALF = Block.box(1, 0, 1, 14, 14, 14); // 半高碰撞箱
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public SpecialEnderChestBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(SpecialEnderChestBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        // Ensure block entity is correct type
        if (!(level.getBlockEntity(pos) instanceof SpecialEnderChestBlockEntity specialEnderChestBE))
            return InteractionResult.sidedSuccess(level.isClientSide);
        // Check if there's a solid block above
        BlockPos blockpos = pos.above();
        if (level.getBlockState(blockpos).isRedstoneConductor(level, blockpos)) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        // Get the target player
        Player targetPlayer = level.getPlayerByUUID((specialEnderChestBE).getTargetUUID());
        if (targetPlayer == null) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (player.isCrouching()) {
            if (Objects.equals(targetPlayer, player)) {
                specialEnderChestBE.changeLock();
                return InteractionResult.CONSUME;
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            if (specialEnderChestBE.isLocked() && !Objects.equals(targetPlayer, player))
                return InteractionResult.sidedSuccess(level.isClientSide);

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
            withBlockEntityDo(level, pos, be -> be.setTargetUUID(player.getUUID()));
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return IBE.super.getTicker(level, state, type);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        // 渲染/选中用的形状（显示框）
        return SHAPE_HALF;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        // 实体碰撞用的形状，返回 EMPTY 则实体可穿过
        return SHAPE_HALF;
    }

    @Override
    public @NotNull VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        // 射线检测/交互用的形状（右键检测）
        return SHAPE_HALF;
    }
}
