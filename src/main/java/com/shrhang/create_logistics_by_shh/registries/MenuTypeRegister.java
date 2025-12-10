package com.shrhang.create_logistics_by_shh.registries;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuTypeRegister {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ShHsCreateLogistics.MODID);
}
