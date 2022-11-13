package com.volmit.wormholes.content;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.List;

public class ItemPortkey extends Item {
    public ItemPortkey(Settings settings) {
        super(settings.maxCount(8).fireproof().rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("§7Construct a frame of §dLogs§7 or §dPlanks§7, or §dWool"));
        tooltip.add(Text.of("§7Toss a Key in, Link to a §5§l§nVoid Dimension"));
        tooltip.add(Text.of("§7§lDimensional Scales Below: "));
        tooltip.add(Text.of("§7Wool: 1 : 10 | Logs: 500 : 1 | Planks: 1 : 1000"));
        tooltip.add(Text.of("§7§oThe Overworld/Nether is  8 : 1"));
    }
}
