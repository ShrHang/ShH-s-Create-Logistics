package com.shrhang.create_logistics_by_shh.block.special_ender_chest;

import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.api.packager.InventoryIdentifier;
import com.simibubi.create.content.equipment.clipboard.ClipboardCloneable;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.math.BlockFace;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpecialEnderChestBlockEntity extends SmartBlockEntity implements IHaveHoveringInformation, ClipboardCloneable {
    private UUID targetUUID;
    private InventoryIdentifier invId;
    private boolean isLocked;

    public String targetName = "???";
    protected IItemHandler inventory;

    private static final Logger LOGGER = LogManager.getLogger();

    public SpecialEnderChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        targetUUID = null;
        isLocked = true;
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            // TODO Animation
        }
        return super.triggerEvent(id, type);
    }

    @Override
    public boolean addToTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.create_logistics_by_shh.special_ender_chest.header").forGoggles(tooltip);
        Component nameComponent = Component.literal(targetName).withStyle(ChatFormatting.GOLD);
        if (targetUUID != null) {
            if (level != null && level.getPlayerByUUID(targetUUID) != null) {
                CreateLang.translate(
                                "tooltip.create_logistics_by_shh.special_ender_chest.owner",
                                nameComponent
                        )
                        .style(ChatFormatting.GRAY)
                        .forGoggles(tooltip);
            } else {
                CreateLang.translate(
                                "tooltip.create_logistics_by_shh.special_ender_chest.owner_unknown",
                                nameComponent
                        )
                        .style(ChatFormatting.DARK_RED)
                        .forGoggles(tooltip);
            }
            if (isLocked)
                CreateLang.translate("tooltip.create_logistics_by_shh.special_ender_chest.locked")
                        .style(ChatFormatting.RED)
                        .forGoggles(tooltip);
            else
                CreateLang.translate("tooltip.create_logistics_by_shh.special_ender_chest.unlocked")
                        .style(ChatFormatting.GREEN)
                        .forGoggles(tooltip);
        } else
            tooltip.add(Component.literal("Error: No Target. | 这不应该啊").withStyle(ChatFormatting.DARK_RED));
        return true;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    private void init() {
        invId = new SpecialEnderChestBlockInvId(targetUUID);
        if (targetUUID != null && level != null && level.getPlayerByUUID(targetUUID) != null)
            targetName = level.getPlayerByUUID(targetUUID).getName().getString();
    }

    public InventoryIdentifier getInvId() {
        init();
        return this.invId;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        if (targetUUID != null)
            tag.putUUID("TargetPlayer", targetUUID);
        tag.putBoolean("IsLocked", isLocked);
        tag.putString("DisplayName", targetName);
    } // Output

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.hasUUID("TargetPlayer"))
            setTargetUUID(tag.getUUID("TargetPlayer"));
        if (tag.contains("IsLocked"))
            setLock(tag.getBoolean("IsLocked"));
        if (tag.contains("DisplayName"))
            targetName = tag.getString("DisplayName");
    } // Input

    public void setTargetUUID(UUID newUUID) {
        if (!Objects.equals(this.targetUUID, newUUID))
            this.inventory = null;
        this.targetUUID = newUUID;
        init();
        notifyUpdate();
    }

    public UUID getTargetUUID() {
        return this.targetUUID;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void changeLock() {
        setLock(!isLocked);
    }

    public void setLock(boolean lock) {
        isLocked = lock;
        notifyUpdate();
    }

    @Override
    public String getClipboardKey() {
        return "Block";
    }

    @Override
    public boolean readFromClipboard(@NotNull HolderLookup.Provider registries, CompoundTag tag, Player player, Direction side, boolean simulate) {
        if (!tag.hasUUID("TargetPlayer") || !tag.contains("IsLocked") || !tag.contains("DisplayName"))
            return false;
        if (targetUUID == null)
            return false;
        if (!simulate) {
            targetName = tag.getString("DisplayName");
            setLock(tag.getBoolean("IsLocked"));
            if (!getTargetUUID().equals(tag.getUUID("TargetPlayer")))
                setTargetUUID(tag.getUUID("TargetPlayer"));
        }
        return true;
    }

    @Override
    public boolean writeToClipboard(@NotNull HolderLookup.Provider registries, CompoundTag tag, Direction side) {
        if (targetUUID != null) {
            tag.putUUID("TargetPlayer", targetUUID);
            tag.putBoolean("IsLocked", isLocked);
        }
        return true;
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
