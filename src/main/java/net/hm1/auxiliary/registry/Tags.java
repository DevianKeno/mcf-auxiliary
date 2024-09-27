package net.hm1.auxiliary.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags
{
    public static final String FORGE_ID = "forge";
    public static final TagKey<Item> CLASS_RANGER = tagItem("classes/ranger");
    public static final TagKey<Item> CLASS_MAGE = tagItem("classes/mage");
    public static final TagKey<Item> CLASS_WARRIOR = tagItem("classes/warrior");
    public static final TagKey<Item> CORE_MATERIAL = tagItem("core_material");
    public static final TagKey<Item> DRAGON_SCALES = tagItem("dragon_scales");
    public static final TagKey<Item> DRAGON_SCALE_ARMOR = tagItem("armors/dragon_scale");
    public static final TagKey<Item> DRAGONSTEEL_ARMOR = tagItem("armors/dragonsteel");
    public static final TagKey<Item> GUN = tagItem("gun");
    public static final TagKey<Item> LIMESTONE = tagItem("limestone");
    public static final TagKey<Item> MAGIC_ARMOR = tagItem("armors/magic");
    public static final TagKey<Block> NEEDS_AUXILIARITE_TOOL = tagBlock("needs_auxiliarite_tool");

    public static TagKey<Block> tagBlock(String name)
    {
        return BlockTags.create(new ResourceLocation(FORGE_ID, name));
    }

    public static TagKey<Item> tagItem(String name)
    {
        return ItemTags.create(new ResourceLocation(FORGE_ID, name));
    }

    public static TagKey<Block> tagBlock(String namespace, String name)
    {
        return BlockTags.create(new ResourceLocation(namespace, name));
    }

    public static TagKey<Item> tagItem(String namespace, String name)
    {
        return ItemTags.create(new ResourceLocation(namespace, name));
    }
}
