package com.baimo.morematerials;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BaiMoMoreMaterials.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEMS_TAB =
            TABS.register("items", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.bm_more_materials.items"))
                    .icon(() -> new ItemStack(ModItems.ALL_ITEMS.get("primordial_dawn_star").get()))
                    .displayItems((params, output) -> {
                        ModItems.ALL_ITEMS.forEach((name, item) -> {
                            // 过滤条件：
                            // 1. 排除名字带有 "_demo_" 的演示物品
                            boolean isDemo = name.contains("_demo_");

                            // 2. 排除奥瑞终端未完成体（包含 oritech_data_terminal 且 不是最终完成体）
                            boolean isIncompleteTerminal = name.startsWith("oritech_data_terminal") && !name.equals("oritech_data_terminal");

                            if (!isDemo && !isIncompleteTerminal) {
                                output.accept(item.get());
                            }
                        });
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DEMO_TAB =
            TABS.register("demo", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.bm_more_materials.demo"))
                    .icon(() -> new ItemStack(ModItems.ALL_ITEMS.get("low_demo_ingot").get()))
                    .displayItems((params, output) -> {
                        ModItems.ALL_ITEMS.forEach((name, item) -> {
                            if (name.contains("_demo_")) {
                                output.accept(item.get());
                            }
                        });
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS_TAB =
            TABS.register("blocks", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.bm_more_materials.blocks"))
                    .icon(() -> new ItemStack(ModBlocks.MATERIAL_BLOCKS.get("star_shimmer_block").get()))
                    .displayItems((params, output) -> {
                        ModBlocks.MATERIAL_BLOCKS.values().forEach(block -> {
                            ItemStack stack = new ItemStack(block.get().asItem());
                            if (!stack.isEmpty()) {
                                output.accept(stack);
                            }
                        });
                    })
                    .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}