package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import com.shrhang.create_logistics_by_shh.block.special_ender_chest.SpecialEnderChestBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;

public class BlockRegister {
    private static final CreateRegistrate REGISTRATE = ShHsCreateLogistics.REGISTRATE;
    public static final BlockEntry<SpecialEnderChestBlock> BRASS_ENDER_CHEST = REGISTRATE
            .block("brass_ender_chest", SpecialEnderChestBlock::new)
            .blockstate((ctx, prov) ->
                    prov.horizontalBlock(ctx.getEntry(), prov.models().getExistingFile(ctx.getId()))
            )
            .simpleItem()
            .register();

    public static void register() {
    }
}
