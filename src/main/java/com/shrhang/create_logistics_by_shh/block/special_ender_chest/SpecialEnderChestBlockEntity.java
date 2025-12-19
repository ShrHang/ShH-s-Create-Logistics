package com.shrhang.create_logistics_by_shh.block.special_ender_chest;

import com.simibubi.create.api.packager.InventoryIdentifier;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createmod.catnip.math.BlockFace;
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
import java.util.UUID;

public class SpecialEnderChestBlockEntity extends SmartBlockEntity {
    private UUID targetUUID;
    private InventoryIdentifier invId;
    protected IItemHandler inventory;

//    private static final Logger LOGGER = LogManager.getLogger();

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
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    private void initInvId() {
        invId = new SpecialEnderChestBlockInvId(targetUUID);
    }

    public InventoryIdentifier getInvId() {
        initInvId();
        return this.invId;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        if (targetUUID != null)
            tag.putUUID("TargetPlayer", targetUUID);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.hasUUID("TargetPlayer"))
            targetUUID = tag.getUUID("TargetPlayer");
    }

    public void setTargetUUID(UUID newUUID) {
        if (!this.targetUUID.equals(newUUID))
            this.inventory = null;
        this.targetUUID = newUUID;
        initInvId();
    }

    public UUID getTargetUUID() {
        return this.targetUUID;
    }

    @Nullable
    public IItemHandler getInventory() {
        if (level == null || level.isClientSide || targetUUID == null)
            return null;

        Player targetPlayer = level.getPlayerByUUID(targetUUID);
        if (targetPlayer == null)
            return null;

        if (inventory == null)
            inventory = new InvWrapper(targetPlayer.getEnderChestInventory());

        return inventory;
    }

    public record SpecialEnderChestBlockInvId(UUID targetUUID) implements InventoryIdentifier {
        @Override
        public boolean contains(BlockFace face) {
            UUID uuid = face.serializeNBT().getUUID("TargetPlayer");
            return targetUUID.equals(uuid);
        }
    }
}
