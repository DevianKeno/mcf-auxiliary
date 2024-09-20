package net.hm1.auxiliary.items;

import net.hm1.auxiliary.Auxiliary;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags
{
    public static final TagKey<Block> NEEDS_AUXILIARITE_TOOL = tagBlock("needs_auxiliarite_tool");
    public static final TagKey<Item> MAGIC_ARMOR = tagItem("armors/magic");
    public static final TagKey<Item> DRAGON_SCALE = tagItem("armors/dragon_scale");
    public static final TagKey<Item> DRAGONSTEEL = tagItem("armors/dragonsteel");

    private static TagKey<Block> tagBlock(String name)
    {
        return BlockTags.create(new ResourceLocation(Auxiliary.MOD_ID, name));
    }

    private static TagKey<Item> tagItem(String name)
    {
        return ItemTags.create(new ResourceLocation(Auxiliary.MOD_ID, name));
    }
}
