package net.hm1.auxiliary.datagen;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.items.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider
{
    public static final String[] ARMOR_PIECES = { "helmet", "chestplate", "leggings", "boots" };
    public ModItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper)
    {
        super(packOutput, lookupProvider, blockTags, Auxiliary.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        try
        {
            tagDragonScale();
            tagDragonsteel();
        }
        catch (Exception e)
        {
            DataProvider.LOGGER.error("Failed to initialize Ice and Fire Armor tags!");
        }

        try
        {
            tagMagicArmor();
        }
        catch (Exception e)
        {
            DataProvider.LOGGER.error("Failed to initialize Magic Armor tags!");
        }

    }

    private void tagDragonScale()
    {
        for (String color : new String[] { "red", "bronze", "green", "gray", "blue", "white", "sapphire", "silver" , "electric", "amythest", "copper", "black" })
            tagArmorPieces(ModTags.DRAGONSTEEL, "iceandfire", String.format("armor_%s", color), ARMOR_PIECES);
    }

    private void tagDragonsteel()
    {
        for (String color : new String[] { "fire", "ice", "lightning" })
            tagArmorPieces(ModTags.DRAGONSTEEL, "iceandfire", String.format("dragonsteel_%s", color), ARMOR_PIECES);
    }

    private void tagMagicArmor()
    {
        //region Iron's Spells and Spellbooks
        for (var set : new String[] { "wandering_magician", "pumpkin", "netherite_mage", "pyromancer", "electromancer", "archevoker", "cultist", "cryomancer", "shadowwalker", "priest", "plagued" })
        {
            tagArmorPieces(ModTags.MAGIC_ARMOR, "irons_spellbooks", set, ARMOR_PIECES);
        }
        tagItemIdList(ModTags.MAGIC_ARMOR, "irons_spellbooks", new String[]{ "tarnished_helmet", });
        //endregion

        //region Ars Nouveau
        for (var set : new String[] { "sorcerer", "arcanist", "battlemage" })
        {
            tagArmorPieces(ModTags.MAGIC_ARMOR, "ars_nouveau", set, new String[] { "hood", "robes", "leggings", "boots"});
        }
        //endregion

        //region Ars Elemental
        for (var set : new String[] { "fire", "air", "earth", "water" })
        {
            tagArmorPieces(ModTags.MAGIC_ARMOR, "ars_elemental", set,
                new String[] { "hat", "robes", "leggings", "boots"});
        }
        //endregion

        //region Immersive Armors
        for (var set : new String[] { "robe", "divine", })
        {
            tagArmorPieces(ModTags.MAGIC_ARMOR, "immersive_armors", set, ARMOR_PIECES);
        }
        //endregion
    }

    //region Helpers
    private void tagArmorPieces(TagKey<Item> pTag, String mod, String name, String[] pieces)
    {
        for (var piece : pieces)
        {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, String.format("%s_%s", name, piece)));
            if (item == Items.AIR) continue;
            tag(pTag).add(item);
        }
    }

    private void tagItemIdList(TagKey<Item> pTag, String mod, String[] itemNames)
    {
        for (var itemName : itemNames)
        {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, itemName));
            if (item == Items.AIR) continue;
            tag(pTag).add(item);
        }
    }
    //endregion
}
