package net.hm1.auxiliary.datagen;

import alexthw.ars_elemental.common.items.armor.ArmorSet;
import alexthw.ars_elemental.datagen.AETagsProvider;
import alexthw.ars_elemental.recipe.ElementalArmorRecipe;
import alexthw.ars_elemental.registry.ModItems;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ArmorUpgradeRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import net.hm1.auxiliary.recipe.MagicArmorUpgradeRecipe;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.List;

public class MagicArmorUpgradeRecipeProvider extends ApparatusRecipeProvider
{
    public MagicArmorUpgradeRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    public void addEntries()
    {
        this.recipes.add(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.RODS_BLAZE)),
            3000, 1));

        this.recipes.add(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.RODS_BLAZE),
                Ingredient.of(Tags.Items.RODS_BLAZE)),
            3000, 2));

        this.addRecipe(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.RODS_BLAZE),
                Ingredient.of(Tags.Items.RODS_BLAZE),
                Ingredient.of(Tags.Items.RODS_BLAZE)),
            3000, 3));

        this.addRecipe(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.ENDER_PEARLS)),
            5000, 4));

        this.addRecipe(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.ENDER_PEARLS),
                Ingredient.of(Tags.Items.ENDER_PEARLS)),
            5000, 5));

        this.addRecipe(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.ENDER_PEARLS),
                Ingredient.of(Tags.Items.ENDER_PEARLS),
                Ingredient.of(Tags.Items.ENDER_PEARLS)),
            5000, 6));

        this.addRecipe(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.NETHER_STARS)),
            10000, 7));

        this.addRecipe(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.NETHER_STARS),
                Ingredient.of(Tags.Items.NETHER_STARS)),
            10000, 8));

        this.addRecipe(new MagicArmorUpgradeRecipe(
            List.of(
                Ingredient.of(Tags.Items.NETHER_STARS),
                Ingredient.of(Tags.Items.NETHER_STARS),
                Ingredient.of(Tags.Items.NETHER_STARS)),
            10000, 9));
    }
}