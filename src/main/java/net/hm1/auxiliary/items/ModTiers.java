package net.hm1.auxiliary.items;

import net.hm1.auxiliary.setup.config.AuxiliaryConfig;
import net.hm1.auxiliary.Auxiliary;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

import static com.ibm.icu.text.PluralRules.Operand.f;

public class ModTiers
{
    static final ForgeTier auxiliariteTier = new ForgeTier(
        5,
        3150, //AuxiliaryConfig.AUXI_WEAPON_DURABILITY.get(),
        2f, //AuxiliaryConfig.AUXI_SPEED.get().floatValue(),
        0,
        18,
        ModTags.NEEDS_AUXILIARITE_TOOL,
        () -> Ingredient.of(ModItems.AUXILIARITE_ALLOY.get())
    );

    public static final Tier AUXILIARITE = TierSortingRegistry.registerTier(
        auxiliariteTier,
        new ResourceLocation(Auxiliary.MOD_ID, "auxiliarite"),
        List.of(Tiers.NETHERITE),
        List.of()
    );
}
