package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import com.shrhang.create_logistics_by_shh.block.special_ender_chest.SpecialEnderChestBlock;
import com.shrhang.create_logistics_by_shh.block.special_ender_chest.SpecialEnderChestItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;

public class BlockRegister {
    private static final CreateRegistrate REGISTRATE = ShHsCreateLogistics.REGISTRATE;
    public static final BlockEntry<SpecialEnderChestBlock> TEST_BLOCK = REGISTRATE
            .block("test_block", SpecialEnderChestBlock::new)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().getExistingFile(prov.modLoc("block/test_block"))))
            .item(SpecialEnderChestItem::new).build()
            .register();

    public static void register() {
    }
}
