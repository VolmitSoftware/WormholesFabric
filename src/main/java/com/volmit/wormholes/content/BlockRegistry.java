package com.volmit.wormholes.content;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static com.volmit.wormholes.Wormholes.MOD_ID;

public class BlockRegistry {

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block FRAME = registerBlock("frame",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_BLACK = registerBlock("frame_black",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_BLUE = registerBlock("frame_blue",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_BROWN = registerBlock("frame_brown",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_RED = registerBlock("frame_red",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_GREEN = registerBlock("frame_green",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_YELLOW = registerBlock("frame_yellow",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_CYAN = registerBlock("frame_cyan",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_LIGHT_GRAY = registerBlock("frame_light_gray",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_GRAY = registerBlock("frame_gray",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_PINK = registerBlock("frame_pink",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_LIME = registerBlock("frame_lime",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_LIGHT_BLUE = registerBlock("frame_light_blue",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_MAGENTA = registerBlock("frame_magenta",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_ORANGE = registerBlock("frame_orange",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));
    public static final Block FRAME_WHITE = registerBlock("frame_white",
            new FrameBlock(AbstractBlock.Settings.of(Material.STONE)
                    .strength(4f, 1200f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_TILES)
                    .emissiveLighting((state, world, pos) -> true)));

    private static Block registerBlock(String id, Block block) {
        Block bl = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, id), block);
        BLOCKS.add(bl);
        return bl;
    }

    public static void registerBlocks() {
        // This is just here to make sure the class is loaded
    }

}
