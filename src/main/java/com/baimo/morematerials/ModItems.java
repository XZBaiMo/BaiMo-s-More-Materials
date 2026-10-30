package com.baimo.morematerials;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BaiMoMoreMaterials.MODID);

    public static final Map<String, DeferredItem<Item>> ALL_ITEMS = new LinkedHashMap<>();

    // 普通物品列表（已移除奥瑞终端，避免重复注册）
    private static final List<String> ITEM_NAMES = List.of(
            // --- 1. 黎明之星连在一起 ---
            "dawn_star",
            "unbreakable_dawn_star",
            "primordial_dawn_star",

            // --- 2. 黎明之星碎片连在一起 ---
            "dawn_star_shard",
            "unbreakable_dawn_star_shard",
            "primordial_dawn_star_shard",

            // --- 3. 星月日的锭连在一起 ---
            "star_shimmer_ingot",
            "moon_glow_ingot",
            "sun_corona_ingot",

            // --- 4. 星月日的粒连在一起 ---
            "star_shimmer_nugget",
            "moon_glow_nugget",
            "sun_corona_nugget",

            // --- 5. 其他材料 ---
            "allthemodium_alloy_ingot",
            "amber_crystal", "obsidian_crystal",
            "certus_ingot", "fluix_ingot", "time_crystal_ingot",
            "paradox_time_crystal", "paradox_time_crystal_ingot",
            "dragon_gobber_ingot",
            "evil_chaos_shard",

            "infinite_gray_ingot", "infinite_gray_nugget",
            "endless_chroma_ingot", "endless_chroma_nugget"
    );

    static {
        // 1. 批量注册普通物品
        for (String name : ITEM_NAMES) {
            ALL_ITEMS.put(name, ITEMS.registerSimpleItem(name));
        }

        // 2. 注册奥瑞数据终端（完成体，在创造栏显示）
        ALL_ITEMS.put("oritech_data_terminal", ITEMS.register("oritech_data_terminal",
                () -> new Item(new Item.Properties())));

        // 3. 注册奥瑞数据终端的 4 个未完成体（带有副标题）
        ALL_ITEMS.put("oritech_data_terminal1", registerOritechTerminal("oritech_data_terminal1", "未激活"));
        ALL_ITEMS.put("oritech_data_terminal2", registerOritechTerminal("oritech_data_terminal2", "未完成（进度25%）"));
        ALL_ITEMS.put("oritech_data_terminal3", registerOritechTerminal("oritech_data_terminal3", "未完成（进度50%）"));
        ALL_ITEMS.put("oritech_data_terminal4", registerOritechTerminal("oritech_data_terminal4", "未完成（进度75%）"));

        // 4. 注册演示材料（低、中、高、终极）
        ALL_ITEMS.put("low_demo_ingot", registerDemoItem("low_demo_ingot", "低级材料，用此材料合成的物品仅演示作用"));
        ALL_ITEMS.put("mid_demo_ingot", registerDemoItem("mid_demo_ingot", "中级材料，用此材料合成的物品仅演示作用"));
        ALL_ITEMS.put("high_demo_ingot", registerDemoItem("high_demo_ingot", "高级材料，用此材料合成的物品仅演示作用"));
        ALL_ITEMS.put("ultimate_demo_ingot", registerDemoItem("ultimate_demo_ingot", "终极材料，用此材料合成的物品仅演示作用"));

        ALL_ITEMS.put("low_demo_nugget", registerDemoItem("low_demo_nugget", "低级材料，用此材料合成的物品仅演示作用"));
        ALL_ITEMS.put("mid_demo_nugget", registerDemoItem("mid_demo_nugget", "中级材料，用此材料合成的物品仅演示作用"));
        ALL_ITEMS.put("high_demo_nugget", registerDemoItem("high_demo_nugget", "高级材料，用此材料合成的物品仅演示作用"));
        ALL_ITEMS.put("ultimate_demo_nugget", registerDemoItem("ultimate_demo_nugget", "终极材料，用此材料合成的物品仅演示作用"));

        // 5. 注册“无人世界的夜码”
        ALL_ITEMS.put("empty_sekai_nightcode", ITEMS.register("empty_sekai_nightcode", () -> new Item(new Item.Properties()) {
            @Override
            public Component getName(ItemStack stack) {
                return gradientText("无人世界的夜码", 0xBB6588, 0x8889CC, 0xCCAA87, 0xDDAACB);
            }

            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                tooltipComponents.add(gradientText(
                        "来自另一个次元的物品，似乎蕴含了很多负能量，但是终会被希望净化",
                        0xBB6588, 0x8889CC, 0xCCAA87, 0xDDAACB
                ));
                tooltipComponents.add(Component.literal("——如果伤痛无法消除，那就幻化成蝴蝶结装饰起来吧")
                        .withStyle(style -> style.withColor(0xDDAACB)));
            }
        }));
    }

    // ================== 辅助注册方法 ==================

    // 奥瑞终端未完成体专属注册方法
    private static DeferredItem<Item> registerOritechTerminal(String name, String subtitleText) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()) {
            @Override
            public Component getName(ItemStack stack) {
                return Component.literal("奥瑞数据终端");
            }

            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                tooltipComponents.add(Component.literal(subtitleText).withStyle(ChatFormatting.GRAY));
            }
        });
    }

    // 演示物品注册辅助方法
    private static DeferredItem<Item> registerDemoItem(String name, String tooltipText) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()) {
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                tooltipComponents.add(Component.literal(tooltipText).withStyle(ChatFormatting.GRAY));
            }
        });
    }

    // ================== 以下为炫彩渐变工具方法 ==================

    private static int interpolateColor(int color1, int color2, float ratio) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        int r = (int) (r1 + (r2 - r1) * ratio);
        int g = (int) (g1 + (g2 - g1) * ratio);
        int b = (int) (b1 + (b2 - b1) * ratio);
        return (r << 16) | (g << 8) | b;
    }

    public static MutableComponent gradientText(String text, int... colors) {
        MutableComponent result = Component.empty();
        int length = text.length();
        if (length == 0) return result;
        if (colors.length == 1) return Component.literal(text).withStyle(style -> style.withColor(colors[0]));

        for (int i = 0; i < length; i++) {
            float globalRatio = (float) i / (length - 1);
            float colorSection = globalRatio * (colors.length - 1);
            int colorIndex = (int) colorSection;
            float localRatio = colorSection - colorIndex;
            int color;
            if (colorIndex >= colors.length - 1) {
                color = colors[colors.length - 1];
            } else {
                color = interpolateColor(colors[colorIndex], colors[colorIndex + 1], localRatio);
            }
            final int finalColor = color;
            result.append(Component.literal(String.valueOf(text.charAt(i)))
                    .withStyle(style -> style.withColor(finalColor)));
        }
        return result;
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}