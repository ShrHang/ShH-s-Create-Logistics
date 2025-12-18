package com.shrhang.create_logistics_by_shh.block.special_ender_chest;

import com.simibubi.create.api.packager.InventoryIdentifier;
import net.createmod.catnip.math.BlockFace;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record SpecialEnderChestBlockInventoryIdentifier(SpecialEnderChestBlockEntity owner) implements InventoryIdentifier {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public boolean contains(BlockFace face) {
        Level level = owner.getLevel();
        if (level == null || level.isClientSide || owner.getTargetUUID() == null)
            return false;
        BlockEntity be = level.getBlockEntity(face.getPos());
        return be instanceof SpecialEnderChestBlockEntity other && owner.getTargetUUID().equals(other.getTargetUUID());
    }
}
