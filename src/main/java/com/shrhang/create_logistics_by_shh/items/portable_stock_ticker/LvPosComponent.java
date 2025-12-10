package com.shrhang.create_logistics_by_shh.items.portable_stock_ticker;

import com.shrhang.create_logistics_by_shh.ShHsCreateLogistics;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.swing.*;
import java.util.function.UnaryOperator;

public class LvPosComponent {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ShHsCreateLogistics.MODID);

    public static final DataComponentType<LvPosRecord> LV_POS_COMPONENT_TYPE = register(
            "LvPos",
            builder -> builder.persistent(LvPosRecord.CODEC).networkSynchronized(LvPosRecord.STREAM_CODEC)
    );

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.register(name, () -> type);
        return type;
    }

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
