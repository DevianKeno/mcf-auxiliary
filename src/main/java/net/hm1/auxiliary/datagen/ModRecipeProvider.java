package net.hm1.auxiliary.datagen;

import net.hm1.auxiliary.registry.Blocks;
import net.hm1.auxiliary.registry.Items;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    static final String bomd = "bosses_of_mass_destruction";
    static final String bic = "born_in_chaos_v1";
    static final String cataclysm = "cataclysm";
    static final String sd = "stalwart_dungeons";

    public ModRecipeProvider(PackOutput pOutput)
    {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CORE_ITEM.get())
            .pattern("AAA")
            .pattern("AEA")
            .pattern("AAA")
            .define('E', net.minecraft.world.item.Items.ENDER_EYE)
            .define('A', Items.AUXILIARITE_ALLOY.get())
            .unlockedBy(getHasName(Items.AUXILIARITE_ALLOY.get()), has(Items.AUXILIARITE_ALLOY.get()))
            .save(pWriter);

        //region DESTRUCTION_CATALYST
        final Item BLAZING_EYE = ForgeRegistries.ITEMS.getValue(new ResourceLocation(bomd, "blazing_eye"));
        final Item VOID_THORN = ForgeRegistries.ITEMS.getValue(new ResourceLocation(bomd, "void_thorn"));
        final Item BRIMSTONE_NECTAR = ForgeRegistries.ITEMS.getValue(new ResourceLocation(bomd, "brimstone_nectar"));
        final Item OBSIDIAN_HEART = ForgeRegistries.ITEMS.getValue(new ResourceLocation(bomd, "obsidian_heart"));
        final Item ANCIENT_ANIMA = ForgeRegistries.ITEMS.getValue(new ResourceLocation(bomd, "ancient_anima"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DESTRUCTION_CATALYST.get(), 1)
            .pattern(" E ")
            .pattern("VNO")
            .pattern(" A ")
            .define('E', BLAZING_EYE)
            .define('V', VOID_THORN)
            .define('N', BRIMSTONE_NECTAR)
            .define('O', OBSIDIAN_HEART)
            .define('A', ANCIENT_ANIMA)
            .unlockedBy(getHasName(Items.AUXILIARITE_ALLOY.get()), has(Items.AUXILIARITE_ALLOY.get()))
            .save(pWriter);
        //endregion

        //region STALWART_CRYSTAL
        final Item AWFUL_CRYSTAL = ForgeRegistries.ITEMS.getValue(new ResourceLocation(sd, "awful_crystal"));
        final Item TUNGSTEN_INGOT = ForgeRegistries.ITEMS.getValue(new ResourceLocation(sd, "tungsten_ingot"));
        final Item VOID_CRYSTAL = ForgeRegistries.ITEMS.getValue(new ResourceLocation(sd, "void_crystal"));
        final Item CHORUNDUM = ForgeRegistries.ITEMS.getValue(new ResourceLocation(sd, "chorundum"));
        final Item ANCIENT_FIRE = ForgeRegistries.ITEMS.getValue(new ResourceLocation(sd, "ancient_fire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STALWART_CRYSTAL.get(), 1)
            .pattern(" A ")
            .pattern("TVC")
            .pattern(" F ")
            .define('A', AWFUL_CRYSTAL)
            .define('T', TUNGSTEN_INGOT)
            .define('V', VOID_CRYSTAL)
            .define('C', CHORUNDUM)
            .define('F', ANCIENT_FIRE)
            .unlockedBy(getHasName(Items.AUXILIARITE_ALLOY.get()), has(Items.AUXILIARITE_ALLOY.get()))
            .save(pWriter);
        //endregion

        //region CATACLYSMIC_AMALGAMATION
        final Item MONSTROUS_HORN = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cataclysm, "monstrous_horn"));
        final Item WITHERITE_INGOT = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cataclysm, "witherite_ingot"));
        final Item BURNING_ASHES = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cataclysm, "burning_ashes"));
        final Item VOID_CORE = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cataclysm, "void_core"));
        final Item ABYSSAL_EGG = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cataclysm, "abyssal_egg"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CATACLYSMIC_AMALGAMATION.get(), 1)
            .pattern(" H ")
            .pattern("WAV")
            .pattern(" E ")
            .define('H', MONSTROUS_HORN)
            .define('W', WITHERITE_INGOT)
            .define('A', BURNING_ASHES)
            .define('V', VOID_CORE)
            .define('E', ABYSSAL_EGG)
            .unlockedBy(getHasName(Items.AUXILIARITE_ALLOY.get()), has(Items.AUXILIARITE_ALLOY.get()))
            .save(pWriter);
        //endregion

        //region Auxiliarite Alloy recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.AUXILIARITE_ALLOY_BLOCK.get())
            .pattern("AAA")
            .pattern("AAA")
            .pattern("AAA")
            .define('A', Items.AUXILIARITE_ALLOY.get())
            .unlockedBy(getHasName(Items.AUXILIARITE_ALLOY.get()), has(Items.AUXILIARITE_ALLOY.get()))
            .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.AUXILIARITE_ALLOY.get(), 9)
            .requires(Blocks.AUXILIARITE_ALLOY_BLOCK.get())
            .unlockedBy(getHasName(Blocks.AUXILIARITE_ALLOY_BLOCK.get()), has(Blocks.AUXILIARITE_ALLOY_BLOCK.get()))
            .save(pWriter);
        //endregion
    }
}