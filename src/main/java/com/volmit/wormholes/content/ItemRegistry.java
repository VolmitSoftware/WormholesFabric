package com.volmit.wormholes.content;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.volmit.wormholes.Wormholes.MOD_ID;

public class ItemRegistry {

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, MOD_ID), () -> new ItemStack(ItemRegistry.WAND));

    public static final Item WAND = registerItem("wand", new ItemWand(new Item.Settings().group(ITEM_GROUP)));

    public static final Item FRAME = registerItem("frame", new BlockItem(BlockRegistry.FRAME, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_BLACK = registerItem("frame_black", new BlockItem(BlockRegistry.FRAME_BLACK, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_BLUE = registerItem("frame_blue", new BlockItem(BlockRegistry.FRAME_BLUE, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_BROWN = registerItem("frame_brown", new BlockItem(BlockRegistry.FRAME_BROWN, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_RED = registerItem("frame_red", new BlockItem(BlockRegistry.FRAME_RED, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_LIME = registerItem("frame_lime", new BlockItem(BlockRegistry.FRAME_LIME, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_CYAN = registerItem("frame_cyan", new BlockItem(BlockRegistry.FRAME_CYAN, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_LIGHT_GRAY = registerItem("frame_light_gray", new BlockItem(BlockRegistry.FRAME_LIGHT_GRAY, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_GRAY = registerItem("frame_gray", new BlockItem(BlockRegistry.FRAME_GRAY, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_PINK = registerItem("frame_pink", new BlockItem(BlockRegistry.FRAME_PINK, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_LIGHT_BLUE = registerItem("frame_light_blue", new BlockItem(BlockRegistry.FRAME_LIGHT_BLUE, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_MAGENTA = registerItem("frame_magenta", new BlockItem(BlockRegistry.FRAME_MAGENTA, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_ORANGE = registerItem("frame_orange", new BlockItem(BlockRegistry.FRAME_ORANGE, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_WHITE = registerItem("frame_white", new BlockItem(BlockRegistry.FRAME_WHITE, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_YELLOW = registerItem("frame_yellow", new BlockItem(BlockRegistry.FRAME_YELLOW, new Item.Settings().group(ITEM_GROUP)));
    public static final Item FRAME_GREEN = registerItem("frame_green", new BlockItem(BlockRegistry.FRAME_GREEN, new Item.Settings().group(ITEM_GROUP)));

    private static Item registerItem(String id, Item item) {

        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }

    public static void registerItems(){
        // This is just here to make sure the class is loaded
    }

}
