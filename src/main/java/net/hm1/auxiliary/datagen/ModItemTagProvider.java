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
        super(packOutput, lookupProvider, blockTags, ModTags.FORGE_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tagCoreMaterial();
        tagDragonScaleItem();

        /// Dragon Scale Armor
        for (var color : Auxiliary.ICEANDFIRE_DRAGON_SCALE_COLORS)
            for (var piece : Auxiliary.ARMOR_PIECES)
            {
                tagId(ModTags.DRAGON_SCALE_ARMOR, String.format("iceandfire:armor_%s_%s", color, piece));
            }

        /// Dragonsteel Armor
        for (var set : Auxiliary.ICEANDFIRE_DRAGONSTEEL_SET)
            for (var piece : Auxiliary.ARMOR_PIECES)
            {
                tagId(ModTags.DRAGONSTEEL_ARMOR, String.format("iceandfire:dragonsteel_%s_%s", set, piece));
            }

        /// Guns
        /// TACZ
        tagId(ModTags.GUN, String.format("%s:modern_kinetic_gun", com.tacz.guns.GunMod.MOD_ID));
        /// Pointblank
        for (var type : GunsRegistry.GunTypes.values())
            for (var id : GunsRegistry.Pointblank.getIdsOf(type))
            {
                tagId(ModTags.GUN, String.format("%s:%s", com.vicmatskiv.pointblank.PointBlankMod.MODID, id));
            }

        /// Limestone
        tagIdList(ModTags.LIMESTONE, List.of(new String[]{ "alexscaves:limestone", "create:limestone", "quark:limestone" }));

        /// Magic Armor
        tagIdList(ModTags.MAGIC_ARMOR, MagicArmor.ALL_IDS);
    }

    void tagCoreMaterial()
    {
        String[] items =
        {
            "alexscaves:pure_darkness",
            "bosses_of_mass_destruction:void_thorn",
            "bosses_of_mass_destruction:blazing_eye",
            "bosses_of_mass_destruction:ancient_anima",
            "bosses_of_mass_destruction:obsidian_heart",
            "cataclysm:ignitium_ingot",
            "cataclysm:monstrous_horn",
            "cataclysm:witherite_block",
            "cataclysm:witherite_ingot",
            "deeperdarker:warden_carapace",
            "deeperdarker:heart_of_the_deep",
            "enigmaticlegacy:evil_essence",
            "forbidden_arcanus:dragon_scale",
            "irons_spellbooks:dragonskin",
            "irons_spellbooks:lightning_bottle",
            "minecraft:echo_shard",
            "minecraft:nether_star",
            "quark:dragon_scale",
            "stalwart_dungeons:ancient_fire",
            "stalwart_dungeons:awful_crystal",
            "twilightforest:fiery_blood"
        };

        for (var id : items)
            tagId(ModTags.CORE_MATERIAL, id);
    }

    void tagDragonScaleItem()
    {
        tagId(ModTags.DRAGON_SCALES, "irons_spellbooks:dragonskin" );
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
