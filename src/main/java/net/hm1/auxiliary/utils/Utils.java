package net.hm1.auxiliary.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

public class Utils
{
    public static Item item(String id)
    {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
    }

    @Nullable
    public static Enchantment getEnchantment(String id)
    {
        return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
    }

    public static boolean hasEnchantment(ItemStack stack, @Nullable Enchantment enchantment)
    {
        return enchantment != null && stack.getEnchantmentLevel(enchantment) > 0;
    }
}
