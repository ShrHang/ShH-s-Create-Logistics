package com.shrhang.create_logistics_by_shh.block.special_ender_chest;

import com.simibubi.create.api.packager.InventoryIdentifier;
import net.createmod.catnip.math.BlockFace;

import java.util.UUID;

public record SpecialEnderChestBlockInvId(UUID targetUUID) implements InventoryIdentifier {
    @Override
    public boolean contains(BlockFace face) {
        return false;
    }

}
