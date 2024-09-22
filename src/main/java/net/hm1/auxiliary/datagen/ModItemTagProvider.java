package net.hm1.auxiliary.datagen;

import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.armor.MagicArmor;
import net.hm1.auxiliary.init.ModTags;
import net.hm1.auxiliary.setup.registry.GunsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider
{
    public ModItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper)
    {
        super(packOutput, lookupProvider, blockTags, Auxiliary.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        /// Dragon Scale
        for (var color : Auxiliary.ICEANDFIRE_DRAGON_SCALE_COLORS)
            for (var piece : Auxiliary.ARMOR_PIECES) {
                tagId(ModTags.DRAGON_SCALE, String.format("iceandfire:armor_%s_%s", color, piece));
            }
        /// Dragonsteel
        for (var set : Auxiliary.ICEANDFIRE_DRAGONSTEEL_SET)
            for (var piece : Auxiliary.ARMOR_PIECES) {
                tagId(ModTags.DRAGONSTEEL, String.format("iceandfire:dragonsteel_%s_%s", set, piece));
            }

        /// Gun
        /// TACZ
        tagId(ModTags.GUN, String.format("%s:modern_kinetic_gun", com.tacz.guns.GunMod.MOD_ID));
        for (var type : GunsRegistry.GunTypes.values()) {
            /// Pointblank
            for (var id : GunsRegistry.Pointblank.getIdsOf(type)) {
                tagId(ModTags.GUN, String.format("%s:%s", com.vicmatskiv.pointblank.PointBlankMod.MODID, id));
            }
        }

        /// Magic Armor
        tagIdList(ModTags.MAGIC_ARMOR, MagicArmor.ALL_IDS);
    }

    public void tagId(TagKey<Item> tagKey, String id)
    {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if (item == null || item == Items.AIR) return;
        this.tag(tagKey).add(item);
    }

    public void tagId(TagKey<Item> tagKey, String mod, String id)
    {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod, id));
        if (item == null || item == Items.AIR) return;
        this.tag(tagKey).add(item);
    }

    public void tagIdList(TagKey<Item> tagKey, List<? extends String> ids)
    {
        for (String id : ids) tagId(tagKey, id);
    }
}
