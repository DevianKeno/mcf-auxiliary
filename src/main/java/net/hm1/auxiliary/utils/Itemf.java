package net.hm1.auxiliary.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class Itemf
{
    public static Item item(String id)
    {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
    }
}
