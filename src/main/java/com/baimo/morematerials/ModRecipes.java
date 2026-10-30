package com.baimo.morematerials;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipes extends RecipeProvider implements IConditionBuilder {
    public ModRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // ================== 1. 基础材料转换配方 ==================
        String[] materials = {
                "star_shimmer", "moon_glow", "sun_corona",
                "low_demo", "mid_demo", "high_demo", "ultimate_demo",
                "infinite_gray", "endless_chroma"
        };

        for (String material : materials) {
            ItemLike ingot = ModItems.ALL_ITEMS.get(material + "_ingot").get();
            ItemLike nugget = ModItems.ALL_ITEMS.get(material + "_nugget").get();
            ItemLike block = ModBlocks.MATERIAL_BLOCKS.get(material + "_block").get();

            // 9粒 -> 1锭
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot)
                    .requires(nugget, 9).unlockedBy("has_nugget", has(nugget))
                    .save(recipeOutput, material + "_ingot_from_nuggets");

            // 1锭 -> 9粒
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                    .requires(ingot).unlockedBy("has_ingot", has(ingot))
                    .save(recipeOutput, material + "_nugget_from_ingot");

            // 9锭 -> 1块
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                    .pattern("###").pattern("###").pattern("###").define('#', ingot)
                    .unlockedBy("has_ingot", has(ingot))
                    .save(recipeOutput, material + "_block");

            // 1块 -> 9锭
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9)
                    .requires(block).unlockedBy("has_block", has(block))
                    .save(recipeOutput, material + "_ingot_from_block");
        }

        // ================== 2. 奥瑞数据终端 升级配方 ==================
        // 定义低、中、高、终的演示锭和粒
        ItemLike ingot1 = ModItems.ALL_ITEMS.get("low_demo_ingot").get();
        ItemLike ingot2 = ModItems.ALL_ITEMS.get("mid_demo_ingot").get();
        ItemLike ingot3 = ModItems.ALL_ITEMS.get("high_demo_ingot").get();
        ItemLike ingot4 = ModItems.ALL_ITEMS.get("ultimate_demo_ingot").get();

        ItemLike nugget1 = ModItems.ALL_ITEMS.get("low_demo_nugget").get();
        ItemLike nugget2 = ModItems.ALL_ITEMS.get("mid_demo_nugget").get();
        ItemLike nugget3 = ModItems.ALL_ITEMS.get("high_demo_nugget").get();
        ItemLike nugget4 = ModItems.ALL_ITEMS.get("ultimate_demo_nugget").get();

        // 定义奥瑞终端的 5 个状态
        ItemLike oritech0 = ModItems.ALL_ITEMS.get("oritech_data_terminal1").get();
        ItemLike oritech1 = ModItems.ALL_ITEMS.get("oritech_data_terminal2").get();
        ItemLike oritech2 = ModItems.ALL_ITEMS.get("oritech_data_terminal3").get();
        ItemLike oritech3 = ModItems.ALL_ITEMS.get("oritech_data_terminal4").get();
        ItemLike oritech4 = ModItems.ALL_ITEMS.get("oritech_data_terminal").get();

        // 升级 1：未激活 -> 25%
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, oritech1)
                .pattern("NIN")
                .pattern("ICI")
                .pattern("NIN")
                .define('N', nugget1)
                .define('I', ingot1)
                .define('C', oritech0)
                .unlockedBy("has_oritech0", has(oritech0))
                .save(recipeOutput, "oritech_data_terminal2");

        // 升级 2：25% -> 50%
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, oritech2)
                .pattern("NIN")
                .pattern("ICI")
                .pattern("NIN")
                .define('N', nugget2)
                .define('I', ingot2)
                .define('C', oritech1)
                .unlockedBy("has_oritech1", has(oritech1))
                .save(recipeOutput, "oritech_data_terminal3");

        // 升级 3：50% -> 75%
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, oritech3)
                .pattern("NIN")
                .pattern("ICI")
                .pattern("NIN")
                .define('N', nugget3)
                .define('I', ingot3)
                .define('C', oritech2)
                .unlockedBy("has_oritech2", has(oritech2))
                .save(recipeOutput, "oritech_data_terminal4");

        // 升级 4：75% -> 100% (完全体)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, oritech4)
                .pattern("NIN")
                .pattern("ICI")
                .pattern("NIN")
                .define('N', nugget4)
                .define('I', ingot4)
                .define('C', oritech3)
                .unlockedBy("has_oritech3", has(oritech3))
                .save(recipeOutput, "oritech_data_terminal_final");
    }
}