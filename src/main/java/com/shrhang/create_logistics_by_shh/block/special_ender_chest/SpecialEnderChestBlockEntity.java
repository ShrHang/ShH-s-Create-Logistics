package com.shrhang.create_logistics_by_shh.block.special_ender_chest;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpecialEnderChestBlockEntity extends SmartBlockEntity{
    UUID targetUUID;

    public SpecialEnderChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        targetUUID = null;
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            // TODO Animation
        }
        return super.triggerEvent(id, type);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        if (targetUUID != null) {
            tag.putUUID("TargetPlayer", targetUUID);
        }
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.hasUUID("TargetPlayer")) {
            targetUUID = tag.getUUID("TargetPlayer");
        }
    }

    public void setTargetUUID(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }

    @Nullable
    public IItemHandler getItemHandler() {
        if (level == null || level.isClientSide || targetUUID == null) {
            return null;
        }
        Player player = Objects.requireNonNull(level.getServer()).getPlayerList().getPlayer(targetUUID);
        if (player != null) {
            return new InvWrapper(player.getEnderChestInventory());
        }
        return null;
    }
}
