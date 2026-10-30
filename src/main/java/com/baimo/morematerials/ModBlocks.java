package com.baimo.morematerials;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BaiMoMoreMaterials.MODID);

    public static final Map<String, DeferredBlock<Block>> MATERIAL_BLOCKS = new LinkedHashMap<>();

    private static final List<String> BLOCK_NAMES = List.of(
            "star_shimmer_block",
            "moon_glow_block",
            "sun_corona_block",
            "low_demo_block",
            "mid_demo_block",
            "high_demo_block",
            "ultimate_demo_block",
            "infinite_gray_block",
            "endless_chroma_block"
    );

    static {
        for (String name : BLOCK_NAMES) {
            DeferredBlock<Block> block = BLOCKS.register(name,
                    () -> new Block(BlockBehaviour.Properties.of()
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()));

            MATERIAL_BLOCKS.put(name, block);
            ModItems.ITEMS.registerSimpleBlockItem(block);
        }
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}