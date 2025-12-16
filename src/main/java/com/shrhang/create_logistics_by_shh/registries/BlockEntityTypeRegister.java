package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import com.shrhang.create_logistics_by_shh.block.special_ender_chest.SpecialEnderChestBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class BlockEntityTypeRegister {
    private static final CreateRegistrate REGISTRATE = ShHsCreateLogistics.REGISTRATE;
    public static final BlockEntityEntry<SpecialEnderChestBlockEntity> SPECIAL_ENDER_CHEST_BE = REGISTRATE
            .blockEntity("special_enderchest", SpecialEnderChestBlockEntity::new)
            .validBlocks(BlockRegister.TEST_BLOCK)
            .register();
    public static void register() {
    }
}
