package net.hm1.auxiliary.event;

import com.hollingsworth.arsnouveau.api.perk.*;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import com.hollingsworth.arsnouveau.api.util.RomanNumber;
import net.hm1.auxiliary.Auxiliary;
import net.hm1.auxiliary.init.ModTags;
import net.hm1.auxiliary.setup.registry.ArsNouveauAuxiliary;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = Auxiliary.MOD_ID)
public class MagicArmorTooltipEvent
{
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onItemTooltip(ItemTooltipEvent event)
    {
        if (!event.getItemStack().is(ModTags.MAGIC_ARMOR)) return;

        appendHoverText(event.getItemStack(), event.getToolTip(), event.getFlags());
    }

    static void appendHoverText(ItemStack stack, List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.addAll(1, constructArmorPerkTooltips(stack)); /// add to index 1 so it displays at the top :)
    }

    /*
     * Create perk tooltips
     */
    static List<Component> constructArmorPerkTooltips(ItemStack stack)
    {
        var perkTooltips = new ArrayList<Component>();

        IPerkProvider<ItemStack> perkProvider = PerkRegistry.getPerkProvider(stack.getItem());
        if (perkProvider != null && perkProvider.getPerkHolder(stack) instanceof ArmorPerkHolder perkHolder)
        {
            if (perkHolder.getTier() == 0)
            {
                perkTooltips.add(Component.translatable("ars_nouveau.threadable_armor").withStyle(ChatFormatting.GOLD));
                return perkTooltips;
            }

            if (ArsNouveauAuxiliary.PerkSlots.TIER_MAPPINGS.containsKey(perkHolder.getTier()))
            {
                perkTooltips.add(Component.literal(Component.translatable("ars_nouveau.threaded").getString() + " " + RomanNumber.toRoman(perkHolder.getTier()))
                    .withStyle(ChatFormatting.GOLD));

                var perkSlots = new ArrayList<>(ArsNouveauAuxiliary.PerkSlots.TIER_MAPPINGS.get(perkHolder.getTier()).stream().filter(s -> s != ArsNouveauAuxiliary.PerkSlots.NONE).toList());
                if (perkSlots.isEmpty()) return perkTooltips;

                var perks = perkHolder.getPerks();
                for (IPerk perk : perks)
                {
                    if (perkSlots.isEmpty()) break;
                    var perkSlot = perkSlots.get(0);
                    perkSlots.remove(0);
                    var perkName = Component.translatable("item." + perk.getRegistryName().getNamespace() + "." + perk.getRegistryName().getPath()).getString();
                    perkTooltips.add(Component.literal(perkName + " " + RomanNumber.toRoman(perkSlot.value)).withStyle(ChatFormatting.GRAY));
                }

                for (var rem : perkSlots) /// print the rem. empty slots
                {
                    perkTooltips.add(Component.literal("Empty " + RomanNumber.toRoman(rem.value)).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
                }
            }
        }

        return perkTooltips;
    }
}
