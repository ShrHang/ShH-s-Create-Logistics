package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.block.special_ender_chest.SpecialEnderChestBlockEntity;
import com.simibubi.create.api.packager.InventoryIdentifier;

public class InventoryIdentifiersRegister {
    public static void registerDefaults() {
        InventoryIdentifier.REGISTRY.register(BlockRegister.BRASS_ENDER_CHEST.get(), (level, state, face) ->
                level.getBlockEntity(face.getPos()) instanceof SpecialEnderChestBlockEntity be ? be.getInvId() : null);
    }
}
