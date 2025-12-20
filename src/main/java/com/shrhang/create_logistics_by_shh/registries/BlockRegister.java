package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import com.shrhang.create_logistics_by_shh.block.special_ender_chest.SpecialEnderChestBlock;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.material.MapColor;

public class BlockRegister {
    private static final CreateRegistrate REGISTRATE = ShHsCreateLogistics.REGISTRATE;
    public static final BlockEntry<SpecialEnderChestBlock> BRASS_ENDER_CHEST = REGISTRATE
            .block("brass_ender_chest", SpecialEnderChestBlock::new)
            .properties(p -> p
                    .mapColor(MapColor.STONE)
                    .strength(1.5f, 600.0f)
                    .lightLevel(state -> 7)
                    .requiresCorrectToolForDrops()
            )
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE)
            .blockstate((ctx, prov) ->
                    prov.horizontalBlock(ctx.getEntry(), prov.models().getExistingFile(ctx.getId()))
            )
            .simpleItem()
            .register();

    public static void register() {
    }
}
